package com.jumpstart.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumpstart.entities.ContactUs;
import com.jumpstart.exception.ResourceNotFoundException;
import com.jumpstart.payload.ContactUsDto;
import com.jumpstart.repository.ContactUsRepository;
import com.jumpstart.service.ContactUsService;
import com.jumpstart.service.SlackService;
import com.jumpstart.smtp.ContactFormResponse;
import com.jumpstart.smtp.EmailSendingHandlerService;

@Service
public class ContactUsServiceImpl implements ContactUsService {

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
	public void storeAdminResponse(ContactUsDto contactUsDto) {

		ContactUs adminResponse = this.modelMapper.map(contactUsDto, ContactUs.class);

		// calling the email control handler
		EmailSendingHandlerService eshs = new EmailSendingHandlerService();

		ContactFormResponse contactFormResponse = new ContactFormResponse(adminResponse.getName(),
				adminResponse.getEmail(), adminResponse.getInvoice(), adminResponse.getQueries(),
				adminResponse.getConid(), adminResponse.getResponse(), adminResponse.getRespondent());

		boolean userQueriesResponse = eshs.UserQueriesResponse(contactFormResponse);

		if (userQueriesResponse) {
			// saving the query
			ContactUs storedQuery = this.contactUsRepository.save(adminResponse);

			// webhook slack
			String message = "*Queried ID:*\n\t" + "JS-UQ-SL-" + storedQuery.getConid() + "\n\n*Invoice :*\n\t"
					+ storedQuery.getInvoice() + "\n\n*Name:*\n\t" + storedQuery.getName() + "\n\n*Email:*\n\t"
					+ storedQuery.getEmail() + "\n\n*Queries:*\n\t" + storedQuery.getQueries() + "\n\n*Response:*\n\t"
					+ storedQuery.getResponse() + "\n\n*Respondent By:*\n\t" + storedQuery.getRespondent()
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
