package com.cg.hsm.daoimpl;

import java.util.List;
import javax.persistence.Query;
import com.cg.hsm.dao.FinanceDAO;
import com.cg.hsm.domain.Finance;
import com.cg.hsm.exception.FinanceFeeColumnEmpty;
import com.cg.hsm.exception.PatientFinanceDetailsNotFound;
import com.cg.hsm.util.DBUtil;

/**
 * This FinanceDAOImpl implements CRUD operations of FinanceDAO class
 * @author kethu_greeshma
 *
 */
public class FinanceDAOImpl extends DBUtil implements FinanceDAO {

	@Override
	public void add(Finance finance) {
		entityManager.getTransaction().begin();
		entityManager.persist(finance);
		System.out.println("Finance details successfully added");
		entityManager.getTransaction().commit();
		
	}

	@Override
	public void remove(int id)  throws PatientFinanceDetailsNotFound {
		entityManager.getTransaction().begin();
		Finance finance = entityManager.find(Finance.class, id);
		entityManager.remove(finance);
		entityManager.getTransaction().commit();
		
	}

	@Override
	public void updateFee(int id,double amount) throws FinanceFeeColumnEmpty {
		entityManager.getTransaction().begin();
		Finance update = entityManager.find(Finance.class, id);
		update.setTotalFee(amount);
		entityManager.getTransaction().commit();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Finance> findAll() {
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("from Finance");
		List<Finance> answers = query.getResultList();
		for (Finance ans:answers) {
			System.out.println(ans.getPatientId());
			System.out.println(ans.getPatientName());
			System.out.println(ans.getRegistrationFee());
			System.out.println(ans.getDoctorFee());
			System.out.println(ans.getMedicinesAmount());
			System.out.println(ans.getTotalFee());
		}
		entityManager.getTransaction().commit();
		return answers;
	}

}
