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
package enums;

 
public enum JWTClaimsEnum {
	 
	ALG("alg"),
	 
	TYP("typ"),
 
	X5C("x5c"),
 
	SUB("sub"),
 
	SUBJECT_ROLE("subject_role"),
 
	PURPOSE_OF_USE("purpose_of_use"),
 
	ISS("iss"),
 
	SUBJECT_APPLICATION_ID("subject_application_id"),
	 
	SUBJECT_APPLICATION_VENDOR("subject_application_vendor"),
	 
	SUBJECT_APPLICATION_VERSION("subject_application_version"),
 
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
 
	FILE_HASH("file_hash");
	 
	private String key;

	 
	/**
	 * Constructor.
	 * 
	 * @param inKey	key
	 */
	private JWTClaimsEnum(String inKey) {
		key = inKey;
	}
	
	/**
	 * Getter key.
	 * 
	 * @return	key
	 */
	public String getKey() {
		return key;
	}
 

}
