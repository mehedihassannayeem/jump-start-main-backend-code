package com.jumpstart.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumpstart.entities.Account;
import com.jumpstart.entities.ContactUs;
import com.jumpstart.entities.User;
import com.jumpstart.exception.ResourceNotFoundException;
import com.jumpstart.payload.ContactUsDto;
import com.jumpstart.repository.AccountRepository;
import com.jumpstart.repository.ContactUsRepository;
import com.jumpstart.security.TokenAuthenticationFilter;
import com.jumpstart.security.TokenProvider;
import com.jumpstart.service.ContactUsService;
import com.jumpstart.service.SlackService;
import com.jumpstart.smtp.ContactFormResponse;
import com.jumpstart.smtp.EmailSendingHandlerService;

@Service
public class ContactUsServiceImpl implements ContactUsService {

	/**
	 * Token start
	 */

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TokenAuthenticationFilter tokenAuthenticationFilter;

	@Autowired
	private TokenProvider tokenProvider;

	/**
	 * Token finish
	 */

	@Autowired
	private ContactUsRepository contactUsRepository;

	@Autowired
	private SlackService slackService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void storeUserQueries(ContactUsDto contactUsDto) {

		ContactUs userContactForm = this.modelMapper.map(contactUsDto, ContactUs.class);

		// generating and UUID
		String con_form_id = UUID.randomUUID().toString();

		// setting the UUID as primary key
		userContactForm.setConid(con_form_id);

		// checking the invoice number
		if (contactUsDto.getInvoice().isBlank() || contactUsDto.getInvoice().isEmpty()) {
			userContactForm.setInvoice(null);
		}

		// saving the query
		ContactUs storedQuery = this.contactUsRepository.save(userContactForm);

		// webhook slack
		String message = "*Queried ID:*\n\t" + "JS-UQ-SL-" + storedQuery.getConid() + "\n\n*Invoice :*\n\t"
				+ storedQuery.getInvoice() + "\n\n*Name:*\n\t" + storedQuery.getName() + "\n\n*Email:*\n\t"
				+ storedQuery.getEmail() + "\n\n*Queries:*\n\t" + storedQuery.getQueries()
				+ "\n\n:identification_card:  :e-mail:  :pushpin:  :paperclip:  :memo:";

		slackService.sendUserQueries(message);

	}

	@Override
	public void storeAdminResponse(ContactUsDto contactUsDto, HttpServletRequest request) {

		// getting admin profile
		String token = this.tokenAuthenticationFilter.getJwtFromRequest(request);
		Long id = this.tokenProvider.getUserIdFromToken(token);
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		User user = account.getUtbl();

		//
		ContactUs adminResponse = this.contactUsRepository.findById(contactUsDto.getConid()).orElseThrow(
				() -> new ResourceNotFoundException("User queries", "querying id", contactUsDto.getConid()));

		adminResponse.setResAt(new Date().toLocaleString());
		adminResponse.setRespondent(user);
		adminResponse.setResponse(contactUsDto.getResponse());

		// calling the email control handler
		EmailSendingHandlerService eshs = new EmailSendingHandlerService();

		ContactFormResponse contactFormResponse = new ContactFormResponse(adminResponse.getName(),
				adminResponse.getEmail(), adminResponse.getInvoice(), adminResponse.getQueries(),
				adminResponse.getConid(), adminResponse.getResponse(), adminResponse.getRespondent().getName());

//		boolean userQueriesResponse = eshs.UserQueriesResponse(contactFormResponse);
		boolean userQueriesResponse = true;

		// if the email sent only then will store and send slack message
		// so that another admin can try to contact once again
		if (userQueriesResponse) {

			// saving the response
			ContactUs storedQuery = this.contactUsRepository.save(adminResponse);

			// webhook slack
			String message = "*Queried ID:*\n\t" + "JS-UQ-SL-" + storedQuery.getConid() + "\n\n*Invoice :*\n\t"
					+ storedQuery.getInvoice() + "\n\n*Name:*\n\t" + storedQuery.getName() + "\n\n*Email:*\n\t"
					+ storedQuery.getEmail() + "\n\n*Queries:*\n\t" + storedQuery.getQueries() + "\n\n*Response:*\n\t"
					+ storedQuery.getResponse() + "\n\n*Respondent By:*\n\t" + storedQuery.getRespondent().getName()
					+ "\n\n*Respond At:*\n\t" + storedQuery.getResAt()
					+ "\n\n:identification_card:  :e-mail:  :pushpin:  :paperclip:  :memo:";

			slackService.sendAdminResponse(message);
		}

	}

	@Override
	public List<ContactUsDto> fetchAllQueries() {
		List<ContactUs> listOfQueries = this.contactUsRepository.findAll();

		List<ContactUsDto> queries = listOfQueries.stream()
				.map(query -> this.modelMapper.map(query, ContactUsDto.class)).collect(Collectors.toList());

		return queries;
	}

	@Override
	public ContactUsDto fetchAQuery(String conId) {
		ContactUs contactUs = this.contactUsRepository.findById(conId)
				.orElseThrow(() -> new ResourceNotFoundException("Query", "quering id", conId));
		return this.modelMapper.map(contactUs, ContactUsDto.class);
	}

}
