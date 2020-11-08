package com.lti.agro.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lti.agro.dto.AdminViewInsuranceApplicationDto;
import com.lti.agro.entity.Bidder;
import com.lti.agro.entity.InsuranceApplications;
import com.lti.agro.entity.InsuranceClaim;
import com.lti.agro.repository.BidderDaoImpl;
import com.lti.agro.repository.InsuranceApplicationDaoImpl;
import com.lti.agro.repository.InsuranceClaimDaoImpl;

@Repository
public class AdminServiceImpl {
	
	@Autowired
	BidderDaoImpl bidderDao;
	@Autowired
	InsuranceApplicationDaoImpl insuranceDao;
	@Autowired
	InsuranceClaimDaoImpl insuranceClaimDao;
	

	
	public Bidder approveBidderRegistration(int bId) {
	
	Bidder newBidder=bidderDao.findBidderById(bId);
	if(newBidder==null)
		return null;
	newBidder.setApproval(true);
	bidderDao.addOrUpfateBidder(newBidder);
	return newBidder;
		
		
	}
	
	public List<AdminViewInsuranceApplicationDto> viewRequest() {
		
		
		List<AdminViewInsuranceApplicationDto> napp=new ArrayList<>();
		
	    List<InsuranceApplications> app= insuranceDao.viewAllInsuranceApplications();
	    for(InsuranceApplications a:app) {
	    	if(a.isStatus()==false) {
	    		AdminViewInsuranceApplicationDto dto=new AdminViewInsuranceApplicationDto();
	    		dto.setName(a.getName());
	    		dto.setAddress(a.getAddress());
	    		dto.setCropImage1(a.getCropImage1());
	    		dto.setCropImage2(a.getCropImage2());
	    		dto.setCropType(a.getCropType());
	    		dto.setCropName(a.getCropName());
	    		dto.setCultivationArea(a.getCultivationArea());
	    		dto.setEmail(a.getEmail());
	    		dto.setFarmersPrimium(a.getFarmersPrimium());
	    		dto.setGovtsPrimium(a.getFarmersPrimium());
	    		//dto.setInsuranceComapnyName(a.getInsurancecompany().getCompanyName());
	    		dto.setPolicyNo(a.getPolicyNo());
	    		dto.setState(a.getState());
	    		dto.setYear(a.getYear());
	    		dto.setSumAssured(a.getSumAssured());
	    		napp.add(dto);
	    	}
	    }
	    return napp;
		
	}
	public boolean AdminApprovalForInsuranceApplication(int policyNo) {
		
		InsuranceApplications newapp=insuranceDao.findInsurnaceByPolicyNo(policyNo);
		if(newapp!= null) {
			newapp.setStatus(true);
			insuranceDao.ApplyForInsurance(newapp);
			return true;
		}
		return false;
	}
	public boolean AdminApprovalForInsuranceClaim(int rId) {
		
		InsuranceClaim newclaim=insuranceClaimDao.findByRId(rId);
		if(newclaim!=null) {
			newclaim.setDateOfApproval(LocalDate.now());
			newclaim.setTransactionId(UUID.randomUUID().toString());
			insuranceClaimDao.placeAClaimRequest(newclaim);
			return true;
		}
		return false;
	}

}
