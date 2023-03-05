package com.jumpstart.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumpstart.entities.ContactUs;
import com.jumpstart.payload.ContactUsDto;
import com.jumpstart.repository.ContactUsRepository;
import com.jumpstart.service.ContactUsService;
import com.jumpstart.service.SlackService;

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

		ContactUs storedQuery = this.contactUsRepository.save(this.modelMapper.map(contactUsDto, ContactUs.class));

		String message = "*Queried ID:*\n\t" + "XYZ-UQ-SL-" + storedQuery.getConid() +

				"\n\n*Name:*\n\t" + storedQuery.getName() + "\n\n*Email:*\n\t" + storedQuery.getEmail()
				+ "\n\n*Queries:*\n\t" + storedQuery.getQueries()
				+ "\n\n:identification_card:  :e-mail:  :pushpin:  :paperclip:  :memo:";

		slackService.sendMessageToSlack(message);

	}

}
