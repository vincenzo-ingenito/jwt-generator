package utility;

import enums.ActionEnum;
import enums.PurposeOfUseEnum;
import lombok.Data;

@Data
public class TokenRequestDTO {

	private Integer durationHours;

	private String sub;
	
	private String subject_role;
	
	private PurposeOfUseEnum purpose_of_use;
	
	private String iss;
	
	private String subject_application_id;
	
	private String subject_application_vendor;
	
	private String subject_application_version;
	
	private String locality;
	
	private String subject_organization_id;
	
	private String subject_organization;
	
	private String aud;
	
	private boolean patient_consent;
	
	private ActionEnum action_id;
	
	private String resource_hl7_type;
	
	private String jti;
	
	private String person_id;
	
	private byte[] pdf;

}
