package handlers;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class PasswordHandler {
	
	public String hashPassword(String password) {
		String hashedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
		    for(int i=0; i < bytes.length; i++){
		    	sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		    }
		    hashedPassword = sb.toString();
		}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashedPassword;
	}
	
	public String generatePassword() {
		ArrayList<Character> passwordChars = new ArrayList<>(7);
		final char[] lowerCase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		final char[] upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		final char[] numbers = "1234567890".toCharArray();
		
		//final String[] upperCase = new String[] {"ABCDEFGHIJKLMNOPQRSTUVWXYZ"};
		//final String[] numbers = new String[] {"0123456789"};
		Random rand = new SecureRandom();
		
		for (int i = 0; i < passwordChars.size(); i++) {
		int randInt = rand.nextInt(2);
		
		switch(randInt) {
			case 0: passwordChars.add(lowerCase[rand.nextInt(lowerCase.length)]);
					break;
			case 1:	passwordChars.add(upperCase[rand.nextInt(upperCase.length)]);
					break;
			case 2: passwordChars.add(numbers[rand.nextInt(numbers.length)]);
					break;
			}
		}
		Collections.shuffle(passwordChars);
		StringBuilder sb = new StringBuilder();
		for(Character c : passwordChars) {
			sb.append(c);
		}
		return sb.toString();
	}
}
