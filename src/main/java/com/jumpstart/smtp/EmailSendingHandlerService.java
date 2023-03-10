package com.jumpstart.smtp;

import java.io.File;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSendingHandlerService {

	// setting the SMTP server login credentials
	private final String smtpLogCredential = "developmentpurposeemail@gmail.com";
	private final String smtpLogPassword = "yttccezswivqebum";

	// setting the SMTP host informations
	private static Properties hostProperties() {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.enable", "true");

		return properties;
	}

	//
	//
	// otp for sign up
	public String getSignUpOtp() {
		Random rndRandom = new Random();
		int rndOtp = rndRandom.nextInt(999999);
		return String.format("%06d", rndOtp);
	}

	//
	//
	// otp for password resetting
	public String getResetPassOtp() {
		Random rndRandom = new Random();
		int rndOtp = rndRandom.nextInt(999999);
		return String.format("%06d", rndOtp);
	}

	//
	//
	//
	//
	// send email function for sign up
	public boolean signupEmailSend(SignUpUser user, String logo) {
		boolean f = false;

		final String emailSubject = user.getSignupUserName() + " - confirm your registration on ' Jump Start '";
		final String emailBody = EmailSendingBody.signupEmailBody(user.getSignupUserName(), user.getSignupUserEmail(),
				user.getSignupUserOTP());

		// getting the session object after authentication
		Session session = Session.getInstance(hostProperties(), new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpLogCredential, smtpLogPassword);
			}
		});

		// composing the message and content
		MimeMessage mimeMessage = new MimeMessage(session);

		try {

			mimeMessage.setFrom(smtpLogCredential);
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getSignupUserEmail()));

			mimeMessage.setSubject(emailSubject);

			mimeMessage.setContent(emailBody, "text/html");

			// sending the email using transport class
			Transport.send(mimeMessage);

			f = true;

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return f;
	}

	//
	//
	//
	//
	// send email function for password resetting
	public boolean resetEmailSend(ResetProfilePassword user, String logo) {
		boolean f = false;

		final String emailSubject = user.getResetUserName() + " - confirm your identity to reset password.";
		final String emailBody = "Hi! " + user.getResetUserName()
				+ ".\rIt looks like you have a problem logging into your profile.\r\rPlease use the OTP to confirm your identity for resetting your password in the portal."
				+ "\rYour OTP is - " + user.getResetUserOTP() + "\r\rThis is an auto-generated email to "
				+ user.getResetUserEmail() + " please don't reply or respond to this email."
				+ "\r\rIf you didn't request to reset your password, someone else might try to log in to your account."
				+ "\rDo not worry; your account is still safe. But we would like you to change the password immediately."
				+ "\rThank you for your time.\r\r Best regards from --- \"ABC Jobs Portal\".";

		// getting the session object after authentication
		Session session = Session.getInstance(hostProperties(), new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpLogCredential, smtpLogPassword);
			}
		});

		// composing the message and content
		MimeMessage mimeMessage = new MimeMessage(session);

		try {

			mimeMessage.setFrom(smtpLogCredential);
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getResetUserEmail()));

			mimeMessage.setSubject(emailSubject);

			// path of the content
			String path = logo;

			MimeMultipart mimeMultipart = new MimeMultipart();

			// text
			MimeBodyPart textMime = new MimeBodyPart();
			// file
			MimeBodyPart fileMime = new MimeBodyPart();

			try {
				// setting text
				textMime.setText(emailBody);

				// setting file
				File file = new File(path);
				fileMime.attachFile(file);

				// setting in mimeMultipart
				mimeMultipart.addBodyPart(textMime);
				mimeMultipart.addBodyPart(fileMime);

			} catch (Exception e) {
				e.printStackTrace();
			}

			mimeMessage.setContent(mimeMultipart);

			// sending the email using transport class
			Transport.send(mimeMessage);

			f = true;

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return f;
	}

	//
	//
	//
	//
	// send email function for password resetting notification
	public boolean resetEmailNotify(String userEmailAddress, String logo) {
		boolean f = false;

		final String emailSubject = userEmailAddress + " - your password has changed.";
		final String emailBody = "Hi! " + userEmailAddress
				+ ".\r\rYour \"ABC Jobs Portal\" account's password has been changed. If you did not make this request, you can follow the link below to revert the change. If you require more assistance you can contact our support team at"
				+ "\rsupport@abcjobsportal.com" + "\r\rhttp://localhost:8585/AbcJobPortal/pass-revert-for/"
				+ userEmailAddress + "\r\rThis is an auto-generated email to " + userEmailAddress
				+ " please don't reply or respond to this email."
				+ "\r\rIf you didn't request to reset your password, someone else might try to log in to your account."
				+ "\rWe would like you to change the password immediately."
				+ "\rThank you for your time.\r\r Best regards from --- \"ABC Jobs Portal\".";

		// getting the session object after authentication
		Session session = Session.getInstance(hostProperties(), new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpLogCredential, smtpLogPassword);
			}
		});

		// composing the message and content
		MimeMessage mimeMessage = new MimeMessage(session);

		try {

			mimeMessage.setFrom(smtpLogCredential);
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmailAddress));

			mimeMessage.setSubject(emailSubject);

			// path of the content
			String path = logo;

			MimeMultipart mimeMultipart = new MimeMultipart();

			// text
			MimeBodyPart textMime = new MimeBodyPart();
			// file
			MimeBodyPart fileMime = new MimeBodyPart();

			try {
				// setting text
				textMime.setText(emailBody);

				// setting file
				File file = new File(path);
				fileMime.attachFile(file);

				// setting in mimeMultipart
				mimeMultipart.addBodyPart(textMime);
				mimeMultipart.addBodyPart(fileMime);

			} catch (Exception e) {
				e.printStackTrace();
			}

			mimeMessage.setContent(mimeMultipart);

			// sending the email using transport class
			Transport.send(mimeMessage);

			f = true;

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return f;
	}

	//
	//
	//
	//
	// send email function for inviting users on the portal
	public boolean bulkEmailInviteUsers(InviteUsers users, String logo) {
		boolean f = false;

		final String emailSubject = "Join Our Portal Today !";
		final String emailBody = "Hi! "
				+ ".\rWe are inviting you to use our new \"ABC Jobs Portal\", which will give you a better overview of all that is happening with jobs, career, learning metarials, etc."
				+ "\rThis platform will help you get the most out of effective jobs, learning metarials."
				+ "\r\rCheck out the portal now - http://localhost:8585/AbcJobPortal/"
				+ "\r\rBest regards, \rThe \"AbcJobPortal\" team.";

		// getting the session object after authentication
		Session session = Session.getInstance(hostProperties(), new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpLogCredential, smtpLogPassword);
			}
		});

		// composing the message and content
		MimeMessage mimeMessage = new MimeMessage(session);

		try {

			mimeMessage.setFrom(smtpLogCredential);
			Address usersAddresses[] = { new InternetAddress(users.getEmail_1()),
					new InternetAddress(users.getEmail_2()), new InternetAddress(users.getEmail_3()),
					new InternetAddress(users.getEmail_4()), new InternetAddress(users.getEmail_5()) };
			;
			mimeMessage.addRecipients(Message.RecipientType.TO, usersAddresses);

			mimeMessage.setSubject(emailSubject);

			// path of the content
			String path = logo;

			MimeMultipart mimeMultipart = new MimeMultipart();

			// text
			MimeBodyPart textMime = new MimeBodyPart();
			// file
			MimeBodyPart fileMime = new MimeBodyPart();

			try {
				// setting text
				textMime.setText(emailBody);

				// setting file
				File file = new File(path);
				fileMime.attachFile(file);

				// setting in mimeMultipart
				mimeMultipart.addBodyPart(textMime);
				mimeMultipart.addBodyPart(fileMime);

			} catch (Exception e) {
				e.printStackTrace();
			}

			mimeMessage.setContent(mimeMultipart);

			// sending the email using transport class
			Transport.send(mimeMessage);

			f = true;

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return f;
	}

	//
	//
	//
	//
	// send email function for user query response
	public boolean UserQueriesResponse(ContactFormResponse response, String logo) {
		boolean f = false;

		final String emailSubject = "Thanks for contacting us - " + response.getUser_name();
		final String emailBody = "Hi! " + response.getUser_name() + ".\rThank your so much for your queries and time."
				+ "\r\rYour response id --> " + response.getUser_res_id()
				+ "\r\r-----------------------------------------------------------------------------------------------------------------\r\r"
				+ "This is our response to your query --> " + "\r\r\t" + response.getResponse()
				+ "\r\r\t\t\t\t\t\t Responded by --> " + response.getRespondent()
				+ "\r\r-----------------------------------------------------------------------------------------------------------------\r\r"
				+ "You are receiving this mail because someone has use this " + response.getUser_email()
				+ " to contact us for this below query --> " + "\r\r\t" + response.getUser_query()
				+ "\r\r-----------------------------------------------------------------------------------------------------------------\r\r"
				+ "Feel free to contact us anytime on this email support@abcjobsportal.com or by using the contact form on the website."
				+ "http://localhost:8585/AbcJobPortal/#urlContactFormHash" + "\r\rPlease mention your response id ("
				+ response.getUser_res_id() + ") to get a fast response from us." + "\r\rBest regards, \r"
				+ response.getRespondent() + " <-- \"ABC Jobs Portal\" team.";

		// getting the session object after authentication
		Session session = Session.getInstance(hostProperties(), new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpLogCredential, smtpLogPassword);
			}
		});

		// composing the message and content
		MimeMessage mimeMessage = new MimeMessage(session);

		try {

			mimeMessage.setFrom(smtpLogCredential);

			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(response.getUser_email()));

			mimeMessage.setSubject(emailSubject);

			// path of the content
			String path = logo;

			MimeMultipart mimeMultipart = new MimeMultipart();

			// text
			MimeBodyPart textMime = new MimeBodyPart();
			// file
			MimeBodyPart fileMime = new MimeBodyPart();

			try {
				// setting text
				textMime.setText(emailBody);

				// setting file
				File file = new File(path);
				fileMime.attachFile(file);

				// setting in mimeMultipart
				mimeMultipart.addBodyPart(textMime);
				mimeMultipart.addBodyPart(fileMime);

			} catch (Exception e) {
				e.printStackTrace();
			}

			mimeMessage.setContent(mimeMultipart);

			// sending the email using transport class
			Transport.send(mimeMessage);

			f = true;

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return f;
	}

}
