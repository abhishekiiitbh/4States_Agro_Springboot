package com.lti.agro.services;

import com.lti.agro.entity.Sales;
import com.lti.agro.repository.SalesDaoImpl;

public interface BiddingService {
	public void placeBid(int sId,double biddingAmount,int bId);

}
