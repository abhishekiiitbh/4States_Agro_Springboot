package com.lti.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.agro.dto.Status;
import com.lti.agro.dto.Status.StatusType;
import com.lti.agro.entity.Bidder;
import com.lti.agro.repository.BidderDaoImpl;
import com.lti.agro.services.BidderServices;
import com.lti.agro.services.EmailService;

@CrossOrigin
@RestController
public class BidderController {
	
	@Autowired
	BidderServices bidderServices;
	@Autowired
	EmailService emailServices;
	
	@PostMapping(path="/registerBidder")
	public Status registerBidder(@RequestBody Bidder bidder){
		
		bidder.setApproval(false);
		boolean res=bidderServices.addOrUpdateABidder(bidder);
		Status status=new Status();
		if(res) {
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("You have successfully generated a registration request!");
			String text="Your registration request is submitted successfully, please await for confirmation mail.";
			String subject="Registration Confirmation";
			
			emailServices.sendEmailForNewRegistration(bidder.getEmail(), text, subject);
		}else {
			status.setStatus(StatusType.FAILURE);
			status.setMessage("Registration failed! Your records already exists");
		}
		return status;
	}
}
