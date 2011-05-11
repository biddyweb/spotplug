package com.plugtree.spotplug.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.drools.runtime.rule.RuleContext;

import com.plugtree.spotplug.Actuator;

public class MailActuator implements Actuator {

	//TODO: configuration file.
	private String host = "smtp.gmail.com";
	private String port = "587";
	private String mailFrom = "mail@gmail.com";
	private String mailTo = "mail2@plugtree.com";
	private String password = "passwordAca";
	
	@Override
	public void writeOutput(RuleContext ruleContext, User user) {
		
		//TODO: configuration file.
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.port", port);
		properties.setProperty("mail.smtp.user", mailFrom);
		properties.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(properties);

		MimeMessage message = new MimeMessage(session);

		try
		{
			message.setFrom(new InternetAddress(mailFrom));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
			message.setSubject("Fraud Detection");
			message.setText("The user " + user.getId() + "committed fraud.");

			Transport transport = session.getTransport("smtp");
			transport.connect(mailFrom, password);
			transport.sendMessage(message, message.getAllRecipients());
			
			transport.close();

		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
