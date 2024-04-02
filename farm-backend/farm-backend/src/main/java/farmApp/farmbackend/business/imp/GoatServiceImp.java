package farmApp.farmbackend.business.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import farmApp.farmbackend.dataAccess.GoatRepository;
import farmApp.farmbackend.dto.GoatDto;
import farmApp.farmbackend.entities.Goat;
import farmApp.farmbackend.exception.ResourceNotFoundException;
import farmApp.farmbackend.mapper.GoatMapper;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class GoatServiceImp implements GoatService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoatServiceImp.class);
	
	private GoatRepository goatRepository;
	
	//Get Goat REST API
	@Override
	public long getTotalNumberOfGoat() {
		
		LOGGER.info("getTotalNumberOfGoat method called");
		
		long totalNumberOfGoat = goatRepository.count();
		
		LOGGER.info("Total number of goats: {}",totalNumberOfGoat);
		
		return totalNumberOfGoat;
	}

	
	//Get Goat REST API {id}
	@Override
	public GoatDto getGoatById(Long goatId) {
		
		LOGGER.info("getGoatById method called with goatId: {}", goatId);
		
		Goat goat = goatRepository.findById(goatId)
				.orElseThrow(() -> new ResourceNotFoundException("Goat is not exists with given id" + goatId));
		
		LOGGER.info("Found goat with id: {}", goatId);
		
		return GoatMapper.mapToGoatDto(goat);
	}
	
	//Get Goat REST API
	@Override
	public List<GoatDto> getAllGoat() {
		
		LOGGER.info("All goats are taken");
		
		List<Goat> goats =  goatRepository.findAll();
		
		LOGGER.info("All goats were received successfully");
		
		return goats.stream().map((goat) -> GoatMapper.mapToGoatDto(goat))
				.collect(Collectors.toList());
	}
	
	
	//Update Goat REST API
	@Override
	public GoatDto updateGoat(Long goatId, GoatDto updatedGoat) {
		
		LOGGER.info("updateGoat method called with goatId: {} and updatedGoat: {}", goatId, updatedGoat);
		
		Goat goat = goatRepository.findById(goatId)
				.orElseThrow(() -> new ResourceNotFoundException("Goat is not exists with given id" + goatId));

		LOGGER.debug("Found goat with id: {}", goatId);
		
		goat.setAge(updatedGoat.getAge());
		goat.setGender(updatedGoat.getGender());
		goat.setType(updatedGoat.getType());
		goat.setWeight(updatedGoat.getWeight());
		
		Goat newGoatUpdate = goatRepository.save(goat);
		
		LOGGER.info("Goat updated successfully: {}", newGoatUpdate);
		
		return GoatMapper.mapToGoatDto(newGoatUpdate);
	}

	//Delete Goat REST API 
	@Override
	public void deleteGoat(Long goatId) {
		
		LOGGER.info("deleteGoat method called with goatId: {}", goatId);
		
		Goat goat = goatRepository.findById(goatId)
				.orElseThrow(() -> new ResourceNotFoundException("Goat is not exists with given id" + goatId));
		
		LOGGER.debug("Found goat with id: {}", goatId);
		
		goatRepository.deleteById(goatId);
	
		LOGGER.info("Goat with id {} deleted successfully", goatId);
	}

	
	//Post Goat REST API 
	@Override
	public GoatDto createGoat(GoatDto goatDto) {
		
		LOGGER.info("createGoat method called with goatDto: {}",goatDto);
		
		Goat goat = GoatMapper.mapToGoat(goatDto);
		
		LOGGER.debug("Mapping GoatDto to Goat: {}", goat);
		
		Goat savedGoat = goatRepository.save(goat);
		
		LOGGER.info("Goat saved successfully: {}", savedGoat);
		
		return GoatMapper.mapToGoatDto(savedGoat);
	}


	


	

	

}
