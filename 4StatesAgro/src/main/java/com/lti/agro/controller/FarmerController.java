package com.lti.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.agro.dto.Status;
import com.lti.agro.dto.Status.StatusType;
import com.lti.agro.entity.Farmer;
import com.lti.agro.repository.FarmerDaoImpl;
import com.lti.agro.services.EmailService;
import com.lti.agro.services.FarmerServices;

@CrossOrigin
@RestController
public class FarmerController {
	
	@Autowired
	FarmerServices farmerService;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping(path="/registerFarmer")
	public Status registerFarmer(@RequestBody Farmer farmer){
		
		farmer.setApproval(false);
		boolean result =  farmerService.addOrUpdate(farmer);
		Status status = new Status();
		if(result)
		{
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Registration Request Submitted Successfully!");
			String text="Your Request for Registration has been Submitted Successfully! Please await for"
						+" confirmation mail from our Team!"+"Regards!<br>4StatesAgro ";
			
			String subject = "Registration Confirmation";
			emailService.sendEmailForNewRegistration(farmer.getEmail(), text, subject);
			
		}
		else
		{
			status.setStatus(StatusType.FAILURE);
			status.setMessage("Request Failed! Your Record Already exists!");
		}
		return status;
	}
	
	
}
