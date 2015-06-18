package com.nadimpalli;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ValidateEmail {
	public static void main(String[] args) {
		ValidateEmail mailCheck = new ValidateEmail();
 
		// Specify Boolean Flag
		boolean isValid = false;
 
		String email = "*****@gmail.com";
		isValid = mailCheck.myEmailValidator(email);
		mailCheck.myLogger(email, isValid);
 
		email = "hello.gmail";
		isValid = mailCheck.myEmailValidator(email);
		mailCheck.myLogger(email, isValid);
 
		email = "hello.n@";
		isValid = mailCheck.myEmailValidator(email);
		mailCheck.myLogger(email, isValid);
	}
 
	private boolean myEmailValidator(String email) {
		boolean isValid = false;
		try {
			//
			// Create InternetAddress object and validated the supplied
			// address which is this case is an email address.
			InternetAddress internetAddress = new InternetAddress(email);
			internetAddress.validate();
			isValid = true;
		} catch (AddressException e) {
			System.out.println("You are in catch block -- Exception Occurred for: " + email);
		}
		return isValid;
	}
 
	private void myLogger(String email, boolean valid) {
		System.out.println(email + " is " + (valid ? "a" : "not a") + " valid email address\n");
	}
}
