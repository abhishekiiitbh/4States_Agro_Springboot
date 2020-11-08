package com.lti.agro.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.agro.dto.InsuranceClaimDto;
import com.lti.agro.dto.Status;
import com.lti.agro.dto.Status.StatusType;
import com.lti.agro.entity.InsuranceClaim;
import com.lti.agro.services.InsuranceClaimServices;

import net.bytebuddy.matcher.ClassLoaderHierarchyMatcher;

@RestController
@CrossOrigin
public class InsuranceClaimController {
	
	@Autowired
	InsuranceClaimServices insuranceClaimServices;
	
	@PostMapping(path="/registerClaim")
	
	public Status ClaimApplication(@RequestBody InsuranceClaimDto insuranceClaimDto) {
		
		InsuranceClaim claim=new InsuranceClaim();
		claim.setCauseOfClaim(insuranceClaimDto.getCauseOfClaim());
		claim.setDateOfClaim(insuranceClaimDto.getDateOfClaim());
		claim.setDateOfLoss(insuranceClaimDto.getDateOfLoss());
		claim.setDateOfApproval(LocalDate.of(1111, 1, 1));
		claim.setAmountClaimed(0);
		int policNo=insuranceClaimDto.getPolicyNo();
		boolean result=insuranceClaimServices.addOrUpdateInsuranceClaim(claim, policNo);
		//boolean result=false;
		System.out.println(insuranceClaimDto.getCauseOfClaim()+" "+insuranceClaimDto.getDateOfClaim()+" "+insuranceClaimDto.getDateOfLoss()+" "
		+insuranceClaimDto.getPolicyNo());
		
		Status status=new Status();
		if(result) {
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Claim succesfully submitted");
		}else {
			status.setStatus(StatusType.FAILURE);
			status.setMessage("You have already placed a claim or policy number does not exist");
		}
		return status;
		
	}

}
