package com.lti.agro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.agro.dto.AdminViewInsuranceApplicationDto;
import com.lti.agro.dto.Status;
import com.lti.agro.dto.Status.StatusType;
import com.lti.agro.entity.Bidder;
import com.lti.agro.entity.InsuranceApplications;
import com.lti.agro.services.AdminServiceImpl;
import com.lti.agro.services.EmailService;

@RestController
@CrossOrigin
public class AdminController {

	@Autowired
	AdminServiceImpl adminservice;
	@Autowired
	EmailService emailService;
	
	@PostMapping(path="/approveBidderRegistration")
	public Status approveBidderRegistration(@RequestParam("bid") int bId) {
		Bidder approvedBidder=adminservice.approveBidderRegistration(bId);
		Status status=new Status();
		if(approvedBidder!=null) {
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Bidder porfile approved successfully");
			String text="Your profile has been approved!\n Your Id is "+approvedBidder.getbId();
			String subject="Registration Approved";
			
			emailService.sendEmailForNewRegistration(approvedBidder.getEmail(), text, subject);
		}else {
			status.setStatus(StatusType.FAILURE);
			status.setMessage("Bidder profile does not exist");
		}
		return status;
	}
	
	@GetMapping(path="/ViewInsuranceRequest")
		public List<AdminViewInsuranceApplicationDto> viewRequests(){
		return adminservice.viewRequest();
	}
	@PostMapping(path="/ApproveInsurance")
	public Status approveApplication(@RequestParam("pNo") int policyNo) {
		boolean result=adminservice.AdminApprovalForInsuranceApplication(policyNo);
		Status status=new Status();
		if(result) {
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Approved Successfully");
		}else {
			status.setStatus(StatusType.FAILURE);
			status.setMessage("Approval rejected");
		}
		return status;
		
	}
	@PostMapping(path="/ApproveInsuranceClaim")
	public Status approveClaim(@RequestParam("rId") int rId) {
		boolean result=adminservice.AdminApprovalForInsuranceClaim(rId);
		Status status=new Status();
		if(result) {
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Approved claim successfully");
		}else {
			status.setStatus(StatusType.FAILURE);
			status.setMessage("Claim rejected");
		}
		return status;
		
	}
	
	
}
