import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
public class MailHandler 
{
	/*
	IN ORDER FOR THIS CLASS TO FUNCTION, TWO EXTERNAL JARS ARE REQUIRED.
	ADD ALL JARS IN THE "resources" FOLDER TO CLASSPATH (SIMILARLY TO HOW servlet-api.jar WAS ADDED).
	*/
	private final String senderEmail = "testingemailsender44@gmail.com";
    private final String senderPassword = "dogsandcats123";
    private Properties prop_;
	public MailHandler() {
		this.prop_ = new Properties();
        prop_.put("mail.smtp.host", "smtp.gmail.com");
        prop_.put("mail.smtp.port", "587");
        prop_.put("mail.smtp.auth", "true");
        prop_.put("mail.smtp.starttls.enable", "true"); //TLS
	}
	public void sendPasswordMail(String recipient, String username, String password) {
		Session session = Session.getInstance(prop_,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("senderEmail", "senderPassword");
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("senderEmail, recipient")
            );
            message.setSubject("Your TimeMate Login Credentials");
            message.setText("Your login credentials are:" + "\n" + "Username: " + username + "\n" + "Password: " + password);
            Transport.send(message);
        } catch (MessagingException e) {
        	System.out.println(e.toString());
        }
	}
}
