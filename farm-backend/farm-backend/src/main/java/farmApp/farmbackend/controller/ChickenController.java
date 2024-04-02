package farmApp.farmbackend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import farmApp.farmbackend.business.imp.ChickenService;
import farmApp.farmbackend.dto.ChickenDto;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/chicken")
public class ChickenController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChickenController.class);
	
	private ChickenService chickenService;
	
	
	@GetMapping("/count") //getCount
	public  long getTotalNumberOfChickens() {
		
		LOGGER.info("getTotalNumberOfChickens Request received for method");
		
		long totalCount = chickenService.getTotalNumberOfChicken();
		
		LOGGER.info("The operation of the getTotalNumberOfChickens method is completed. Total number of chickens: {}",totalCount);
		
		return totalCount;
	}
	
	
	@GetMapping("{id}") //getId
	public ResponseEntity<ChickenDto> getChickenById(@PathVariable("id") Long chickenId){
		
		LOGGER.info("getChickenById Request for method received. ID: {}",chickenId);

		ChickenDto chickenDto = chickenService.getChickenById(chickenId);
		
		LOGGER.info("getChickenById method operation is completed. Rotating chicken DTO: {}",chickenDto);
		
		return ResponseEntity.ok(chickenDto);
		
	}
	
	
	@GetMapping() //getAll
	public ResponseEntity<List<ChickenDto>> getAllChicken(){
		
		LOGGER.info("All chickens requested.");
		
		List<ChickenDto> chicken = chickenService.getAllChickens();
		
		LOGGER.debug("Retrieved {} chickens.", chicken.size());
		
		return new ResponseEntity<>(chicken,HttpStatus.OK);
	}
	
	@PutMapping("{id}") //updateId
	public ResponseEntity<ChickenDto> updateChicken (@PathVariable("id") Long chickenId, @RequestBody ChickenDto updatedChicken){
		
		LOGGER.info("updateChicken API method called with chickenId: {} and updatedChicken: {}", chickenId, updatedChicken);
		
		ChickenDto chickenDto = chickenService.updateChicken(chickenId, updatedChicken);
		
		LOGGER.info("Chicken updated successfully: {}", chickenDto);
		
		return new ResponseEntity<>(chickenDto,HttpStatus.OK);
	}
	
	@DeleteMapping("{id}") //deleteId
	public ResponseEntity<String> deleteChicken (@PathVariable("id") Long chickendId){
		
		LOGGER.info("deleteChicken API method called with chickenId: {}", chickendId);
		
		chickenService.deleteChicken(chickendId);
		
		LOGGER.info("Chicken with id {} deleted successfully", chickendId);
		
		return ResponseEntity.ok("Chicken deleted successfully"); 
	}
	
	
	
	@PostMapping() //create
	public ResponseEntity<ChickenDto> createChicken(@RequestBody ChickenDto chickenDto){
		
		LOGGER.info("createChicken API method called with chickenDto: {}", chickenDto);
		
		ChickenDto savedChicken = chickenService.createChicken(chickenDto);
		
		LOGGER.info("Chicken created successfully: {}", savedChicken);
		
		return new ResponseEntity<>(savedChicken,HttpStatus.CREATED);
	}
	
	

}
