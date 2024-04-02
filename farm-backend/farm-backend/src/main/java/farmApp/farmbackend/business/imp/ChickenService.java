package farmApp.farmbackend.business.imp;

import java.util.List;

import farmApp.farmbackend.dto.ChickenDto;


public interface ChickenService {
	
	ChickenDto getChickenById(Long chickenId);
	
	List<ChickenDto> getAllChickens();
	
	ChickenDto updateChicken(Long chickenId, ChickenDto updatedChicken);
	
	void deleteChicken(Long chickenId);
	
	ChickenDto createChicken (ChickenDto chickenDto);
	
	long getTotalNumberOfChicken();

}
