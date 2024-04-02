package farmApp.farmbackend.business.imp;

import java.util.List;

import farmApp.farmbackend.dto.SheepDto;


public interface SheepService {
	
    SheepDto getSheepById(Long sheepId);
	
	List<SheepDto> getAllSheeps();
	
	SheepDto updateSheep (Long sheepId, SheepDto updatedSheep);
	
	void deleteSheep (Long sheepId);
	
	SheepDto createSheep(SheepDto sheepDto);
	
	long getTotalNumberOfSheep();
	

}
