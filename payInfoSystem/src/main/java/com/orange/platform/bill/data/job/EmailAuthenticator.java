package com.orange.platform.bill.data.job;

import javax.mail.Authenticator; 
import javax.mail.PasswordAuthentication; 

public class EmailAuthenticator extends Authenticator { 
    private String username ; 
    private String password ; 
    public EmailAuthenticator() { 
    	super(); 
    } 
    
    public EmailAuthenticator(String user,String pwd){ 
    	super(); 
    	username = user; 
    	password = pwd; 
    } 
    
    @Override
	public PasswordAuthentication getPasswordAuthentication(){ 
    	return new PasswordAuthentication(username,password); 
    } 
}

 


