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