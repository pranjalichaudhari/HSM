package com.cg.hsm.tdd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.cg.hsm.exception.InsufficientExperienceException;
import com.cg.hsm.exception.InsufficientHoursOfAvailabilityException;
import com.cg.hsm.test.DoctorDAOImplTest;



public class DoctorTest {
	

	private DoctorDAOImplTest doctorDAOImplTest;
	
	 
	
	  @SuppressWarnings("static-access")
	  
	  @Test public void testAddDoctorGivenDoctorIdShouldReturnSatement() throws
	  InsufficientHoursOfAvailabilityException, InsufficientExperienceException{
	  String result = doctorDAOImplTest.registerDoctor();
	  assertEquals("Added doctor to database",result); }
	 
	
	/*
	 * @SuppressWarnings("static-access")
	 * 
	 * @Test public void testDeleteDoctorGivenDoctorIdShouldReturnStatement() {
	 * String result = doctorDAOImplTest.removeDoctor();
	 * assertEquals("removed doctor from database",result);
	 * 
	 * }
	 */
	  
	
	  @SuppressWarnings("static-access")
	  
	  @Test public void testUpdateDoctorIsGivenInputShouldReturnStatement() {
	  String output=doctorDAOImplTest.updateDoctor();
	  assertEquals("Doctor Details Updated",output); }
	 
	  
	
	  @SuppressWarnings("static-access")
	  
	  @Test public void testUpdateDoctorFeeIsGivenInputShouldReturnStatement() {
	  String output=doctorDAOImplTest.updateDoctorFee();
	  assertEquals("Doctor Fees Updated",output); }
	 

	
}
