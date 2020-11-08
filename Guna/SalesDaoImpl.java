package com.lti.agro.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lti.agro.entity.ContactUsRequest;
import com.lti.agro.entity.Sales;



@Repository
public class SalesDaoImpl {
	
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public Sales placeASellRequest(Sales sale) {
		return em.merge(sale);
		//System.out.println(newSale.getSalesId());
	}
	
	//changes made here:void-->return type
	public Sales findSalesById(int sId) {
		Sales foundSale = em.find(Sales.class, sId);
		return foundSale;
		//System.out.println(foundSale);
	}
	
	
	public List<Sales> viewAllSales() {
		/*String jpql="Select s from Sales s";
		TypedQuery<Sales> query = em.createQuery(jpql,Sales.class);
		List<Sales> sales=query.getResultList();
		/*for(Sales sale:sales)
		{
			System.out.println(sale);
		}*/
		
		Query query=em.createQuery("Select s from Sales s",Sales.class);
		return query.getResultList();
	}
	
	public void findSalesByFarmerId(int fId) {
		String jpql="Select s from Sales s where s.fid=:farmerId";
		TypedQuery<Sales> query = em.createQuery(jpql, Sales.class);
		query.setParameter("farmerId", fId);
		Sales sale=query.getSingleResult();
		System.out.println(sale);
	}
	
	public void findSalesByBidder(int bId) {
		
		String jpql="Select s from Sales s where s.bid=:bidderId";
		TypedQuery<Sales> query = em.createQuery(jpql, Sales.class);
		query.setParameter("bidderId", bId);
		Sales sale=query.getSingleResult();
		System.out.println(sale);
	}
}