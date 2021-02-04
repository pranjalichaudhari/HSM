package com.cg.hsm.exception;

public class InsufficientHoursOfAvailabilityException extends Exception {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Create InsufficientHoursOfAvailabilityException object without error message
	 */
	public InsufficientHoursOfAvailabilityException() {
		super();
	}
	/**
	 * Create InsufficientHoursOfAvailabilityException object with error message
	 */
	public InsufficientHoursOfAvailabilityException(String errMsg){
		super(errMsg);

}
}