package com.lti.agro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.agro.entity.InsuranceApplications;
import com.lti.agro.entity.InsuranceClaim;
import com.lti.agro.repository.InsuranceClaimDaoImpl;

@Service
public class InsuranceClaimServices {

	@Autowired
	InsuranceClaimDaoImpl insuranceClaimDao;
	@Autowired
	InsuranceApplicationServiceImpl insuranceServiceImpl;

	public boolean addOrUpdateInsuranceClaim(InsuranceClaim insuranceClaim, int policyNo) {

		InsuranceApplications app=insuranceServiceImpl.findInsurnceByPolicyNo(policyNo);
		if ( app!= null) {
			
			System.out.println("True");
			try {
				insuranceClaimDao.checkClaimExists(policyNo);
				return false;
			} catch (Exception e) {
                insuranceClaim.setInsuranceapplication(app);
                app.setInsuranceclaim(insuranceClaim);
				InsuranceClaim newclaim = insuranceClaimDao.placeAClaimRequest(insuranceClaim);
				return true;
			}
		}else {
			System.out.println("false");
			return false;
		}

	}

	public InsuranceClaim showPreviousClaimByFid(int fid) {
		InsuranceClaim ic = insuranceClaimDao.showPreviousClaimByFid(fid);

		return ic;
	}

}
