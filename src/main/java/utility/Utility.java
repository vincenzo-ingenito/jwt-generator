package utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Hex;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
 
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utility {

	private static final Logger LOGGER = Utility.getLogger(Utility.class.getName());

	/**
	 * Chunk size file.
	 */
	private static final int CHUNK_SIZE = 16384;
 
 

	/**
	 * Get file from file system.
	 * 
	 * @param filename		path file
	 * @return				content of the file
	 * @throws Exception
	 */
	public static byte[] getFileFromFS(final String filename) throws Exception {
		byte[] b = null;
		try (InputStream is = new FileInputStream(new File(filename))) {
			try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();) {
				int nRead;
				byte[] data = new byte[CHUNK_SIZE];
				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();
				b = buffer.toByteArray();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			LOGGER.info(String.format("Error while retrieving file from fs: %s", e));
		}
		return b;
	}

	/**
	 * Add hours to a date.
	 * 
	 * @param date	date
	 * @param hours	hours to add
	 * @return		updated date
	 */
	public static Date addHoursToJavaUtilDate(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();
	}

	/**
	 * Check if the magic number of the file is "%PDF".
	 * 
	 * @param pdf	file to check
	 * @return		flag
	 */
	public static boolean isPdf(byte[] pdf) {
		boolean out = false;
		if (pdf!=null && pdf.length >4) {
			byte[] magicNumber = Arrays.copyOf(pdf, 4);
			String strMagicNumber = new String(magicNumber);
			out = strMagicNumber.equals("%PDF");
		}
		return out;
	}

	/**
	 * Encode object in sha 256 and then in hexadecimal.
	 * 
	 * @param objectToEncode	object to encode
	 * @return					encoded object
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodeSHA256(byte[] objectToEncode) throws NoSuchAlgorithmException {
		final MessageDigest digest = MessageDigest.getInstance("SHA-256");
		return Hex.encodeHexString(digest.digest(objectToEncode));
	}
 

	public static Logger getLogger(String className) {
		final Logger logger = Logger.getLogger(className);
		final ConsoleHandler consoleHandler = new ConsoleHandler();

		final Formatter f = new Formatter() {
			@Override
			public String format(LogRecord logRecord) {
				return logRecord.getMessage() + "\n"; // Printing only message
			}
		};

		consoleHandler.setFormatter(f);
		logger.addHandler(consoleHandler);
		logger.setUseParentHandlers(false);
		return logger;
	}
}