package com.lti.agro.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.agro.dto.BidStatusDto;
import com.lti.agro.entity.Bidder;
import com.lti.agro.entity.Sales;
import com.lti.agro.repository.BidderDaoImpl;
import com.lti.agro.repository.SalesDaoImpl;

@Service
public class BiddingServiceImpl implements BiddingService {
	
	@Autowired
	BidderDaoImpl bidderDaoimpl;
	@Autowired
	SalesDaoImpl salesDaoImpl;

	@Override
	public void placeBid(int sId, double biddingAmount,int bId) {
		Sales sales=salesDaoImpl.findSalesById(sId);
		if(sales.getBiddingAmount()<biddingAmount) {
			sales.setBiddingAmount(biddingAmount);
			Bidder bidder=bidderDaoimpl.findBidderById(bId);
			sales.setBidder(bidder);
			List<Sales> list=new ArrayList<Sales>();
			list.add(sales);
			bidder.setSales(list);
			salesDaoImpl.placeASellRequest(sales);
			}
		
		}
	

}
