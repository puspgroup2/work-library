package handlers;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
public class MailHandler 
{
	/*
	IN ORDER FOR THIS CLASS TO FUNCTION, TWO EXTERNAL JARS ARE REQUIRED.
	ADD ALL JARS IN THE "resources" FOLDER TO CLASSPATH (SIMILARLY TO HOW servlet-api.jar WAS ADDED).
	 */
	private final String senderEmail = "puspmailgroup2@gmail.com";
	private final String senderPassword = "_7xYxeY-9c$7by.r%/aL";

	public MailHandler() {
	}
	
	public void sendPasswordMail(String recipient, String username, String password) {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Your TimeMate login credentials");
            message.setText("Your login credentials are:" + "\n" + "Username: " + username + "\n" + "Password: " + password);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
}
