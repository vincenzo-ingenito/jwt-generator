package enums;

 
public enum JWTAuthEnum {
	
	ALG("alg"),
	 
	TYP("typ"),
 
	X5C("x5c"),
 
	SUB("sub"),
 
	SUBJECT_ROLE("subject_role"),
 
	PURPOSE_OF_USE("purpose_of_use"),
 
	ISS("iss"),
	 
	LOCALITY("locality"),
 
	SUBJECT_ORGANIZATION_ID("subject_organization_id"),
	 
	SUBJECT_ORGANIZATION("subject_organization"),
 
	AUD("aud"),
 
	PATIENT_CONSENT("patient_consent"),

	ACTION_ID("action_id"),
 
	RESOURCE_HL7_TYPE("resource_hl7_type"),
 
	JTI("jti"),

	PERSON_ID("person_id"),
 
	IAT("iat"),
 
	EXP("exp"),
 
	JWT("JWT"),
	  
	ATTACHMENT_HASH("attachment_hash"),
 
	PEM_PATH("pem_path"),
 
	P12_PATH("p12_path");

	 
	private String key;
 

	 
	private JWTAuthEnum(String inKey) {
		key = inKey;
	}
	 
	public String getKey() {
		return key;
	}


}
