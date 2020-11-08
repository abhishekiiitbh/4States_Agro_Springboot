package com.lti.agro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.agro.dto.Status;
import com.lti.agro.dto.Status.StatusType;
import com.lti.agro.entity.ContactUsRequest;
import com.lti.agro.entity.Farmer;
import com.lti.agro.entity.Sales;
import com.lti.agro.repository.ContactUsDaoImpl;
import com.lti.agro.services.AdminServicesImpl;
import com.lti.agro.services.EmailService;
// Done by Abhishek
@CrossOrigin
@RestController
public class AdminController {
	
	@Autowired
	ContactUsDaoImpl dao=new ContactUsDaoImpl();
	
	@Autowired
	AdminServicesImpl adminserviceimpl;
	
	@Autowired
	private EmailService emailservice;
	
	@GetMapping(path = "/viewAllMessages")
	public List<ContactUsRequest> viewAllMessages() {

		List<ContactUsRequest> msgs = dao.viewAllMessages();

		return msgs;
	}
	
	@PostMapping(path="/approveSellRequest")
	public Status approveSellRequest(@RequestParam("salesId") int salesId,@RequestParam("basePrice") double basePrice){
		
		Sales aprovedSellRequest=adminserviceimpl.approveSellRequest(salesId, basePrice);
		Status status=new Status();
		if(aprovedSellRequest!=null)
		{
		status.setStatus(StatusType.SUCCESS);
		status.setMessage("Sell request approved.");
		String subject="Sell Request Approved!";
		String text="Your Crop has been approved successfully. \n Your Sales Id is: "+aprovedSellRequest.getSalesId();
		//emailservice.sendEmailForNewRegistration(aprovedSellRequest.getFarmer().getEmail(), text, subject);
		emailservice.sendEmailForNewRegistration("b116001@iiit-bh.ac.in", text, subject);
		}
		else
		{
			status.setStatus(StatusType.FAILURE);
			status.setMessage("Your Sell request Does Not Exist!");
		}
		
		return status;
	}

}
