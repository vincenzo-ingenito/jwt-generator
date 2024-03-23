package utility;

import java.io.ByteArrayInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.util.Enumeration;

public class CertificateUtility {
 
	public static Key extractKeyByAliasFromP12(char[] password, byte[] p12) throws Exception {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(p12)) {
			java.security.KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
			keyStore.load(bais, password);
			Enumeration<String> aliases = keyStore.aliases();
			while (aliases.hasMoreElements()) {
				String a = aliases.nextElement();
				if (keyStore.isKeyEntry(a)) {
					return keyStore.getKey(a, password);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
}
