package com.lti.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.agro.services.EmailService;
import com.lti.agro.dto.Status;
import com.lti.agro.dto.Status.StatusType;
import com.lti.agro.entity.ContactUsRequest;
import com.lti.agro.repository.ContactUsDaoImpl;

@CrossOrigin
@RestController
public class ContactUsController {
	
	@Autowired
	private EmailService emailservice;
	
	@Autowired
	ContactUsDaoImpl contactusdaoimpl;
	
	@PostMapping(path="/ContactUsRequest")
	public Status ContactUsRequest(@RequestBody ContactUsRequest contactusrequest){
		
		contactusrequest.setStatus(false);
		
		contactusdaoimpl.addAMessage(contactusrequest);
		String subject="Message sent successfully!";
		String text="We need your patience for now. Soon we will get back to you to resolve your query.Thanks for contacting.";
		
		emailservice.sendEmailForNewRegistration(contactusrequest.getEmail(), text, subject);
		
		Status status=new Status();
		status.setStatus(StatusType.SUCCESS);
		status.setMessage("Request sent sucessfully");
		
		return status;
	}
	
}
