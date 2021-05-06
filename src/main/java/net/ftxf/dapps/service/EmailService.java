package net.ftxf.dapps.service;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;


@Service
public class EmailService {
	
	
	public static void sendFromYandex(String to, String subject, String body)
			throws AddressException, MessagingException {
		Logger log = Logger.getLogger(EmailService.class.getName());
		log.info("check info request: "+ to +" -- "+subject);
		String from = "system@cec.net.vn";
		String pass = "cecSystem!234";
		String host = "smtp.yandex.com";

		Properties props = System.getProperties();
		
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.quitwait", "false");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		//props.put("mail.debug", "true");
		

		Session session = Session.getInstance(props);
		session.setDebug(false);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");

			message.setFrom(new InternetAddress("CEC System<system@cec.net.vn>"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject, "UTF-8");
			message.setContent(body, "text/html; charset=UTF-8");
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}