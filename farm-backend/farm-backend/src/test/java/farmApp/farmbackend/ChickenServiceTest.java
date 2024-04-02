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

import farmApp.farmbackend.business.imp.ChickenServiceImp;
import farmApp.farmbackend.dataAccess.ChickenRepository;
import farmApp.farmbackend.dto.ChickenDto;
import farmApp.farmbackend.entities.Chicken;
import farmApp.farmbackend.exception.ResourceNotFoundException;
import farmApp.farmbackend.mapper.ChickenMapper;

@ExtendWith(MockitoExtension.class)
public class ChickenServiceTest {

    @Mock
    private ChickenRepository chickenRepository;

    @InjectMocks
    private ChickenServiceImp chickenService;
    
    @Test //getTotalNumberOfChicken{5}
    public void testGetTotalNumberOfChicken_WhenChickenExists() {
        
        long expectedCount = 5L;
        when(chickenRepository.count()).thenReturn(expectedCount);
        
        long result = chickenService.getTotalNumberOfChicken();

        assertEquals(expectedCount, result,"If there are chickens it should return the total amount of chickens");
    }

    @Test //getTotalNumberOfChicken{0}
    public void testGetTotalNumberOfChicken_WhenNoChickenExists() {
      
        long expectedCount = 0L;
        when(chickenRepository.count()).thenReturn(expectedCount);

        long result = chickenService.getTotalNumberOfChicken();

        assertEquals(expectedCount, result,"If there is no chicken in the database it should return 0");
    }

    @Test //getChickenById{10}
    public void testGetChickenByIdValidId() {

        Long chickenId = 1L;
        Chicken chicken = new Chicken();
        chicken.setId(chickenId);
        chicken.setType("Type A");
        when(chickenRepository.findById(chickenId)).thenReturn(java.util.Optional.of(chicken));

        ChickenDto result = chickenService.getChickenById(chickenId);

        assertNotNull(result);
        assertEquals(chickenId, result.getId());
        assertEquals("Type A", result.getType());
    }

    @Test //getChickenById{999}
    public void testGetChickenByIdInvalidId() {
       
        Long chickenId = 2L;
        
        when(chickenRepository.findById(chickenId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            chickenService.getChickenById(chickenId);
        });
    }
    
    
    @Test //getAllChickens
    public void testGetAllChickens() {

        List<Chicken> mockChickens = new ArrayList<>();
        mockChickens.add(new Chicken(1L, "Type A", "Chicken", 2, 1.5));
        mockChickens.add(new Chicken(2L, "Type B", "Rooster", 3, 1.8));
        when(chickenRepository.findAll()).thenReturn(mockChickens);

       
        List<ChickenDto> result = chickenService.getAllChickens();

       
        assertEquals(mockChickens.size(), result.size(), "Tüm tavuklar dönülmeli");
        for (int i = 0; i < mockChickens.size(); i++) {
            ChickenDto expectedDto = ChickenMapper.mapToChickenDto(mockChickens.get(i));
            assertEquals(expectedDto.getId(), result.get(i).getId(), "Chicken ids must match");
            assertEquals(expectedDto.getType(), result.get(i).getType(), "Chicken types must match");
            assertEquals(expectedDto.getAge(), result.get(i).getAge(), "Chicken ages must match");
            assertEquals(expectedDto.getGender(), result.get(i).getGender(), "Chicken genders must match");
            assertEquals(expectedDto.getWeight(), result.get(i).getWeight(), "Chicken weights must match");
        }
    }
    
    
    @Test //updateChicken
    public void testUpdateChicken() {
   
        Long chickenId = 1L;
        ChickenDto updatedChickenDto = new ChickenDto();
        updatedChickenDto.setType("Type A");
        updatedChickenDto.setAge(3);
        updatedChickenDto.setGender("Rooster");
        updatedChickenDto.setWeight(2.0);

        Chicken existingChicken = new Chicken();
        existingChicken.setId(chickenId);
        existingChicken.setType("Type B");
        existingChicken.setAge(2);
        existingChicken.setGender("Chicken");
        existingChicken.setWeight(1.5);

        when(chickenRepository.findById(chickenId)).thenReturn(java.util.Optional.of(existingChicken));
        when(chickenRepository.save(any(Chicken.class))).thenAnswer(invocation -> invocation.getArgument(0));

       
        ChickenDto result = chickenService.updateChicken(chickenId, updatedChickenDto);

        
        assertEquals(updatedChickenDto.getType(), result.getType(), "Chicken type should be updated");
        assertEquals(updatedChickenDto.getAge(), result.getAge(), "Chicken age should be updated");
        assertEquals(updatedChickenDto.getGender(), result.getGender(), "Chicken gender should be updated");
        assertEquals(updatedChickenDto.getWeight(), result.getWeight(), "Chicken weight should be updated");
    }
    
    @Test //updateChicken
    public void testUpdateChickenNotFound() {
        
        Long chickenId = 1L;
        ChickenDto updatedChicken = new ChickenDto();
        updatedChicken.setAge(2);
        updatedChicken.setGender("Chicken");
        updatedChicken.setType("Type A");
        updatedChicken.setWeight(1.5);

        when(chickenRepository.findById(chickenId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            chickenService.updateChicken(chickenId, updatedChicken);
        });
    }
    
    @Test //deleteChicken
    public void testDeleteChicken() {
        Long chickenId = 1L;
        Chicken chicken = new Chicken();
        chicken.setId(chickenId);
        
        when(chickenRepository.findById(chickenId)).thenReturn(java.util.Optional.of(chicken));

        chickenService.deleteChicken(chickenId);
        verify(chickenRepository, times(1)).deleteById(chickenId);
    }
    
    @Test //deleteChicken
    public void testDeleteChickenNotFound() {
       
        Long chickenId = 1L;
       
        when(chickenRepository.findById(chickenId)).thenReturn(java.util.Optional.empty());

        
        assertThrows(ResourceNotFoundException.class, () -> {
            chickenService.deleteChicken(chickenId);
        });
        verify(chickenRepository, never()).deleteById(chickenId);
    }
    
    
    @Test //createChicken
    public void testCreateChicken() {
    	
        ChickenDto chickenDto = new ChickenDto();
        chickenDto.setType("Type A");
        chickenDto.setAge(2);
        chickenDto.setGender("Chicken");
        chickenDto.setWeight(1.5);

        Chicken chicken = new Chicken();
        chicken.setType(chickenDto.getType());
        chicken.setAge(chickenDto.getAge());
        chicken.setGender(chickenDto.getGender());
        chicken.setWeight(chickenDto.getWeight());

        when(chickenRepository.save(any(Chicken.class))).thenReturn(chicken);

        
        ChickenDto result = chickenService.createChicken(chickenDto);


        assertNotNull(result);
        assertEquals(chickenDto.getType(), result.getType());
        assertEquals(chickenDto.getAge(), result.getAge());
        assertEquals(chickenDto.getGender(), result.getGender());
        assertEquals(chickenDto.getWeight(), result.getWeight());
    }
    
    
    
    
}
