package com.jumpstart.smtp;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
	// generating OTP validate time
	public Date OtpValidationTime() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 5);

		return calendar.getTime();
	}

	//
	//
	// generating password reverting validate time
	public Date PasswordRevertValidationTime() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 24 * 60);

		return calendar.getTime();
	}

	//
	//
	//
	//
	// send email function for sign up
	public boolean signupEmailSend(SignUpUser user) {
		boolean f = false;

		final String emailSubject = user.getSignupUserName() + " - confirm your registration on ' Jump Start '";
		final String emailBody = EmailSendingBody.signupEmailBody(user.getSignupUserName(), user.getSignupUserEmail(),
				user.getSignupUserOTP(), user.getSignupUserOTPValidTime());

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
	public boolean resetEmailSend(ResetProfilePassword user) {
		boolean f = false;

		final String emailSubject = user.getResetUserName() + " - confirm your identity to reset password";
		final String emailBody = EmailSendingBody.resetEmailBody(user.getResetUserName(), user.getResetUserEmail(),
				user.getResetUserOTP(), user.getResetUserOTPValidTime());

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
	// send email function for password resetting notification
	public boolean resetEmailNotify(ResetProfilePasswordNotification user) {
		boolean f = false;

		final String emailSubject = user.getResetUserEmail() + " - your password has changed.";
		final String emailBody = EmailSendingBody.passwordResetNotificationBody(user.getResetUserName(),
				user.getResetUserEmail(), user.getResetUserRecoveryValidTime());

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
	// send email function for inviting users on the portal
	/*
	 * public boolean bulkEmailInviteUsers(InviteUsers users) { boolean f = false;
	 * 
	 * final String emailSubject = "Join Our Portal Today !"; final String emailBody
	 * = "Hi! " +
	 * ".\rWe are inviting you to use our new \"ABC Jobs Portal\", which will give you a better overview of all that is happening with jobs, career, learning metarials, etc."
	 * +
	 * "\rThis platform will help you get the most out of effective jobs, learning metarials."
	 * + "\r\rCheck out the portal now - http://localhost:8585/AbcJobPortal/" +
	 * "\r\rBest regards, \rThe \"AbcJobPortal\" team.";
	 * 
	 * // getting the session object after authentication Session session =
	 * Session.getInstance(hostProperties(), new Authenticator() {
	 * 
	 * @Override protected PasswordAuthentication getPasswordAuthentication() {
	 * return new PasswordAuthentication(smtpLogCredential, smtpLogPassword); } });
	 * 
	 * // composing the message and content MimeMessage mimeMessage = new
	 * MimeMessage(session);
	 * 
	 * try {
	 * 
	 * mimeMessage.setFrom(smtpLogCredential); Address usersAddresses[] = { new
	 * InternetAddress(users.getEmail_1()), new InternetAddress(users.getEmail_2()),
	 * new InternetAddress(users.getEmail_3()), new
	 * InternetAddress(users.getEmail_4()), new InternetAddress(users.getEmail_5())
	 * }; ; mimeMessage.addRecipients(Message.RecipientType.TO, usersAddresses);
	 * 
	 * mimeMessage.setSubject(emailSubject);
	 * 
	 * mimeMessage.setContent(emailBody, "text/html");
	 * 
	 * // sending the email using transport class Transport.send(mimeMessage);
	 * 
	 * f = true;
	 * 
	 * } catch (MessagingException e) { e.printStackTrace(); }
	 * 
	 * return f; }
	 */

	//
	//
	//
	//
	// send email function for user query response
	public boolean UserQueriesResponse(ContactFormResponse response) {
		boolean f = false;

		final String emailSubject = "Thanks for contacting us - " + response.getUserName();
		final String emailBody = EmailSendingBody.userQueryResponseBody(response.getUserName(), response.getUserResId(),
				response.getResponse(), response.getRespondent(), response.getUserEmail(), response.getUserQuery());

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

			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(response.getUserEmail()));

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

}
