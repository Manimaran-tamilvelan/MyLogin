package com.fullcreative.demo;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SimpleMail extends HttpServlet {

	@RequestMapping("/mail")
	public void sendMail(HttpServletRequest req, HttpServletResponse res) {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("maran.mallow@gmail.com", "Mani Maran"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("info.demo@gmail.com", "Demo"));
			msg.setSubject("Your Example.com account has been activated");
			msg.setText("This is a test");
			Transport.send(msg);
			System.out.println("Mail Sent");
		} catch (AddressException e) {
			System.out.println("address not found");
		} catch (MessagingException e) {
			System.out.println("msg exception");
		} catch (UnsupportedEncodingException e) {
			System.out.println("exception on encoding");
		}
	}
}
