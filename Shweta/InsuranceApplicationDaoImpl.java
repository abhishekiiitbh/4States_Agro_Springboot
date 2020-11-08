package com.lti.agro.repository;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lti.agro.entity.InsuranceApplications;



@Repository
public class InsuranceApplicationDaoImpl implements InsuranceApplicationDao {

	@PersistenceContext
	EntityManager em;
	
	@Transactional 
	public int ApplyForInsurance(InsuranceApplications insurance) {
		
		InsuranceApplications newApplication = em.merge(insurance);
		return newApplication.getPolicyNo();
		
	}

	public InsuranceApplications findInsuranceByAadhar(String aadharNo) {
		
		String jpql="Select ia from InsuranceApplications ia where ia.farmer.aadhaarCardNumber=:aadharno";
		TypedQuery<InsuranceApplications> query = em.createQuery(jpql,InsuranceApplications.class);
		return query.getSingleResult();
		
	}

	public List<InsuranceApplications> viewAllInsuranceApplications() {
		
		String jpql="Select ia from InsuranceApplications ia";
		TypedQuery<InsuranceApplications> query=em.createQuery(jpql,InsuranceApplications.class);
		return query.getResultList();
		
	}

	public InsuranceApplications findInsurnaceByPolicyNo(int insuranceId) {
		InsuranceApplications inusanceFound=em.find(InsuranceApplications.class, insuranceId);
		return inusanceFound;
	}
	public InsuranceApplications showPreviousInsuranceById(int farmerid) {
		String jpql = "select ia from InsuranceApplication ia WHERE ia.fId=:farmerid";
		TypedQuery<InsuranceApplications> query = em.createQuery(jpql, InsuranceApplications.class);
		return query.getSingleResult();
	}

}
