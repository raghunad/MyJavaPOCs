package com.nadimpalli;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.Logger;

public class CheckingMails {
	
	 final static Logger logger = Logger.getLogger(CheckingMails.class);
	
	 public static void check(String host, String storeType, String user,
		      String password) 
		   {
		      try {

		      //create properties field
		      Properties properties = new Properties();
		     
		      properties.put("mail.pop3.host", host);
		      properties.put("mail.pop3.port", "995");
		      properties.put("mail.pop3.starttls.enable", "true");
		      Session emailSession = Session.getDefaultInstance(properties);
		  
		      //create the POP3 store object and connect with the pop server
		      Store store = emailSession.getStore("pop3s");

		      store.connect(host, user, password);

		      //create the folder object and open it
		      Folder emailFolder = store.getFolder("Inbox");
		      emailFolder.open(Folder.READ_ONLY);

		      // retrieve the messages from the folder in an array and print it
		      Message[] messages = emailFolder.getMessages();
		      logger.info("messages.length---" + messages.length);

		      for (int i = 0, n = 4; i < n; i++) { // messages.length
		         Message message = messages[i];
		         logger.info("---------------------------------");
		         logger.info("Email Number " + (i + 1));
		         logger.info("Subject: " + message.getSubject());
		        // logger.info("From Length: " + message.getFrom());
		         logger.info("From: " + message.getFrom()[0]);
		         logger.info("Recipients: " + message.getAllRecipients()[0]);
		         logger.info("Text: " + message.getContent().toString());
		         logger.info("Content Type: " + message.getContentType());
		      }

		      //close the store and folder objects
		      emailFolder.close(false);
		      store.close();

		      } catch (NoSuchProviderException e) {
		         e.printStackTrace();
		      } catch (MessagingException e) {
		         e.printStackTrace();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		   }

		   public static void main(String[] args) {

		      String host = "pop.gmail.com";// change accordingly
		      String mailStoreType = "pop3";
		      String username = "****@gmail.com";// change accordingly
		      String password = "****";// change accordingly

		      check(host, mailStoreType, username, password);

		   }
}
