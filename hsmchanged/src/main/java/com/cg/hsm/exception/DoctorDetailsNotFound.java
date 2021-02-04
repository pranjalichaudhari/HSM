package com.cg.hsm.exception;

/**
 * This class is an exception class to throw exception when particular patient is not found in finance table
 * @author kethu_greeshma
 *
 */

public class DoctorDetailsNotFound extends Exception{
	private static final long serialVersionUID = 1L;
	/**
	 * Create FinanceFeeColumnEmpty object without error message
	 */
	public DoctorDetailsNotFound() {
		super();
	}
	
	/**
	 * Create FinanceFeeColumnEmpty object with error message
	 */
	public DoctorDetailsNotFound(String errMsg) {
		super(errMsg);
	}
}
