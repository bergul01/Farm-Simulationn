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

import farmApp.farmbackend.business.imp.SheepService;
import farmApp.farmbackend.dto.SheepDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/sheep")
public class SheepController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SheepController.class);
	
	private SheepService sheepService;
	
	@GetMapping("/count") //getCount
	public long getTotalNumberOfSheep() {
		
		LOGGER.info("getTotalNumberOfSheep Request received for method");
		
		long totalCount = sheepService.getTotalNumberOfSheep();
		
		LOGGER.info("The operation of the getTotalNumberOfSheeps method is completed. Total number of sheeps: {}",totalCount);
		
		return totalCount;
	}
	
	@GetMapping("{id}") //getId
	public ResponseEntity<SheepDto> getSheepById(@PathVariable("id") Long sheepId){
		
		LOGGER.info("getSheepById Request for method received. ID: {}",sheepId);
		
		SheepDto sheepDto = sheepService.getSheepById(sheepId);
		
		LOGGER.info("getSheepById method operation is completed. Rotating sheep DTO: {}",sheepDto);
		
		return ResponseEntity.ok(sheepDto);
	}
	
	@GetMapping() //getAll
	public ResponseEntity<List<SheepDto>> getAllSheeps(){
		
		LOGGER.info("All sheeps requested.");
		
		List<SheepDto> sheep = sheepService.getAllSheeps();
		
		LOGGER.debug("Retrieved {} sheeps.", sheep.size());
		
		return ResponseEntity.ok(sheep);
	}
	
	@PutMapping("{id}") //updateId
	public ResponseEntity<SheepDto> updateSheep (@PathVariable("id") Long sheepId, @RequestBody SheepDto updatedSheep){
		
		LOGGER.info("updateSheep API method called with sheepId: {} and updatedSheep: {}", sheepId, updatedSheep);
		
		SheepDto sheepDto = sheepService.updateSheep(sheepId, updatedSheep);
		
		LOGGER.info("Sheep updated successfully: {}", sheepDto);
		
		return ResponseEntity.ok(sheepDto);
		
	}
	
	
	@DeleteMapping("{id}") //deleteId
	public ResponseEntity<String> deleteSheep (@PathVariable("id") Long sheepId){
		
		LOGGER.info("deleteSheep API method called with sheepId: {}", sheepId);
		
		sheepService.deleteSheep(sheepId);
		
		LOGGER.info("Sheep with id {} deleted successfully", sheepId);
		
		return ResponseEntity.ok("Sheep deleted successfully");
	}
	
	
	@PostMapping() //create
	public ResponseEntity<SheepDto> createSheep(@RequestBody SheepDto sheepDto){
		
		LOGGER.info("createSheep API method called with SheepDto: {}", sheepDto);
		
		SheepDto savedSheep = sheepService.createSheep(sheepDto);
		
		LOGGER.info("Sheep created successfully: {}", savedSheep);
		
		return new ResponseEntity<>(savedSheep,HttpStatus.CREATED);
		
		
	}
}
