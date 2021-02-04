package com.cg.hsm.tdd;

import static org.junit.Assert.assertEquals;


import com.cg.hsm.exception.InsufficientContactException;

import com.cg.hsm.test.PatientDAOImplTest;


import org.junit.Test;


public class PatientTest {

	
	@Test
	public void testDeletePatientIsGivenPatientIdShouldReturnStatement() {
		
		String output= PatientDAOImplTest.removePatient();
		assertEquals("Deleted Patient from Database",output);
		
		
		
	}
	@Test
	public void testAddPatientIsGivenPatientIdShouldReturnStatement() throws InsufficientContactException {
		String result= PatientDAOImplTest.registerPatient();
		assertEquals(result,"Added Patient in Database");
		System.out.println(result);
		
	}
	@Test
	public void testUpdatePatientHistoryIsGivenInputShouldReturnStatement() {
		String output=PatientDAOImplTest.updatePatientHistory();
		assertEquals("Patient History Updated",output);
	}
	@Test
	public void testUpdatePatientIsGivenInputShouldReturnStatement() {
				PatientDAOImplTest impl = new PatientDAOImplTest();
		@SuppressWarnings("static-access")
		String output=impl.updatePatientPersonalInfo();
assertEquals("Patient Personal Info Updated",output);
	}
	
	
	 

}