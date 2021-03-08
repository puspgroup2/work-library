package handlers;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailHandler {
	/*
	 * IN ORDER FOR THIS CLASS TO FUNCTION, TWO EXTERNAL JARS ARE REQUIRED. ADD ALL
	 * JARS IN THE "resources" FOLDER TO CLASSPATH .
	 */
	private final String senderEmail = "puspmailgroup2@gmail.com";
	private final String senderPassword = "_7xYxeY-9c$7by.r%/aL";
	private Properties prop_;

	public MailHandler() {
		this.prop_ = new Properties();
		prop_.put("mail.smtp.host", "smtp.gmail.com");
		prop_.put("mail.smtp.port", "587");
		prop_.put("mail.smtp.auth", "true");
		prop_.put("mail.smtp.starttls.enable", "true"); // TLS
	}

	/**
	 * Send password to the recipient's e-mail.
	 * 
	 * @param recipient The e-mail of recipient.
	 * @param username  recipient's username.
	 * @param password  recipient's password.
	 */
	public void sendPasswordMail(String recipient, String username, String password) {
		// Recipient's email ID needs to be mentioned.
		String to = recipient;

		// Sender's email ID needs to be mentioned
		String from = senderEmail;

		// Assuming you are sending email from through gmails smtp
		String host = "smtp.gmail.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(senderEmail, senderPassword);

			}

		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Your TimeMate login credentials");
			message.setText(
					"Your login credentials are:" + "\n" + "Username: " + username + "\n" + "Password: " + password);
			Transport.send(message);
		} catch (MessagingException exception) {
			exception.printStackTrace();
		}
	}
}
