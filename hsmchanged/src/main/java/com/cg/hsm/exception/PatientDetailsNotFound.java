package com.cg.hsm.exception;

/**
 * This class is an exception class to throw exception when particular patient is not found in finance table
 * @author kethu_greeshma
 *
 */

public class PatientDetailsNotFound extends Exception{
	private static final long serialVersionUID = 1L;
	/**
	 * Create FinanceFeeColumnEmpty object without error message
	 */
	public PatientDetailsNotFound() {
		super();
	}
	
	/**
	 * Create FinanceFeeColumnEmpty object with error message
	 */
	public PatientDetailsNotFound(String errMsg) {
		super(errMsg);
	}
}
