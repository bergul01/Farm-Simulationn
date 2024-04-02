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

import farmApp.farmbackend.business.imp.SheepServiceImp;
import farmApp.farmbackend.dataAccess.SheepRepository;
import farmApp.farmbackend.dto.SheepDto;
import farmApp.farmbackend.entities.Sheep;
import farmApp.farmbackend.exception.ResourceNotFoundException;
import farmApp.farmbackend.mapper.SheepMapper;

@ExtendWith(MockitoExtension.class)
public class SheepServiceTest {
	
	@Mock
	private SheepRepository sheepRepository;
	
	@InjectMocks
	private SheepServiceImp sheepService;
	
	@Test //getTotalNumberOfSheep{5}
    public void testGetTotalNumberOfSheep_WhenSheepExists() {
        long expectedCount = 5L;
        when(sheepRepository.count()).thenReturn(expectedCount);

        long result = sheepService.getTotalNumberOfSheep();

        assertEquals(expectedCount, result,"If there are sheep, it should return the total amount of sheep");
    }

    @Test //getTotalNumberOfSheep{0}
    public void testGetTotalNumberOfSheep_WhenNoSheepExists() {
        long expectedCount = 0L;
        when(sheepRepository.count()).thenReturn(expectedCount);

        long result = sheepService.getTotalNumberOfSheep();

        assertEquals(expectedCount, result,"If there are no sheep in the database, it should return 0");
    }

    @Test //getSheepByIdValidId
    public void testGetSheepByIdValidId() {
        Long sheepId = 1L;
        Sheep sheep = new Sheep();
        sheep.setId(sheepId);
        sheep.setType("Type A");
        when(sheepRepository.findById(sheepId)).thenReturn(java.util.Optional.of(sheep));

        SheepDto result = sheepService.getSheepById(sheepId);

        assertNotNull(result);
        assertEquals(sheepId, result.getId());
        assertEquals("Type A", result.getType());
    }

    @Test //getSheepByIdInvalidId
    public void testGetSheepByIdInvalidId() {
        Long sheepId = 2L;

        when(sheepRepository.findById(sheepId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            sheepService.getSheepById(sheepId);
        });
    }

    @Test //getAllSheep
    public void testGetAllSheep() {
        List<Sheep> mockSheep = new ArrayList<>();
        mockSheep.add(new Sheep(1L, "Type A","Ram", 2, 1.5));
        mockSheep.add(new Sheep(2L, "Type B","Ewe", 3, 1.8));
        when(sheepRepository.findAll()).thenReturn(mockSheep);

        List<SheepDto> result = sheepService.getAllSheeps();

        assertEquals(mockSheep.size(), result.size(), "All sheep should be returned");
        for (int i = 0; i < mockSheep.size(); i++) {
            SheepDto expectedDto = SheepMapper.mapToSheepDto(mockSheep.get(i));
            assertEquals(expectedDto.getId(), result.get(i).getId(), "Sheep ids must match");
            assertEquals(expectedDto.getType(), result.get(i).getType(), "Sheep types must match");
            assertEquals(expectedDto.getAge(), result.get(i).getAge(), "Sheep ages must match");
            assertEquals(expectedDto.getWeight(), result.get(i).getWeight(), "Sheep weights must match");
        }
    }

    @Test //updateSheep
    public void testUpdateSheep() {
        Long sheepId = 1L;
        SheepDto updatedSheepDto = new SheepDto();
        updatedSheepDto.setType("Type A");
        updatedSheepDto.setAge(3);
        updatedSheepDto.setGender("Ram");
        updatedSheepDto.setWeight(2.0);

        Sheep existingSheep = new Sheep();
        existingSheep.setId(sheepId);
        existingSheep.setType("Type B");
        existingSheep.setAge(2);
        existingSheep.setGender("Ewe");
        existingSheep.setWeight(1.5);

        when(sheepRepository.findById(sheepId)).thenReturn(java.util.Optional.of(existingSheep));
        when(sheepRepository.save(any(Sheep.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SheepDto result = sheepService.updateSheep(sheepId, updatedSheepDto);

        assertEquals(updatedSheepDto.getType(), result.getType(), "Sheep type should be updated");
        assertEquals(updatedSheepDto.getAge(), result.getAge(), "Sheep age should be updated");
        assertEquals(updatedSheepDto.getGender(), result.getGender(),"Sheep gender should be updated");
        assertEquals(updatedSheepDto.getWeight(), result.getWeight(), "Sheep weight should be updated");
    }

    @Test //updateSheepNotFound
    public void testUpdateSheepNotFound() {
        Long sheepId = 1L;
        SheepDto updatedSheep = new SheepDto();
        updatedSheep.setAge(2);
        updatedSheep.setGender("Ram");
        updatedSheep.setType("Type A");
        updatedSheep.setWeight(1.5);

        when(sheepRepository.findById(sheepId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            sheepService.updateSheep(sheepId, updatedSheep);
        });
    }

    @Test //deleteSheep
    public void testDeleteSheep() {
        Long sheepId = 1L;
        Sheep sheep = new Sheep();
        sheep.setId(sheepId);

        when(sheepRepository.findById(sheepId)).thenReturn(java.util.Optional.of(sheep));

        sheepService.deleteSheep(sheepId);
        verify(sheepRepository, times(1)).deleteById(sheepId);
    }

    @Test //deleteSheepNotFound
    public void testDeleteSheepNotFound() {
        Long sheepId = 1L;

        when(sheepRepository.findById(sheepId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            sheepService.deleteSheep(sheepId);
        });
        verify(sheepRepository, never()).deleteById(sheepId);
    }

    @Test //createSheep
    public void testCreateSheep() {
        SheepDto sheepDto = new SheepDto();
        sheepDto.setType("Type A");
        sheepDto.setAge(2);
        sheepDto.setGender("Ram");
        sheepDto.setWeight(1.5);

        Sheep sheep = new Sheep();
        sheep.setType(sheepDto.getType());
        sheep.setAge(sheepDto.getAge());
        sheep.setGender(sheepDto.getGender());
        sheep.setWeight(sheepDto.getWeight());

        when(sheepRepository.save(any(Sheep.class))).thenReturn(sheep);

        SheepDto result = sheepService.createSheep(sheepDto);

        assertNotNull(result);
        assertEquals(sheepDto.getType(), result.getType());
        assertEquals(sheepDto.getAge(), result.getAge());
        assertEquals(sheepDto.getGender(),result.getGender());
        assertEquals(sheepDto.getWeight(), result.getWeight());
    }

}
