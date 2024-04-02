package farmApp.farmbackend.mapper;

import farmApp.farmbackend.dto.SheepDto;
import farmApp.farmbackend.entities.Sheep;

public class SheepMapper {

	public static SheepDto mapToSheepDto (Sheep sheep) {
		return new SheepDto(
				sheep.getId(),
				sheep.getType(),
				sheep.getGender(),
				sheep.getAge(),
				sheep.getWeight()
				);
	}
	
	public static Sheep mapToGoat(SheepDto sheepDto) {
		return new Sheep(
				sheepDto.getId(),
				sheepDto.getType(),
				sheepDto.getGender(),
				sheepDto.getAge(),
				sheepDto.getWeight()
				);
	}
	
}
