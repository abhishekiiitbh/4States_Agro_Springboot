package com.lti.agro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.agro.entity.Bidder;
import com.lti.agro.repository.BidderDaoImpl;

@Service
public class BidderServices {
	
	@Autowired
	BidderDaoImpl bidderDao;
	
	public boolean addOrUpdateABidder(Bidder bidder) {
		
		try {
			bidderDao.findBidderByAadharCardNo(bidder.getAadhaarCardNumber());
			return false;
		}catch(Exception e){
			bidderDao.addOrUpfateBidder(bidder);
		}
		return true;
	}

}
