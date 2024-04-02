package farmApp.farmbackend.business.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import farmApp.farmbackend.dataAccess.SheepRepository;
import farmApp.farmbackend.dto.SheepDto;
import farmApp.farmbackend.entities.Sheep;
import farmApp.farmbackend.exception.ResourceNotFoundException;
import farmApp.farmbackend.mapper.SheepMapper;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class SheepServiceImp implements SheepService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SheepServiceImp.class);
	
	private SheepRepository sheepRepository;
	
	//Get Sheep REST API
	@Override
	public long getTotalNumberOfSheep() {
		
		LOGGER.info("getTotalNumberOfSheep method called");
		
		long totalNumberOfSheep= sheepRepository.count();
		
		LOGGER.info("Total number of sheeps: {}", totalNumberOfSheep);
		
		return totalNumberOfSheep;
	}

	//Get Sheep REST API {id}
	@Override
	public SheepDto getSheepById(Long sheepId) {
		
		LOGGER.info("getSheepById method called with sheepId: {}", sheepId);
		
		Sheep sheep = sheepRepository.findById(sheepId)
				.orElseThrow(() -> new ResourceNotFoundException("Sheep is not exists with given id" + sheepId));
		
		LOGGER.info("Found sheep with id: {}", sheepId);
		
		return SheepMapper.mapToSheepDto(sheep);
	}

	//Get Sheep REST API
	@Override
	public List<SheepDto> getAllSheeps() {
		
		LOGGER.info("All sheeps are taken");
		
		List<Sheep> sheeps = sheepRepository.findAll();
		
		LOGGER.info("All sheeps were received successfully");
		
		return sheeps.stream().map((sheep) -> SheepMapper.mapToSheepDto(sheep))
				.collect(Collectors.toList());
	}
	
	//Update Sheep REST API {id}
	@Override
	public SheepDto updateSheep(Long sheepId, SheepDto updatedSheep) {
		
		LOGGER.info("updateSheep method called with sheepId: {} and updatedSheep: {}", sheepId, updatedSheep);
		
		Sheep sheep = sheepRepository.findById(sheepId)
				.orElseThrow(() -> new ResourceNotFoundException("Sheep is not exists with given id" + sheepId));
		
		LOGGER.debug("Found sheep with id: {}", sheepId);
		
		sheep.setAge(updatedSheep.getAge());
		sheep.setGender(updatedSheep.getGender());
		sheep.setType(updatedSheep.getType());
		sheep.setWeight(updatedSheep.getWeight());
		
		Sheep newSheepUpdate = sheepRepository.save(sheep);
		
		LOGGER.info("Sheep updated successfully: {}", newSheepUpdate);
		
		return SheepMapper.mapToSheepDto(newSheepUpdate);
	}
	

	//Delete Sheep REST API {id}
	@Override
	public void deleteSheep(Long sheepId) {
		
		LOGGER.info("deleteSheep method called with sheepId: {}", sheepId);
		
		Sheep sheep = sheepRepository.findById(sheepId)
				.orElseThrow(() -> new ResourceNotFoundException("Sheep is not exists with given id" + sheepId));
		
		LOGGER.debug("Found sheep with id: {}", sheepId);
		
		sheepRepository.deleteById(sheepId);
		
		LOGGER.info("Sheep with id {} deleted successfully", sheepId);
		
	}

	
	//Post Sheep REST API
	@Override
	public SheepDto createSheep(SheepDto sheepDto) {
		
		LOGGER.info("createSheep method called with sheepDto: {}", sheepDto);
		
		Sheep sheep = SheepMapper.mapToGoat(sheepDto);
		
		LOGGER.debug("Mapping SheepDto to Sheep: {}", sheep);
		
		Sheep savedSheep = sheepRepository.save(sheep);
		
		LOGGER.info("Sheep saved successfully: {}", savedSheep);
		
		return SheepMapper.mapToSheepDto(savedSheep);
	}

	

}
