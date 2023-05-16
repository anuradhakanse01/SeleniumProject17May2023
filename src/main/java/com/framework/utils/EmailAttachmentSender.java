package com.framework.utils;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.framework.selenium.api.base.DriverInstance;
 
//to attach report in email
public class EmailAttachmentSender extends DriverInstance{
 
    public static void sendEmailWithAttachments(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message, String[] attachFiles)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
      //  properties.put("mail.smtp.auth", false);
       // properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
      //  properties.put("mail.password", password);
 
        // creates a new session with an authenticator
		/*
		 * Authenticator auth = new Authenticator() { public PasswordAuthentication
		 * getPasswordAuthentication() { return new PasswordAuthentication(userName,
		 * password); } };
		 */
        Session session = Session.getInstance(properties, null);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress("anuradhakanse1996@gmail.com")};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport.send(msg);
 
    }
 
    /**
     * Test sending e-mail with attachments
     */
    public static void sendEMail(String id, String credential, String receiver) {
    	    	
        // SMTP info
        String host = "anuradhakanse1996@gmail.com";
        String port = "25";
        String mailFrom = id;
        String password = credential;
        String mailTo = receiver;
      
        
 
        // message info
      //  String mailTo = receiver;
        String subject = "Salesforce Automation Result";
        
        
        
        // //to attach report in email
        String[] attachFiles = new String[1];
        String property = System.getProperty("user.dir");
        attachFiles[0] = property+"\\test-output\\emailable-report.html";
       // attachFiles[1] = property+"/"+folderName+"/result.html";
        
        String message = "Please see the attachment for details. \r\n"
        		+ "\r\n"
        		+ "Find the Extent Report at "+property+"/"+folderName;
		
 
        try {
        	System.out.println(mailFrom);
            sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
                subject, message, attachFiles);
            
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }
}
