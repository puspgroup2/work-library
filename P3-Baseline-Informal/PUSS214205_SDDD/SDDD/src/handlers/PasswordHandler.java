package handlers;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Random;

public class PasswordHandler {
	
	/**
	 * Hashes the provided password string with the provided salt string using SHA-256 
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException if the given cryptographic algorithm is not available in the environment
	 */
	public String hashPassword(String password, String salt) {
		String hashedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			//Converts the String salt to byte[] and configures the hash function with the salt string
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			//Converts the password string to byte[] and then hashes it using SHA-256
			byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
			//Encodes the bytes array to String
			hashedPassword = Base64.getEncoder().encodeToString(bytes);
		}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashedPassword;
	}
	
	/**
	 * Generates a new random password according to requirement 6.2.1 in PUSS214201
	 * @return
	 */
	public String generatePassword() {
        ArrayList<Character> passwordChars = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
		final char[] lowerCase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		final char[] upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		final char[] numbers = "1234567890".toCharArray();
		Random rand = new SecureRandom();
		
		passwordChars.add(0, lowerCase[rand.nextInt(lowerCase.length)]);
		passwordChars.add(1, lowerCase[rand.nextInt(lowerCase.length)]);
		passwordChars.add(2, lowerCase[rand.nextInt(lowerCase.length)]);

		passwordChars.add(3, upperCase[rand.nextInt(upperCase.length)]);
		passwordChars.add(4, upperCase[rand.nextInt(upperCase.length)]);
		passwordChars.add(5, upperCase[rand.nextInt(upperCase.length)]);

		passwordChars.add(6, numbers[rand.nextInt(numbers.length)]);
		passwordChars.add(7, numbers[rand.nextInt(numbers.length)]);

		Collections.shuffle(passwordChars);
		
		 for(Character c : passwordChars) {
		 	sb.append(c);
		 }
		return sb.toString();
	}
}
