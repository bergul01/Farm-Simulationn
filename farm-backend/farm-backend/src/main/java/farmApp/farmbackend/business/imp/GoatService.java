package farmApp.farmbackend.business.imp;

import java.util.List;

import farmApp.farmbackend.dto.GoatDto;



public interface GoatService {
	
	
	GoatDto getGoatById(Long goatId);
	
	List<GoatDto> getAllGoat();
	
	GoatDto updateGoat(Long goatId, GoatDto updatedGoat);
	
	void deleteGoat(Long goatId);
	
	GoatDto createGoat(GoatDto goatDto);
	
	long getTotalNumberOfGoat();

}
