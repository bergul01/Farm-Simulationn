package farmApp.farmbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GoatDto {
	
	
	private long id;
	private String type;
	private String gender;
	private double age;
	private double weight;

}
