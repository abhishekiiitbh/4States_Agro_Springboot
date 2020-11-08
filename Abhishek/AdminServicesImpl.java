package com.lti.agro.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.agro.entity.Sales;
import com.lti.agro.repository.SalesDaoImpl;

@Service
public class AdminServicesImpl {
	
	@Autowired
	SalesDaoImpl salesdao;
	
	public Sales approveSellRequest(int salesId,double basePrice) {
		Sales newSale=salesdao.findSalesById(salesId);
		if(newSale==null)
			return null;
		newSale.setBasePrice(basePrice);
		newSale.setSaleStartDate(LocalDate.of(2020, 11, 7));
		newSale.setSaleEndDate(LocalDate.of(2020, 11, 11));
		newSale.setCheckRequest(true);
		salesdao.placeASellRequest(newSale);
		
		return newSale;
	}
	
	
}
