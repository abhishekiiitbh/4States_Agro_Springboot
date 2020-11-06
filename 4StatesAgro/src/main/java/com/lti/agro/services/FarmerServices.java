package com.lti.agro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.agro.entity.Farmer;
import com.lti.agro.repository.FarmerDaoImpl;

@Service
public class FarmerServices {
	
	
	@Autowired
	FarmerDaoImpl farmerdao;
	
	public boolean addOrUpdate(Farmer farmer) {
		
		try {
			
			farmerdao.findFarmerByAadharNo(farmer.getAadhaarCardNumber());
			return false;
		
		} catch (Exception e) {
			
			farmerdao.addOrUpdate(farmer);
		}
		
		return true;
	}
}
