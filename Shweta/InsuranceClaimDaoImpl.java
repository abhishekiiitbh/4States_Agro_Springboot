package com.lti.agro.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lti.agro.entity.InsuranceClaim;


@Repository
public class InsuranceClaimDaoImpl {
	
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public InsuranceClaim placeAClaimRequest(InsuranceClaim claim) {
		InsuranceClaim newClaim =em.merge(claim);
		//System.out.println(newClaim.getrId());
		return newClaim;
	}
	
	
	
	public InsuranceClaim checkClaimExists(int policyNo) {
		
		String jpql ="select ic from InsuranceClaim ic where ic.policyNo=:ps";
		TypedQuery<InsuranceClaim> query =em.createQuery(jpql, InsuranceClaim.class);
		query.setParameter("ps", policyNo);
		return query.getSingleResult();
		
	}
	public InsuranceClaim showPreviousClaimByFid(int fid) {
		String jpql="select ic from InsuranceApplication ic WHERE ic.fId=:fid";
		TypedQuery<InsuranceClaim> query=em.createQuery(jpql,InsuranceClaim.class);
		return query.getSingleResult();
		
	}
	public InsuranceClaim findByRId(int rId) {
		return em.find(InsuranceClaim.class, rId);
	}
	
}
