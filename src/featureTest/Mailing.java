//메일
package featureTest;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailing {
   public static void main(String[] args) {
      Mailing mailing = new Mailing();
      mailing.send();
   }
   
   public void send() {
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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("sjy019254@naver.com"));
            message.setSubject("[테스트]메일제목");
            message.setText("원빈 윤빈 현빈 조빈... 아름다운 당신.");
            Transport.send(message);
            System.out.println("Success Message Send");
         } catch (MessagingException e) {
            e.printStackTrace();
         }
   }
}