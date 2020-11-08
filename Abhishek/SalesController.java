package com.lti.agro.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.agro.entity.Sales;
import com.lti.agro.repository.SalesDaoImpl;

@CrossOrigin
@RestController
public class SalesController {

	@Autowired
	SalesDaoImpl salesdaotest=new SalesDaoImpl();

	@PostMapping(path = "/placeASellRequest")
	public String placeASellRequest(@RequestBody Sales sales) {

		Sales newsale = salesdaotest.placeASellRequest(sales);

		System.out.println(newsale.getCropName() + " " + newsale.getQuantity() + " " + newsale.getBiddingAmount());

		return "sell request successfully placed.";
	}

	@GetMapping(path = "/viewMarketPlace")
	public List<Sales> viewMarketPlace() {

		List<Sales> sales = salesdaotest.viewAllSales();

		List<Sales> res=new ArrayList<Sales>();

		for (Sales s : sales) {
			if (s.isCheckRequest() == true && s.isCropSold() == false) {
				res.add(s);
			}
		}

		return res;
	}

	@GetMapping(path = "/viewSoldCropHistory")
	public List<Sales> viewSoldCropHistory() {

		List<Sales> sales = salesdaotest.viewAllSales();

		List<Sales> res=new ArrayList<Sales>();

		for (Sales s : sales) {
			if (s.isCheckRequest() == true && s.isCropSold() == true) {
				res.add(s);
			}
		}

		return res;
	}

}
