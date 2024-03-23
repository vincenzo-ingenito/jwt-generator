package utility;

import lombok.Data;

@Data
public class TokenRequestDTO {
	private String config;
	
	private int durationHours = 24;
	
	private byte[] privKey;
	
	private byte[] pem;
	
	private byte[] fileToHash;
	
	 
}
