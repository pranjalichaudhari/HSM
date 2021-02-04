package com.cg.hsm.exception;
/**
 * This class is an exception class to throw exception when particular patient is not found in finance table
 * @author kethu_greeshma
 *
 */
public class FinanceFeeColumnEmpty extends Exception{
	private static final long serialVersionUID = 1L;
	/**
	 * Create FinanceFeeColumnEmpty object without error message
	 */
	public FinanceFeeColumnEmpty() {
		super();
	}
	
	/**
	 * Create FinanceFeeColumnEmpty object with error message
	 */
	public FinanceFeeColumnEmpty(String errMsg) {
		super(errMsg);
	}
}
