package farmApp.farmbackend.mapper;

import farmApp.farmbackend.dto.ChickenDto;
import farmApp.farmbackend.entities.Chicken;

public class ChickenMapper {
	
	public static ChickenDto mapToChickenDto(Chicken chicken) {
		
		return new ChickenDto(
				chicken.getId(),
				chicken.getType(),
				chicken.getGender(),
				chicken.getAge(),
				chicken.getWeight()
				);
	}
	
	public static Chicken mapToChicken(ChickenDto chickenDto) {
		
		return new Chicken(
				chickenDto.getId(),
				chickenDto.getType(),
				chickenDto.getGender(),
				chickenDto.getAge(),
				chickenDto.getWeight()
				);
	}

}


