package com.lti.agro.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.agro.entity.Bidder;
import com.lti.agro.entity.InsuranceApplications;
import com.lti.agro.entity.InsuranceClaim;
import com.lti.agro.entity.Sales;
import com.lti.agro.repository.BidderDaoImpl;
import com.lti.agro.repository.InsuranceClaimDao;
import com.lti.agro.repository.InsuranceClaimDaoImpl;
import com.lti.agro.repository.SalesDaoImpl;

@Service
public class AdminService {
	
	
	@Autowired
	BidderDaoImpl bidderDao;
	@Autowired
	SalesDaoImpl salesDaoImpl;
	@Autowired
	InsuranceClaim insuranceClaimDao;
	
	//Yesterdays Part
	public Sales finalizeBid(int sId) {
		
		Sales sales=salesDaoImpl.findSalesById(sId);
		if(sales==null) {
			return null;
		}
		LocalDate endDate=sales.getSalesenddate();
		LocalDate today=LocalDate.now();
		System.out.println("SalesEnddate:"+endDate);
		if(endDate.compareTo(today)==0) {
			sales.setCropSold(true);
			salesDaoImpl.placeASellRequest(sales);
			System.out.println("Sales endDate:::"+sales.getSalesenddate());
		}
		return sales;
	}
	
	public String pendingClaimRequest(){
	   
		LocalDate defDateofApproval=LocalDate.of(01, 01, 01);
		if(insuranceClaimDao.getDateOfClaim().compareTo(defDateofApproval)==0 &&  insuranceClaimDao.getTransactionId()==null && insuranceClaimDao.getAmountClaimed()==0) {
			return insuranceClaimDao.toString();
		}
		return null;
		
		
	}
	

}
