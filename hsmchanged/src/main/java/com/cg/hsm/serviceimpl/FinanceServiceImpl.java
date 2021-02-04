package com.cg.hsm.serviceimpl;

import java.util.List;
import com.cg.hsm.dao.FinanceDAO;
import com.cg.hsm.daoimpl.FinanceDAOImpl;
import com.cg.hsm.domain.Finance;
import com.cg.hsm.exception.PatientFinanceDetailsNotFound;
import com.cg.hsm.service.FinanceService;
/**
 * This class will execute the interface FinanceService methods
 * @author kethu_greeshma
 *
 */
public class FinanceServiceImpl implements FinanceService {
	
	FinanceDAO finDAO = new FinanceDAOImpl();
	
	@Override
	public Finance getPatientReport(String name) throws PatientFinanceDetailsNotFound {
		Finance finance = new Finance();
		List<Finance> finances = finDAO.findAll();
		for(Finance fin: finances) {
			if(fin.getPatientName().equalsIgnoreCase(name)) {
				return fin;
			}
		}
		
		return finance;
	}
	
}
