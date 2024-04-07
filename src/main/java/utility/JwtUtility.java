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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import enums.JWTClaimsEnum;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtility {

	private static final Logger LOGGER = Utility.getLogger(JwtUtility.class.getName());

	public static TokenResponseDTO getTokens(TokenRequestDTO requestDto, byte[] privateKey, byte[] pemByte) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		validateField(requestDto, privateKey, pemByte);
		RSAPrivateKey privKey = readPKCS8PrivateKey(privateKey);
		String pem = cleanPem(pemByte);
		Date iat = new Date();

		Integer duration = requestDto.getDurationHours()==null ? 24 : requestDto.getDurationHours();
		Date exp = Utility.addHoursToJavaUtilDate(iat, duration);

		String jwt = generateAuthJWT(requestDto, privKey, pem, iat, exp);
		String claimsJwt = generateSignatureJWT(requestDto, privKey, pem, iat, exp);

		return new TokenResponseDTO(jwt, claimsJwt);
	}


	private static RSAPrivateKey readPKCS8PrivateKey(byte[] key) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
		KeyFactory factory = KeyFactory.getInstance("RSA");

		PemObject pemObject = readPemObject(key);
		byte[] content = pemObject.getContent();
		PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
		return (RSAPrivateKey) factory.generatePrivate(privKeySpec);
	}


	public static PemObject readPemObject(byte[] data) throws IOException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		try (PemReader pemReader = new PemReader(inputStreamReader)) {
			return pemReader.readPemObject();
		}
	}


	private static void validateField(TokenRequestDTO requestDto, byte[] privateKey, byte[] pemByte) {

		if(requestDto == null) {
			throw new IllegalArgumentException("Required request");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getIss())) {
			throw new IllegalArgumentException("Required issuer");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getJti())) {
			throw new IllegalArgumentException("Required jti");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getAud())) {
			throw new IllegalArgumentException("Required aud");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getSub())) {
			throw new IllegalArgumentException("Required sub");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getSubject_organization_id())) {
			throw new IllegalArgumentException("Required subject_organization_id");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getSubject_organization())) {
			throw new IllegalArgumentException("Required subject_organization");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getLocality())) {
			throw new IllegalArgumentException("Required locality");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getSubject_role())) {
			throw new IllegalArgumentException("Required subject role");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getPerson_id())) {
			throw new IllegalArgumentException("Required person_id");
		}

		if(requestDto.getPurpose_of_use() == null) {
			throw new IllegalArgumentException("Required purpose_of_use");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getResource_hl7_type())) {
			throw new IllegalArgumentException("Required hl7_type");
		}

		if(requestDto.getAction_id() == null) {
			throw new IllegalArgumentException("Required action id");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getSubject_application_id())) {
			throw new IllegalArgumentException("Required subject_application_id");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getSubject_application_vendor())) {
			throw new IllegalArgumentException("Required subject_application_vendor");
		}

		if(StringUtility.isNullOrEmpty(requestDto.getSubject_application_version())) {
			throw new IllegalArgumentException("Required subject_application_version");
		}

		if(privateKey == null || privateKey.length == 0) {
			throw new IllegalArgumentException("Required priv key");
		}

		if(pemByte == null || pemByte.length == 0) {
			throw new IllegalArgumentException("Required pem");
		}


	}

	private static String cleanPem(byte[] pem) {
		LOGGER.info("Clean pem");
		return new String(pem).replace("-----BEGIN PUBLIC KEY-----", "").replaceAll(System.lineSeparator(), "")
				.replace("-----END PUBLIC KEY-----", "").replace("-----BEGIN CERTIFICATE-----", "")
				.replaceAll(System.lineSeparator(), "").replace("-----END CERTIFICATE-----", "").replace("\n", "");
	}


	/**
	 * Generate JWT.
	 * 
	 * @param mapJD             arguments map
	 * @param privateKey        private key
	 * @param x5c               public key
	 * @param iat               issuing time
	 * @param exp               expiring time
	 * @param pathFileToPublish file to hash
	 * @return jwt
	 * @throws Exception
	 */
	private static String generateAuthJWT(TokenRequestDTO requestDto, Key privateKey, String x5c, Date iat, Date exp) {
		Map<String, Object> headerParams = getGenericClaims(x5c, iat, exp);

		Map<String, Object> claims = new HashMap<>(); 
		claims.put(JWTClaimsEnum.ISS.getKey(), "auth:" + cleanIss(requestDto.getIss()));

		return Jwts.builder().setHeaderParams(headerParams).setClaims(claims).signWith(SignatureAlgorithm.RS256, privateKey).compact();
	}

	private static String generateSignatureJWT(TokenRequestDTO requestDto, Key privateKey, String x5c, Date iat, Date exp) throws NoSuchAlgorithmException {
		Map<String, Object> headerParams  = getGenericClaims(x5c, iat, exp);

		Map<String, Object> claims = new HashMap<>();
		claims.put(JWTClaimsEnum.ACTION_ID.getKey(), requestDto.getAction_id());
		claims.put(JWTClaimsEnum.AUD.getKey(), requestDto.getAud());
		claims.put(JWTClaimsEnum.ISS.getKey(), requestDto.getIss());
		claims.put(JWTClaimsEnum.JTI.getKey(), requestDto.getJti());
		claims.put(JWTClaimsEnum.LOCALITY.getKey(), requestDto.getLocality());
		claims.put(JWTClaimsEnum.PATIENT_CONSENT.getKey(), requestDto.isPatient_consent());
		claims.put(JWTClaimsEnum.PERSON_ID.getKey(), requestDto.getPerson_id());
		claims.put(JWTClaimsEnum.PURPOSE_OF_USE.getKey(), requestDto.getPurpose_of_use());
		claims.put(JWTClaimsEnum.RESOURCE_HL7_TYPE.getKey(), requestDto.getResource_hl7_type());
		claims.put(JWTClaimsEnum.SUB.getKey(), requestDto.getSub());
		claims.put(JWTClaimsEnum.SUBJECT_APPLICATION_ID.getKey(), requestDto.getSubject_application_id());
		claims.put(JWTClaimsEnum.SUBJECT_APPLICATION_VENDOR.getKey(), requestDto.getSubject_application_vendor());
		claims.put(JWTClaimsEnum.SUBJECT_APPLICATION_VERSION.getKey(), requestDto.getSubject_application_version());
		claims.put(JWTClaimsEnum.SUBJECT_ORGANIZATION.getKey(), requestDto.getSubject_organization());
		claims.put(JWTClaimsEnum.SUBJECT_ORGANIZATION_ID.getKey(), requestDto.getSubject_organization_id());
		claims.put(JWTClaimsEnum.SUBJECT_ROLE.getKey(), requestDto.getSubject_role());
		if (Utility.isPdf(requestDto.getPdf())) {
			String hash = Utility.encodeSHA256(requestDto.getPdf());
			claims.put(JWTClaimsEnum.FILE_HASH.getKey(), hash);
		}
		claims.put(JWTClaimsEnum.ISS.getKey(), "integrity:" + cleanIss(requestDto.getIss()));
		return Jwts.builder().setHeaderParams(headerParams).setClaims(claims).signWith(SignatureAlgorithm.RS256, privateKey).compact();
	}


	/**
	 * Clean ISS.
	 * 
	 * @param iss
	 * @return iss cleaned
	 */
	private static String cleanIss(final String iss) {
		if (iss == null)
			return null;
		return iss.replaceFirst("integrity:", "").replaceFirst("auth:", "");
	}


	private static Map<String, Object> getGenericClaims(final String x5c, final Date iat, final Date exp){
		Map<String, Object> obj = new HashMap<>();
		obj.put(JWTClaimsEnum.ALG.getKey(), SignatureAlgorithm.RS256);
		obj.put(JWTClaimsEnum.TYP.getKey(), JWTClaimsEnum.JWT.getKey());
		obj.put(JWTClaimsEnum.X5C.getKey(), Arrays.asList(x5c).toArray());
		obj.put(JWTClaimsEnum.IAT.getKey(), iat.getTime() / 1000);
		obj.put(JWTClaimsEnum.EXP.getKey(), exp.getTime() / 1000);
		return obj;
	}

	/**
	 * Validate JWT.
	 * 
	 * @param jwt jwt to validate
	 * @param pem perm certificate
	 * @return flag
	 */
	public static boolean validate(String jwt, byte[] pem) {
		boolean out = true;
		try {
			final RSAPublicKey key = getPublicKey(pem);
			final Algorithm algorithm = Algorithm.RSA256(key, null);
			final DecodedJWT decodedJWT = JWT.decode(jwt);
			algorithm.verify(decodedJWT);
		} catch (Exception e) {
			out = false;
		}
		return out;
	}

	/**
	 * Get public key.
	 * 
	 * @param pem pem
	 * @return public key
	 * @throws Exception
	 */
	private static RSAPublicKey getPublicKey(byte[] pem) throws CertificateException {
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
		InputStream in = new ByteArrayInputStream(pem);
		X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(in);
		return (RSAPublicKey) certificate.getPublicKey();
	}



}