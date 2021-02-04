package com.cg.hsm.exception;
/**
 * This class is an exception class to throw exception when particular patient totalFee column is empty in finance table
 * @author kethu_greeshma
 *
 */
public class PatientFinanceDetailsNotFound extends Exception{
		
	private static final long serialVersionUID = 1L;
	/**
	 * Create PatientFinanceDetailsNotFound object without error message
	 */
	public PatientFinanceDetailsNotFound() {
		super();
	}
	
	/**
	 * Create PatientFinanceDetailsNotFound object with error message
	 */
	public PatientFinanceDetailsNotFound(String errMsg) {
		super(errMsg);
	}
}
