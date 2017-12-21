package org.prof_itgroup.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.it.utils.properties.PropertiesService;

public class EmailSenderSSL implements EmailSender{
	private static Logger log = Logger.getLogger(EmailSenderSSL.class);
	
	private PropertiesService propertiesService;
	
	public EmailSenderSSL(PropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}
	

	public void send(String message){
		Properties props = new Properties();
		
		final String username = propertiesService.get("username");
		final String password = propertiesService.get("password");
		
		props.put("mail.smtp.host", propertiesService.get("mail.smtp.host"));
		props.put("mail.smtp.socketFactory.port", propertiesService.get("mail.smtp.socketFactory.port"));
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", propertiesService.get("mail.smtp.auth"));
		props.put("mail.smtp.port", propertiesService.get("mail.smtp.port"));

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password);
				}
			});

		try {

			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(propertiesService.get("from-email")));
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(propertiesService.get("to-email")));
			mimeMessage.setSubject("Testing Subject");
			mimeMessage.setText(message);

			Transport.send(mimeMessage);

		} catch (MessagingException e) {
			log.info("SgdMessageListener error:" + e.getMessage());
		}
		
	}
}
