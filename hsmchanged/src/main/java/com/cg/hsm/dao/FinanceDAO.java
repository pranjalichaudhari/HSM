package com.cg.hsm.dao;

import java.util.List;
import com.cg.hsm.domain.Finance;
import com.cg.hsm.exception.FinanceFeeColumnEmpty;
import com.cg.hsm.exception.PatientFinanceDetailsNotFound;

/**
 * This FinanceDAO interface performs CRUD operations on Finance
 * @author kethu_greeshma
 *
 */
public interface FinanceDAO {
	
	/**
	 * This addFee method will save the details of a finance object in table
	 * @param fee object to be saved
	 */
	void add(Finance finance);
	
	
	
	/**
	 * This removeFee method will remove the fee details of a finance object in table
	 * @param fee of a patient to be deleted based on id
	 */
	void remove(int id) throws PatientFinanceDetailsNotFound;
	
	
	
	/**
	 * This update method will update the fee details of a finance object in table
	 * @param fee of a patient to be updated based on id
	 */
	void updateFee(int id, double amount) throws PatientFinanceDetailsNotFound, FinanceFeeColumnEmpty;
	
	
	
	/**
	 * this findAll method will return the list of finance fee details from table if found
	 * @return list of patient fee details if found otherwise null
	 */
	List<Finance> findAll();
}
