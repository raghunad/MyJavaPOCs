package com.nadimpalli;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.apache.log4j.Logger;


public class ReadAllEmailByDateWithContent {
	
	final static Logger logger = Logger.getLogger(ReadAllEmailByDateWithContent.class);
	final static int MAX_MESSAGES=10;
	
	 static void showAllMails(Folder inbox){
	        try {
	            Message msg[] = inbox.getMessages();
	            logger.info("==========================================");
	            logger.info("Total Mails Count: "+msg.length);
	            logger.info("==========================================");
	            int count = 0;
	            int startMail = 1;
	            int endMail = inbox.getMessageCount();
	            
	            Message messages[] = inbox.getMessages(startMail, endMail);
	            Message messageReverse[] = reverseMessageOrder(messages);
	            for(Message message:messageReverse) {
	            	if(count<=10)
	            	{
	            		try {
	            			logger.info("==========================================");
	            			writePart(message);
		                    logger.info("==========================================");
		                } catch (Exception e) {
		                    logger.error("No Information");
		                }
		                count++;
	            	}
	            	else
	            		break;
	            }
	             
	        } catch (MessagingException e) {
	            logger.error(e.toString());
	        }
	    }
	 
	 /*
      * reverse the order of the messages
      */
     private static Message[] reverseMessageOrder(Message[] messages) {
          Message revMessages[]= new Message[messages.length];
          int i=messages.length-1;
          for (int j=0;j<messages.length;j++,i--) {
               revMessages[j] = messages[i];
          }
          return revMessages;

     }
     
     /*
	   * This method checks for content-type 
	   * based on which, it processes and
	   * fetches the content of the message
	   */
	   public static void writePart(Part p) throws Exception {
	      if (p instanceof Message)
	         //Call methos writeEnvelope
	         writeEnvelope((Message) p);

	      logger.info("----------------------------");
	      logger.info("CONTENT-TYPE: " + p.getContentType());

	      //check if the content is plain text
	      if (p.isMimeType("text/plain")) {
	         logger.info("This is plain text");
	         logger.info("---------------------------");
	         logger.info((String) p.getContent());
	      } 
	      //check if the content has attachment
	      else if (p.isMimeType("multipart/*")) {
	         logger.info("This is a Multipart");
	         logger.info("---------------------------");
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++)
	            writePart(mp.getBodyPart(i));
	      } 
	      //check if the content is a nested message
	      else if (p.isMimeType("message/rfc822")) {
	         logger.info("This is a Nested Message");
	         logger.info("---------------------------");
	         writePart((Part) p.getContent());
	      } 
	      //check if the content is an inline image
	      else if (p.isMimeType("image/jpeg")) {
	         logger.info("--------> image/jpeg");
	         Object o = p.getContent();

	         InputStream x = (InputStream) o;
	         // Construct the required byte array
	         logger.info("x.length = " + x.available());
	         int i = 0;
	         byte[] bArray = new byte[x.available()];;
	         while ((i = (int) ((InputStream) x).available()) > 0) {
	            int result = (int) (((InputStream) x).read(bArray));
	            if (result == -1)
	        
	          bArray = new byte[x.available()];

	            break;
	         }
	         FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
	         f2.write(bArray);
	      } 
	      else if (p.getContentType().contains("image/")) {
	         logger.info("content type" + p.getContentType());
	         File f = new File("image" + new Date().getTime() + ".jpg");
	         DataOutputStream output = new DataOutputStream(
	            new BufferedOutputStream(new FileOutputStream(f)));
	            com.sun.mail.util.BASE64DecoderStream test = 
	                 (com.sun.mail.util.BASE64DecoderStream) p
	                  .getContent();
	         byte[] buffer = new byte[1024];
	         int bytesRead;
	         while ((bytesRead = test.read(buffer)) != -1) {
	            output.write(buffer, 0, bytesRead);
	         }
	      } 
	      else {
	         Object o = p.getContent();
	         if (o instanceof String) {
	            logger.info("This is a string");
	            logger.info("---------------------------");
	            logger.info((String) o);
	         } 
	         else if (o instanceof InputStream) {
	            logger.info("This is just an input stream");
	            logger.info("---------------------------");
	            InputStream is = (InputStream) o;
	            is = (InputStream) o;
	            int c;
	            while ((c = is.read()) != -1)
	               System.out.write(c);
	         } 
	         else {
	            logger.info("This is an unknown type");
	            logger.info("---------------------------");
	            logger.info(o.toString());
	         }
	      }

	   }
	   /*
	   * This method would print FROM,TO and SUBJECT of the message
	   */
	   public static void writeEnvelope(Message m) throws Exception {
	      Address[] a;
	      // FROM
	      if ((a = m.getFrom()) != null) {
	         for (int j = 0; j < a.length; j++)
	         logger.info("FROM: " + a[j].toString());
	      }

	      // TO
	      if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
	         for (int j = 0; j < a.length; j++)
	         logger.info("TO: " + a[j].toString());
	      }

	      // SUBJECT
	      logger.info("SUBJECT: " + m.getSubject());
	      
	      // Sent Date
	      logger.info("SENT DATE: " + m.getSentDate());
	   }

	 public static void main(String args[]) {
	        Properties props = System.getProperties();
	        props.setProperty("mail.store.protocol", "imaps");
	        try {
	                Session session = Session.getDefaultInstance(props, null);
	                Store store = session.getStore("imaps");

	                // IMAP host for gmail.
	                // Replace  with the valid username of your Email ID.
	                // Replace  with a valid password of your Email ID.

	                store.connect("imap.gmail.com", "******@gmail.com", "*******");

	                // IMAP host for yahoo.
	                //store.connect("imap.mail.yahoo.com", "", "");
	                logger.info(store);

	                Folder inbox = store.getFolder("Inbox");
	                inbox.open(Folder.READ_ONLY);
	               
	                showAllMails(inbox);
	        } catch (NoSuchProviderException e) {
	            logger.error(e.toString());
	            System.exit(1);
	        } catch (MessagingException e) {
	            logger.error(e.toString());
	            System.exit(2);
	        }
	    }
}