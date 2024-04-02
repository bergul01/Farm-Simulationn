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

import farmApp.farmbackend.business.imp.GoatService;
import farmApp.farmbackend.dto.GoatDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/goat")
public class GoatController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoatController.class);
	
	private GoatService goatService;
	
	@GetMapping("/count") //getCount
	public long getTotalNumberOfGoat() {
		
		LOGGER.info("getTotalNumberOfGoat Request received for method");
		
		long totalCount = goatService.getTotalNumberOfGoat();
		
		LOGGER.info("The operation of the getTotalNumberOfGoat method is completed. Total number of goats: {}",totalCount);
		
		return totalCount;
	}
	
	@GetMapping("{id}") //getId
	public ResponseEntity<GoatDto> getGoatById(@PathVariable("id") Long goatId){
		
		LOGGER.info("getGoatById Request for method received. ID: {}",goatId);
		
		GoatDto goatDto = goatService.getGoatById(goatId);
		
		LOGGER.info("getGoatById method operation is completed. Rotating goat DTO: {}",goatDto);
		
		return ResponseEntity.ok(goatDto);
	}
	
	@GetMapping() //getAll
	public ResponseEntity<List<GoatDto>> getAllGoat(){
		
		LOGGER.info("All goats requested.");
		
		List<GoatDto> goat =  goatService.getAllGoat();
		
		LOGGER.debug("Retrieved {} goats.", goat.size());
		
		return ResponseEntity.ok(goat);
	}
	
	@PutMapping("{id}") //updateId
	public ResponseEntity<GoatDto> updateGoat(@PathVariable("id") Long goatId, @RequestBody GoatDto updatedGoat){
		
		LOGGER.info("updateGoat API method called with goatId: {} and updatedGoat: {}", goatId, updatedGoat);
		
		GoatDto goatDto = goatService.updateGoat(goatId, updatedGoat); 
		
		LOGGER.info("Goat updated successfully: {}", goatDto);
		
		return ResponseEntity.ok(goatDto);
		
	}
	@DeleteMapping("{id}") //deleteId
	public ResponseEntity<String> deleteGoat(@PathVariable("id") Long goatId){
		
		LOGGER.info("deleteGoat API method called with goatId: {}", goatId);
		
		goatService.deleteGoat(goatId);
		
		LOGGER.info("Goat with id {} deleted successfully", goatId);
		
		return ResponseEntity.ok("Goat deleted successfully");
	}
	
	@PostMapping() //create
	public ResponseEntity<GoatDto> createGoat(@RequestBody GoatDto goatDto){
		
		LOGGER.info("createGoat API method called with goatDto: {}", goatDto);
		
		GoatDto savedGoat = goatService.createGoat(goatDto);
		
		LOGGER.info("Goat created successfully: {}", savedGoat);
		
		return new ResponseEntity<>(savedGoat,HttpStatus.CREATED);
		
	}
	

}
