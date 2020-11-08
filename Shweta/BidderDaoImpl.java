package com.lti.agro.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lti.agro.entity.Bidder;
import com.lti.agro.entity.Farmer;



@Repository
public class BidderDaoImpl {

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public Bidder addOrUpfateBidder(Bidder bidder) {
		
		return em.merge(bidder);
		//System.out.println(newBidder.getbId());

	}
	public Bidder findBidderByAadharCardNo(String AadharCard) {
		String jpql = "Select b from Bidder b where b.aadhaarCardNumber=:adhar";
		TypedQuery<Bidder> query= em.createQuery(jpql,Bidder.class);
		query.setParameter("adhar", AadharCard);
		Bidder found = query.getSingleResult();
		return found;
		
	}
	
	public Bidder findBidderById(int bId) {
		Bidder foundBidder = em.find(Bidder.class, bId);
		return foundBidder;
	}
	
	public void viewAllBidders() {
		String jpql="Select b from Bidder b";
		TypedQuery<Bidder> query = em.createQuery(jpql, Bidder.class);
		List<Bidder> bidders = query.getResultList();
		for(Bidder bidder:bidders)
		{
			System.out.println(bidder);
		}
	}
	
}
