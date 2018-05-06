package il.ac.hit.controller;

import java.util.Properties;  

import javax.mail.*;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  
  
public class Mailer {  
public static void send(String to, String userName){  
final String subject = "Welcome ";
String msg = "Welcome for To Do List website, here you can access you to do list and manage it. "
		+"\n"+ "Thank you for joining. enjoy!"
		+"\n"+"Regards, Linoy.";

  



//1st step) Get the session object    
Properties properties = new Properties();  
properties.put("mail.smtp.starttls.enable", "true");
properties.put("mail.smtp.host", "smtp.gmail.com");
properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
properties.put("mail.smtp.auth", "true");
//properties.put("mail.smtp.port", "587");
properties.put("mail.smtp.port", "465");
properties.put("mail.debug", "true");
properties.put("mail.transport.protocol", "smtp");
properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
properties.put("mail.smtp.socketFactory.fallback", "false");
properties.setProperty("mail.smtp.quitwait", "false");



Session session = Session.getInstance(properties,  
 new javax.mail.Authenticator() {  
  protected PasswordAuthentication getPasswordAuthentication() {  
   return new PasswordAuthentication("todolistweb@gmail.com","linoytodo1234");  
   }  
});  
//2nd step)compose message  
try {  
 MimeMessage message = new MimeMessage(session);  
 message.setFrom(new InternetAddress("todolistweb@gmail.com"));  
 message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));  
 message.setSubject(subject+ userName+ "!");  
 message.setText(msg);  

 //3rd step)send message  
 Transport.send(message);  
  
 System.out.println("Message sent.");
 
 } catch (MessagingException e) {  
    throw new RuntimeException(e);  
 }  
      
}  
}  