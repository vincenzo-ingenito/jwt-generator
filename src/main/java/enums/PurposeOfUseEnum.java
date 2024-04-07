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

import lombok.Getter;

public enum PurposeOfUseEnum {

	TREATMENT("TREATMENT","Trattamento di cura ordinario"),
	EMERGENCY("EMERGENCY","Trattamento in emergenza"),
	PUBEMERGENCY("PUBEMERGENCY","Trattamento per la salvaguardia di un terzo o della collettività"),
	SYSADMIN("SYSADMIN","Trasferimento del FSE"),
	PERSONAL("PERSONAL","Consultazione del FSE"),
	UPDATE("UPDATE","Invalidamento e aggiornamento di un documento"),
	CONSENT("CONSENT","Comunicazione valori consensi"),
	ADMINISTRATIVE("ADMINISTRATIVE","Operazioni amministrative");

	@Getter
	private String display;

	@Getter
	private String description;

	private PurposeOfUseEnum(String inDisplay, String inDescription) {
		display = inDisplay;
		description = inDescription;
	}

	public static PurposeOfUseEnum get(String inDisplay) {
		PurposeOfUseEnum out = null;
		for (PurposeOfUseEnum v:PurposeOfUseEnum.values()) {
			if (v.getDisplay().equalsIgnoreCase(inDisplay)) {
				out = v;
				break;
			}
		}
		return out;
	}
}