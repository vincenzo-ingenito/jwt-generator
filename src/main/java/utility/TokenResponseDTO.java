package utility;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseDTO {

	private String authorizationBearer;
	
	private String fseJwtSignature;
	 
}
