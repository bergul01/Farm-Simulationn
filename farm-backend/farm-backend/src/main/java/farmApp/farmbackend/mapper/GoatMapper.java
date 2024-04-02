package farmApp.farmbackend.mapper;

import farmApp.farmbackend.dto.GoatDto;
import farmApp.farmbackend.entities.Goat;

public class GoatMapper {
	
	public static GoatDto mapToGoatDto(Goat goat) {
		return new GoatDto(
				goat.getId(),
				goat.getType(),
				goat.getGender(),
				goat.getAge(),
				goat.getWeight()
				);
	}
	
	public static Goat mapToGoat(GoatDto goatDto) {
		return new Goat(
				goatDto.getId(),
				goatDto.getType(),
				goatDto.getGender(),
				goatDto.getAge(),
				goatDto.getWeight()
				);
	}

}
