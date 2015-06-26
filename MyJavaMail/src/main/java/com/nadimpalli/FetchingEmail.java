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
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.Logger;

public class FetchingEmail {
	
	 final static Logger logger = Logger.getLogger(FetchingEmail.class);
	 
	 public static void fetch(String pop3Host, String storeType, String user,
		      String password) {
		      try {
		         // create properties field
		         Properties properties = new Properties();
		         properties.put("mail.store.protocol", "pop3");
		         properties.put("mail.pop3.host", pop3Host);
		         properties.put("mail.pop3.port", "995");
		         properties.put("mail.pop3.starttls.enable", "true");
		         Session emailSession = Session.getDefaultInstance(properties);
		         // emailSession.setDebug(true);

		         // create the POP3 store object and connect with the pop server
		         Store store = emailSession.getStore("pop3s");

		         store.connect(pop3Host, user, password);

		         // create the folder object and open it
		         Folder emailFolder = store.getFolder("Inbox");
		         emailFolder.open(Folder.READ_ONLY);

		         BufferedReader reader = new BufferedReader(new InputStreamReader(
			      System.in));

		         // retrieve the messages from the folder in an array and print it
		         Message[] messages = emailFolder.getMessages();
		         logger.info("messages.length---" + messages.length);

		         for (int i = 0; i < messages.length-1; i++) {//messages.length
		            Message message = messages[i];
		            logger.info("---------------------------------");
		            writePart(message);
		            String line = reader.readLine();
		            if ("YES".equals(line)) {
		               message.writeTo(System.out);
		            } else if ("QUIT".equals(line)) {
		               break;
		            }
		         }

		         // close the store and folder objects
		         emailFolder.close(false);
		         store.close();

		      } catch (NoSuchProviderException e) {
		         e.printStackTrace();
		      } catch (MessagingException e) {
		         e.printStackTrace();
		      } catch (IOException e) {
		         e.printStackTrace();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		   }
		   public static void main(String[] args) {

		      String host = "pop.gmail.com";// change accordingly
		      String mailStoreType = "pop3";
		      String username = 
		         "******@gmail.com";// change accordingly
		      String password = "******";// change accordingly

		      //Call method fetch
		      fetch(host, mailStoreType, username, password);

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
		      logger.info("This is the message envelope");
		      logger.info("---------------------------");
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
		      if (m.getSubject() != null)
		         logger.info("SUBJECT: " + m.getSubject());

		   }
}
