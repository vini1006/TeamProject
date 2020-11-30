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
	
	//public static void main(String[] args) {
	
//	Mailing mailing = new Mailing(mail);
//		mailing.send(mail,);
	//}
	
   
   public void send(String askEmail,String pass) {
      String host = "smtp.gmail.com";
         String user = "viniyoon1006@gmail.com";
         String password = "dbsqls2382#@!#@!";
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
            message.setSubject("[미니지]비밀번호 찾기 결과입니다.");
            message.setText("다음부터는 신경좀 쓰세요 \n비밀번호 :"+pass);
            Transport.send(message);
            System.out.println("Success Message Send");
         } catch (MessagingException e) {
            e.printStackTrace();
         }
   }
}