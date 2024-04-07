# FSE JWT-GENERATOR

- [Installazione](#installazione)
- [Utilizzo](#utilizzo)
- [Licenza](#licenza)

## Installazione

Per utilizzare questa libreria nel tuo progetto Maven, aggiungi la seguente dipendenza al tuo file `pom.xml`:

```xml
<dependency>
    <groupId>io.github.vincenzo-ingenito</groupId>
    <artifactId>jwt-generator</artifactId>
    <version>LAST_VERSION</version>
</dependency>
```

## Utilizzo
Dopo aver ottenuto il byte array della chiave privata ed il byte array della chiave pubblica ottenuta, è possibile invocare il metodo statico ```getTokens``` della classe ```JwtUtility```.
Di seguito viene mostrato un esempio di chiamata:

```

import enums.ActionEnum;
import enums.PurposeOfUseEnum;

public class Launcher {

	public static void main(String[] args) throws Exception {
		byte[] privKey = Utility.getFileFromFS("privKey.key");
		byte[] pem = Utility.getFileFromFS("cert.pem");
		
		TokenRequestDTO request = new TokenRequestDTO();
		request.setAction_id(ActionEnum.CREATE);
		request.setAud("aud");
		request.setDurationHours(24);
		request.setFile_hash(null);
		request.setIss("iss");
		request.setJti("jti");
		request.setLocality("locality");
		request.setPatient_consent(true);
		request.setPerson_id("person_id");
		request.setPurpose_of_use(PurposeOfUseEnum.ADMINISTRATIVE);
		request.setResource_hl7_type("resource_hl7_type");
		request.setSub("sub");
		request.setSubject_application_id("subject_application_id");
		request.setSubject_application_vendor("Subject_application_vendor");
		request.setSubject_application_version("Subject_application_version");
		request.setSubject_organization("subject_organization");
		request.setSubject_organization_id("subject_organization_id");
		request.setSubject_role("subject_role");
		
		TokenResponseDTO responseDTO = JwtUtility.getTokens(request, privKey, pem);
		System.out.println(responseDTO.getAuthorizationBearer());
		System.out.println(responseDTO.getFseJwtSignature());
	}
}
```

Dopo aver ottenuto i token è possibile validarli utilizzando il metodo statico ```validate``` della classe ```JwtUtility```.
Di seguito viene mostrato un esempio di chiamata:

```

import enums.ActionEnum;
import enums.PurposeOfUseEnum;

public class Launcher {

	public static void main(String[] args) throws Exception {
		byte[] privKey = Utility.getFileFromFS("privKey.key");
		byte[] pem = Utility.getFileFromFS("cert.pem");
		
		TokenRequestDTO request = new TokenRequestDTO();
		request.setAction_id(ActionEnum.CREATE);
		request.setAud("aud");
		request.setDurationHours(24);
		request.setFile_hash(null);
		request.setIss("iss");
		request.setJti("jti");
		request.setLocality("locality");
		request.setPatient_consent(true);
		request.setPerson_id("person_id");
		request.setPurpose_of_use(PurposeOfUseEnum.ADMINISTRATIVE);
		request.setResource_hl7_type("resource_hl7_type");
		request.setSub("sub");
		request.setSubject_application_id("subject_application_id");
		request.setSubject_application_vendor("Subject_application_vendor");
		request.setSubject_application_version("Subject_application_version");
		request.setSubject_organization("subject_organization");
		request.setSubject_organization_id("subject_organization_id");
		request.setSubject_role("subject_role");
		
		TokenResponseDTO responseDTO = JwtUtility.getTokens(request, privKey, pem);
		System.out.println(responseDTO.getAuthorizationBearer());
		System.out.println(responseDTO.getFseJwtSignature());

        boolean valid = JwtUtility.validate(responseDTO.getFseJwtSignature(), pem);
		System.out.println(valid);
	}
}
```
## Licenza

Questo progetto è distribuito sotto la [Licenza Apache, Versione 2.0](http://www.apache.org/licenses/LICENSE-2.0).
