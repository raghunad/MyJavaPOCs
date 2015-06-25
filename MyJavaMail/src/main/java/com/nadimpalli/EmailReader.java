package com.nadimpalli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class EmailReader {
	
	final static Logger logger = Logger.getLogger(EmailReader.class);
	
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
	            for(Message message:msg) {
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

	 public static void main(String args[]) {
	        Properties props = System.getProperties();
	        props.setProperty("mail.store.protocol", "imaps");
	        try {
	                Session session = Session.getDefaultInstance(props, null);
	                Store store = session.getStore("imaps");

	                // IMAP host for gmail.
	                // Replace  with the valid username of your Email ID.
	                // Replace  with a valid password of your Email ID.

	                store.connect("imap.gmail.com", "*****@gmail.com", "*******");

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
