//메일
package features;


import java.util.Properties; 

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JTextField;

public class Mailing {
	
	static String mail;
	
	public Mailing()
	{
		mail = "";				
	}
	
	public Mailing(String s)
	{
		mail = s;	
	}
	
	public static void main(String[] args) {
		Mailing mailing = new Mailing(mail);
		mailing.send(mail);
	}
	
   
   public void send(String askEmail) {
      String host = "smtp.gmail.com";
         String user = "sjh581347@gmail.com";
         String password = "seojin1326*";
         Properties props = new Properties();
         props.put("mail.smtp.host", host);
         props.put("mail.smtp.port", 465);
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.ssl.enable", "true"); 
         props.put("mail.smtp.ssl.trust", "smtp.gmail.com");


         Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(user, password);
            }
         });
         try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(askEmail));
            message.setSubject("[테스트]메일제목");
            message.setText("원빈 윤빈 현빈 조빈... 아름다운 당신.");
            Transport.send(message);
            System.out.println("Success Message Send");
         } catch (MessagingException e) {
            e.printStackTrace();
         }
   }
}