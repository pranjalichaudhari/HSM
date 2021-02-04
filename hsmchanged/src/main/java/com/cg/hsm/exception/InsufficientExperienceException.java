package com.cg.hsm.exception;

public class InsufficientExperienceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create InsufficientExperienceException object without error message
	 */
	public InsufficientExperienceException() {
		super();
	}
	/**
	 * Create InsufficientExperienceException object with error message
	 */
	public InsufficientExperienceException(String errMsg){
		super(errMsg);
	}
}