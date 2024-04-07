/*
 * Copyright 2024 vincenzoingenito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
