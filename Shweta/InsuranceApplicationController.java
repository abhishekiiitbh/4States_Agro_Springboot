package com.lti.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.agro.dto.CalculateDto;
import com.lti.agro.dto.InsuranceDto;
import com.lti.agro.dto.Status;
import com.lti.agro.dto.Status.StatusType;
import com.lti.agro.entity.InsuranceApplications;
import com.lti.agro.entity.InsuranceClaim;
import com.lti.agro.services.EmailService;
import com.lti.agro.services.InsuranceApplicationService;
import com.lti.agro.services.InsuranceApplicationServiceImpl;
import com.lti.agro.services.InsuranceClaimServices;

@RestController
@CrossOrigin
public class InsuranceApplicationController {

	@Autowired
	InsuranceApplicationService insuranceapplicationservice;
	@Autowired
	EmailService emailServices;

	InsuranceApplications insuranceApp = new InsuranceApplications();

	@PostMapping(path = "/registerInsurance")

	public Status insuranceApplicationRequest(@RequestBody InsuranceDto insuranceDto) {
		// System.out.println("Shweta"+insuranceDto.getState());
		// System.out.println("Thakur"+insuranceDto.getCropType());
		insuranceApp.setName(insuranceDto.getName());
		insuranceApp.setEmail(insuranceDto.getEmail());
		insuranceApp.setAddress(insuranceDto.getAddress());
		insuranceApp.setCropType(insuranceDto.getCropType());
		insuranceApp.setCropName(insuranceDto.getCropName());
		insuranceApp.setCultivationArea(insuranceDto.getCultivationArea());
		insuranceApp.setYear(insuranceDto.getYear());
		insuranceApp.setState(insuranceDto.getState());
		int farmerId = insuranceDto.getFarmerId();
		Status status = new Status();
		boolean insert = insuranceapplicationservice.applyForInsurance(insuranceApp, farmerId);
		// boolean insert =false;
		if (insert) {
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Insurance request submitted successfully");
			String text = "Your request to apply for insurance is submitted successfully, please await for confirmation mail.";
			String subject = "Insurance Application request";
			emailServices.sendEmailForNewRegistration(insuranceDto.getEmail(), text, subject);

		} else {
			status.setStatus(StatusType.FAILURE);
			status.setMessage("Insurance request cannot be submitted");
		}

		return status;

	}

	@Autowired
	InsuranceApplicationService insuranceApplicationservice;
	@Autowired
	InsuranceClaimServices insuranceClaimService;
	// InsuranceApplicationServiceImpl serviceimpl;

	@GetMapping(path = "/show-insurance")

	public InsuranceApplications showPreviousInsurance(@RequestParam("fId") int fId) {
		InsuranceApplications ia = insuranceApplicationservice.showPreviousInsuranceById(fId);
		return ia;
	}

	@GetMapping(path = "/show-claim")

	public InsuranceClaim showPreviousclaim(@RequestParam("fId") int fId) {
		InsuranceClaim ic = insuranceClaimService.showPreviousClaimByFid(fId);
		return ic;
	}

	@GetMapping(path = "/calcInsurance")
	public CalculateDto calcInsurance(@RequestParam("state") String state, @RequestParam("cropType") String cropType,
			@RequestParam("area") int area) {
		System.out.println("XXXXXXXXXXXX");
		System.out.println(state);
		System.out.println(cropType);
		return insuranceApplicationservice.calcInsurance(state, cropType, area);

	}
}
