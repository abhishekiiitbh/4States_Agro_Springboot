package com.lti.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.agro.dto.BidStatusDto;
import com.lti.agro.dto.CalculateDto;
import com.lti.agro.dto.Status;
import com.lti.agro.dto.Status.StatusType;
import com.lti.agro.entity.InsuranceClaim;
import com.lti.agro.services.BiddingService;

@RestController
@CrossOrigin
public class BiddingController {
	
	@Autowired
	BiddingService bidService;
	
	
	//http://localhost:8080/make-bid?sId=3003&bidAmount=1000&bId=2001
	
	@PostMapping(path="/make-bid")
	public Status makeBidOnProduct(@RequestParam("sId") int sId,@RequestParam("bidAmount")double bidAmount,@RequestParam("bId")int bId) {
		 bidService.placeBid(sId, bidAmount, bId);
		 Status status=new Status();
		 status.setStatus(StatusType.SUCCESS);
		 status.setMessage("Bid Was Successful");
		 return status;
		 //System.out.println("Made SuccessFully");
	}
	
	
	

}
