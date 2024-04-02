package farmApp.farmbackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import farmApp.farmbackend.business.imp.GoatService;
import farmApp.farmbackend.business.imp.GoatServiceImp;
import farmApp.farmbackend.dataAccess.GoatRepository;
import farmApp.farmbackend.dto.GoatDto;
import farmApp.farmbackend.entities.Goat;
import farmApp.farmbackend.exception.ResourceNotFoundException;
import farmApp.farmbackend.mapper.GoatMapper;

@ExtendWith(MockitoExtension.class)
public class GoatServiceTest {

	@Mock
	private GoatRepository goatRepository;
	
	@InjectMocks
	private GoatServiceImp goatService;
	
	@Test //getTotalNumberOfGoat{5}
    public void testGetTotalNumberOfGoat_WhenGoatExists() {
        
        long expectedCount = 5L;
        when(goatRepository.count()).thenReturn(expectedCount);
        
        long result = goatService.getTotalNumberOfGoat();

        assertEquals(expectedCount, result,"If there are goats it should return the total amount of goats");
    }

    @Test //getTotalNumberOfGoat{0}
    public void testGetTotalNumberOfGoat_WhenNoGoatExists() {
      
        long expectedCount = 0L;
        when(goatRepository.count()).thenReturn(expectedCount);

        long result = goatService.getTotalNumberOfGoat();

        assertEquals(expectedCount, result,"If there is no goat in the database it should return 0");
    }
    
    @Test //getGoatById{10}
    public void testGetGoatByIdValidId() {

        Long goatId = 1L;
        Goat goat = new Goat();
        goat.setId(goatId);
        goat.setType("Type A");
        when(goatRepository.findById(goatId)).thenReturn(java.util.Optional.of(goat));

        GoatDto result = goatService.getGoatById(goatId);

        assertNotNull(result);
        assertEquals(goatId, result.getId());
        assertEquals("Type A", result.getType());
    }

    @Test //getgoatById{999}
    public void testGetGoatByIdInvalidId() {
       
        Long goatId = 2L;
        
        when(goatRepository.findById(goatId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            goatService.getGoatById(goatId);
        });
    }
    
    @Test //getAllGoat
    public void testGetAllGoats() {

        List<Goat> mockGoats = new ArrayList<>();
        mockGoats.add(new Goat(1L, "Type A", "Billy Goat", 2, 1.5));
        mockGoats.add(new Goat(2L, "Type B", "Goat", 3, 1.8));
        when(goatRepository.findAll()).thenReturn(mockGoats);

       
        List<GoatDto> result = goatService.getAllGoat();

       
        assertEquals(mockGoats.size(), result.size(), "Tüm tavuklar dönülmeli");
        for (int i = 0; i < mockGoats.size(); i++) {
            GoatDto expectedDto = GoatMapper.mapToGoatDto(mockGoats.get(i));
            assertEquals(expectedDto.getId(), result.get(i).getId(), "Goat ids must match");
            assertEquals(expectedDto.getType(), result.get(i).getType(), "Goat types must match");
            assertEquals(expectedDto.getAge(), result.get(i).getAge(), "Goat ages must match");
            assertEquals(expectedDto.getGender(), result.get(i).getGender(), "Goat genders must match");
            assertEquals(expectedDto.getWeight(), result.get(i).getWeight(), "Goat weights must match");
        }
    }
    
    @Test //updateGoat
    public void testUpdateGoat() {
        Long goatId = 1L;
        GoatDto updatedGoatDto = new GoatDto();
        updatedGoatDto.setType("Type A");
        updatedGoatDto.setAge(3);
        updatedGoatDto.setGender("Billy Goat");
        updatedGoatDto.setWeight(2.0);

        Goat existingGoat = new Goat();
        existingGoat.setId(goatId);
        existingGoat.setType("Type B");
        existingGoat.setAge(2);
        existingGoat.setGender("Goat");
        existingGoat.setWeight(1.5);

        when(goatRepository.findById(goatId)).thenReturn(java.util.Optional.of(existingGoat));
        when(goatRepository.save(any(Goat.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GoatDto result = goatService.updateGoat(goatId, updatedGoatDto);

        assertEquals(updatedGoatDto.getType(), result.getType(), "Goat type should be updated");
        assertEquals(updatedGoatDto.getAge(), result.getAge(), "Goat age should be updated");
        assertEquals(updatedGoatDto.getGender(), result.getGender(), "Goat gender should be updated");
        assertEquals(updatedGoatDto.getWeight(), result.getWeight(), "Goat weight should be updated");
    }

    @Test //updateGoatNotFound
    public void testUpdateGoatNotFound() {
        Long goatId = 1L;
        GoatDto updatedGoat = new GoatDto();
        updatedGoat.setAge(2);
        updatedGoat.setGender("Goat");
        updatedGoat.setType("Type A");
        updatedGoat.setWeight(1.5);

        when(goatRepository.findById(goatId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            goatService.updateGoat(goatId, updatedGoat);
        });
    }
    
    @Test //deleteGoat
    public void testDeleteGoat() {
        Long goatId = 1L;
        Goat goat = new Goat();
        goat.setId(goatId);

        when(goatRepository.findById(goatId)).thenReturn(java.util.Optional.of(goat));

        goatService.deleteGoat(goatId);
        verify(goatRepository, times(1)).deleteById(goatId);
    }

    @Test //deleteGoatNotFound
    public void testDeleteGoatNotFound() {
        Long goatId = 1L;

        when(goatRepository.findById(goatId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            goatService.deleteGoat(goatId);
        });
        verify(goatRepository, never()).deleteById(goatId);
    }
    
    @Test //createGoat
    public void testCreateGoat() {
        GoatDto goatDto = new GoatDto();
        goatDto.setType("Type A");
        goatDto.setAge(2);
        goatDto.setGender("Goat");
        goatDto.setWeight(1.5);

        Goat goat = new Goat();
        goat.setType(goatDto.getType());
        goat.setAge(goatDto.getAge());
        goat.setGender(goatDto.getGender());
        goat.setWeight(goatDto.getWeight());

        when(goatRepository.save(any(Goat.class))).thenReturn(goat);

        GoatDto result = goatService.createGoat(goatDto);

        assertNotNull(result);
        assertEquals(goatDto.getType(), result.getType());
        assertEquals(goatDto.getAge(), result.getAge());
        assertEquals(goatDto.getGender(), result.getGender());
        assertEquals(goatDto.getWeight(), result.getWeight());
    }
    
 
	
}
