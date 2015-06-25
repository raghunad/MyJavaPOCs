package com.nadimpalli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.apache.log4j.Logger;


public class ReadAllEmailByDate {
	
	final static Logger logger = Logger.getLogger(ReadAllEmailByDate.class);
	final static int MAX_MESSAGES=10;
	
	 static void showUnreadMails(Folder inbox){       
	        try {
	            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
	            Message msg[] = inbox.search(ft);
	            logger.info("MAILS: "+msg.length);
	            for(Message message:msg) {
	                try {
	                    logger.info("DATE: "+message.getSentDate().toString());
	                    logger.info("FROM: "+message.getFrom()[0].toString());           
	                    logger.info("SUBJECT: "+message.getSubject().toString());
	                    logger.info("CONTENT: "+message.getContent().toString());
	                    // To get all the data from the getContent() refer to FetchingEmail.java class
	                    logger.info("-------------------------------------------");
	                } catch (Exception e) {
	                    logger.error("No Information");
	                }
	            }
	        } catch (MessagingException e) {
	            logger.error(e.toString());
	        }
	    }
	 
	 static void showAllMails(Folder inbox){
	        try {
	            Message msg[] = inbox.getMessages();
	            logger.info("MAILS: "+msg.length);
	            int count = 0;
	            
	            Date toDate = new Date();
	            Date fromDate = new Date(2015,6,11);
	            
	            Message[] arrayMessages = inbox.getMessages();
	            // Get the last 10 messages
	            int end = inbox.getMessageCount();
	            int start = 1;
	            Message messages[] = inbox.getMessages(start, end);
	               
	            // Reverse the ordering so that the latest comes out first
	            Message messageReverse[] = reverseMessageOrder(messages);
	            
	            /*for (int i = 0; i < messageReverse.length; i++) //max=puruna date=after,min=ajika date...
	            {
	                if (msg[i].getSentDate().after(fromDate) && msg[i].getSentDate().before(toDate))
	                {
	                  Message message = msg[i];        
	                  Address[] fromAddress = message.getFrom();
	                 String from = fromAddress[0].toString();
	                 Address[]toAdress=message.getAllRecipients();
	                 String to=toAdress[0].toString();
	                 String subject = message.getSubject();
	                 String sentDate = message.getSentDate().toString();
	                 String contentType = message.getContentType().toString();
	                 String messageContent = "";
	                 String attachFiles = "";

	                 if (contentType.contains("multipart")) {
	                     // content may contain attachments
	                     Multipart multiPart = (Multipart) message.getContent();
	                     int numberOfParts = multiPart.getCount();
	                     for (int partCount = 0; partCount < numberOfParts; partCount++) {
	                         MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
	                         if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
	                             // this part is attachment
	                             String fileName = part.getFileName();
	                             attachFiles += fileName + ", ";
	                             part.saveFile(saveDirectory + File.separator + fileName);

	                         } else {
	                             // this part may be the message content
	                             messageContent = part.getContent().toString();
	                         }
	                     }

	                     if (attachFiles.length() > 1) {
	                         attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
	                     }
	                 } else if (contentType.contains("text/plain")
	                         || contentType.contains("text/html")) {
	                     Object content = message.getContent();
	                     if (content != null) {
	                         messageContent = content.toString();
	                     }

	                 }

	                 System.out.println("Message #" + (i + 1) + ":");
	                 System.out.println("\t From: " + from);
	                 System.out.println("\t to: " + to);
	                 System.out.println("\t Subject: " + subject);
	                 System.out.println("\t Sent Date: " + sentDate);
	                 System.out.println("\t Message: " + messageContent);
	                 //System.out.println("\t Attachments: " + attachFiles);
	             }
	            } */ 
	            
	            /*for(Message message:msg) {
	            	if(count<=100)
	            	{
	            		try {
		                    logger.info("DATE: "+message.getSentDate().toString());
		                    logger.info("FROM: "+message.getFrom()[0].toString());           
		                    logger.info("SUBJECT: "+message.getSubject().toString());
		                    logger.info("CONTENT: "+message.getContent().toString());
		                    logger.info("------------------------------------------");
		                } catch (Exception e) {
		                    logger.error("No Information");
		                }
		                count++;
	            	}
	            	else
	            		break;
	                
	            }*/
	            
	            for(Message message:messageReverse) {
	            	if(count<=100)
	            	{
	            		try {
		                    logger.info("DATE: "+message.getSentDate().toString());
		                    logger.info("FROM: "+message.getFrom()[0].toString());           
		                    logger.info("SUBJECT: "+message.getSubject().toString());
		                    logger.info("CONTENT: "+message.getContent().toString());
		                    logger.info("------------------------------------------");
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

	 public static void main(String args[]) {
	        Properties props = System.getProperties();
	        props.setProperty("mail.store.protocol", "imaps");
	        try {
	                Session session = Session.getDefaultInstance(props, null);
	                Store store = session.getStore("imaps");

	                // IMAP host for gmail.
	                // Replace  with the valid username of your Email ID.
	                // Replace  with a valid password of your Email ID.

	                store.connect("imap.gmail.com", "******@gmail.com", "******");

	                // IMAP host for yahoo.
	                //store.connect("imap.mail.yahoo.com", "", "");

	                logger.info(store);

	                Folder inbox = store.getFolder("Inbox");
	                inbox.open(Folder.READ_ONLY);
	               
	                BufferedReader optionReader = new BufferedReader(new InputStreamReader(System.in));
	                logger.info("Press (U) to get only unread mails OR Press (A) to get all mails:");
	                try {
	                    char answer = (char) optionReader.read();
	                    if(answer=='A' || answer=='a'){
	                        showAllMails(inbox);
	                    }else if(answer=='U' || answer=='u'){
	                        showUnreadMails(inbox);
	                    }
	                    optionReader.close();
	                } catch (IOException e) {
	                    logger.info(e);
	                }
	               
	        } catch (NoSuchProviderException e) {
	            logger.error(e.toString());
	            System.exit(1);
	        } catch (MessagingException e) {
	            logger.error(e.toString());
	            System.exit(2);
	        }

	    }
	 
}
