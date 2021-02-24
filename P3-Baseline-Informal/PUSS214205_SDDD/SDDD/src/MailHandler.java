
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
public class MailHandler {
	public final String senderEmail = "testingemailsender44@gmail.com";
    public final String password = "dogsandcats123";
    private Properties prop;
	
	public MailHandler() {
		this.prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
	}
	
	public boolean sendPasswordMail(String recipient, String username, String password) {
		Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("testingemailsender44@gmail.com", "dogsandcats123");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("TestingEmailSender44@gmail.com, sebastian.david.forslund@gmail.com")
            );
            message.setSubject("Your TimeMate Login Credentials");
            message.setText("Your login credentials are:" + "\n" + "Username: " + username + "\n" + "Password: " + password);

            Transport.send(message);

            return true;

        } catch (MessagingException e) {
        	System.out.println(e.toString());
        	return false;
        }
	}
}
