package com.lti.agro.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.agro.dto.Status;
import com.lti.agro.dto.Status.StatusType;
import com.lti.agro.entity.InsuranceApplications;
import com.lti.agro.entity.InsuranceClaim;
import com.lti.agro.entity.Sales;
import com.lti.agro.services.AdminService;
import com.lti.agro.services.EmailService;

@RestController
@CrossOrigin
public class AdminController {
	@Autowired
	AdminService adminService;
	@Autowired
	EmailService emailservice;
	
   //YesterdaysPart:Tested Successfully
   //http://localhost:8080/finalizeBid?sId=3003

	@PostMapping(path = "/finalizeBid")

	public Status finalizeBid(@RequestParam("sId") int sId) {
		Sales sales = adminService.finalizeBid(sId);
		Status status = new Status();
		if (sales != null) {
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Sales Closed!!!");
			// To farmer:

			String text = "Congratulations!!\nYour Crop:" + sales.getCropName() + "was Auctioned Successfully!!";
			String subject = "Crop Auction Successfully";
			emailservice.sendEmailForNewRegistration(sales.getFarmer().getEmail(), text, subject);
			// To bidder:
			String bidder_text = "Congratulations!!\nYou are the Winner for crop:" + sales.getCropName();
			String bidder_subject = "Crop Auction Successfully";
			emailservice.sendEmailForNewRegistration(sales.getBidder().getEmail(), bidder_text, bidder_subject);

		} else {
			status.setStatus(StatusType.FAILURE);
			status.setMessage("Sales Not Found!!!");
		}
		return status;
	}
	
	//YesterdaysPart:not yet tested
	@GetMapping(path="/pendingClaim")
	public String pendingClaimRequest() {
		String pendingClaim=adminService.pendingClaimRequest();
		return pendingClaim;
		
	}
	
	

}
