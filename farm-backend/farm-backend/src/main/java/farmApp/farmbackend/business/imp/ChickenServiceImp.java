package farmApp.farmbackend.business.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import farmApp.farmbackend.dataAccess.ChickenRepository;
import farmApp.farmbackend.dto.ChickenDto;
import farmApp.farmbackend.entities.Chicken;
import farmApp.farmbackend.exception.ResourceNotFoundException;
import farmApp.farmbackend.mapper.ChickenMapper;
import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class ChickenServiceImp implements ChickenService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChickenServiceImp.class);
	
	private ChickenRepository chickenRepository;
	
	//Get Chicken REST API
	@Override
	public long getTotalNumberOfChicken() {
		
		LOGGER.info("getTotalNumberOfChicken method called");
		
		long totalNumberOfChicken = chickenRepository.count();
		
		LOGGER.info("Total number of chickens: {}", totalNumberOfChicken);
		
		return totalNumberOfChicken;
		
		
	}
	
	//Get Chicken REST API {id}
	@Override
	public ChickenDto getChickenById(Long chickenId) {
		
		LOGGER.info("getChickenById method called with chickenId: {}", chickenId);

		
		Chicken chicken = chickenRepository.findById(chickenId)
				.orElseThrow(() -> new ResourceNotFoundException("Chicken is not exists with given id" + chickenId));
		
		LOGGER.info("Found chicken with id: {}", chickenId);
		
		return ChickenMapper.mapToChickenDto(chicken);
	}
	//Get Chicken REST API
	@Override
	public List<ChickenDto> getAllChickens() {
		
		LOGGER.info("All chickens are taken");
		
		List<Chicken> chickens = chickenRepository.findAll();
		
		LOGGER.info("All chickens were received successfully");
		
		return chickens.stream().map((chicken) -> ChickenMapper.mapToChickenDto(chicken))
				.collect(Collectors.toList());
	}
	//Update Chicken REST API {id}
	@Override
	public ChickenDto updateChicken(Long chickenId, ChickenDto updatedChicken) {
		
		LOGGER.info("updateChicken method called with chickenId: {} and updatedChicken: {}", chickenId, updatedChicken);
		
		Chicken chicken = chickenRepository.findById(chickenId)
				.orElseThrow(() -> new ResourceNotFoundException("Chicken is not exists with given id" + chickenId));
		
		LOGGER.debug("Found chicken with id: {}", chickenId);

		
		chicken.setAge(updatedChicken.getAge());
		chicken.setGender(updatedChicken.getGender());
		chicken.setType(updatedChicken.getType());
		chicken.setWeight(updatedChicken.getWeight());
		
		Chicken newChickenUpdate = chickenRepository.save(chicken);
		
		LOGGER.info("Chicken updated successfully: {}", newChickenUpdate);
		
		return ChickenMapper.mapToChickenDto(newChickenUpdate);
	}
	
	//Delete Chicken REST API {id}
	@Override
	public void deleteChicken(Long chickenId) {
		
		LOGGER.info("deleteChicken method called with chickenId: {}", chickenId);
		
		Chicken chicken = chickenRepository.findById(chickenId)
				.orElseThrow(() -> new ResourceNotFoundException("Chicken is not exists with given id" + chickenId));
		
		LOGGER.debug("Found chicken with id: {}", chickenId);
		
		chickenRepository.deleteById(chickenId);
		
		LOGGER.info("Chicken with id {} deleted successfully", chickenId);
		
	}

	//Post Chicken REST API
	@Override
	public ChickenDto createChicken(ChickenDto chickenDto) {
		
		LOGGER.info("createChicken method called with chickenDto: {}", chickenDto);
		
		Chicken chicken = ChickenMapper.mapToChicken(chickenDto);
		
		LOGGER.debug("Mapping ChickenDto to Chicken: {}", chicken);
		
		Chicken savedChicken = chickenRepository.save(chicken);
		
		LOGGER.info("Chicken saved successfully: {}", savedChicken);
		
		return ChickenMapper.mapToChickenDto(savedChicken);
	}
		
		
	
	
	
	

}
