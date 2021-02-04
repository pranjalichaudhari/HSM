package com.cg.hsm.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Scanner;
import com.cg.hsm.dao.DoctorDAO;
import com.cg.hsm.dao.FinanceDAO;
import com.cg.hsm.dao.PatientDAO;
import com.cg.hsm.daoimpl.DoctorDAOImpl;
import com.cg.hsm.daoimpl.FinanceDAOImpl;
import com.cg.hsm.daoimpl.PatientDAOImpl;
import com.cg.hsm.domain.Doctor;
import com.cg.hsm.domain.Finance;
import com.cg.hsm.domain.Patient;
import com.cg.hsm.domain.PatientCase;
import com.cg.hsm.exception.DoctorDetailsNotFound;
import com.cg.hsm.exception.FinanceFeeColumnEmpty;
import com.cg.hsm.exception.PatientCaseDetailsNotFound;
import com.cg.hsm.exception.PatientDetailsNotFound;
import com.cg.hsm.exception.PatientFinanceDetailsNotFound;
import com.cg.hsm.service.FinanceService;
import com.cg.hsm.serviceimpl.FinanceServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
/**
 *This class tests the functionalities of FinanceDAOImpl Class 
 *It checks whether all CRUD operations are performed correctly and ensures data is stored in database 
 * @author Kethu_Greeshma
 *
 */
public class FinanceDAOImplTest {

	public static void main(String[] args) {
		
		Scanner sca = new Scanner(System.in);
		
		String response = "";
		do {
			response = "";
			displayOptions();
			System.out.println("Enter your choice");
			int choice = sca.nextInt();
			sca.nextLine();
			switch(choice) {
				case 1:
					System.out.println("Enter the patient name");
					String name = sca.nextLine();
					addPatientFinanceDetails(name);
					break;
					
				case 2:
					System.out.println("Enter the patient name");
					String name1 = sca.nextLine();
					getPatientBill(name1);
					break;
				
				case 3:
					System.out.println("Enter Patient Name");
					String name2 = sca.nextLine();
					System.out.println("Enter the updated amount");
					double amount = sca.nextDouble();
					updatePatientFinanceDetails(name2, amount);
					break;
		
				case 4:
					System.out.println("Enter Patient Name");
					String name3 = sca.nextLine();
					removePatientFinanceDetails(name3);
					break;
			
				case 5:
					
					try {
						getAllFinanceDetails();
					} catch (FileNotFoundException e) {
						System.out.println("File is not found");
					} catch (DocumentException e) {
						System.out.println("Document not found");
					}
					break;
				case 6:
					System.out.println("Thank you for using the application");
					System.exit(0);
				
				default:
					
			}
			System.out.println("Do you want to continue(Yes/No)");
			response = sca.nextLine();
			if(response.equalsIgnoreCase("no")) {
					System.out.println("Thank you for using the application!!!");
					break;
			}
		}while(response.equalsIgnoreCase("yes"));
		sca.close();
	}

	
	private static void displayOptions() {
		System.out.println("1. Add Finance Details");
		System.out.println("2. Get bill of a patient");
		System.out.println("3. Update Finance Details");
		System.out.println("4. Remove patient finance details");
		System.out.println("5. Get all finance details");
		System.out.println("6. Exit");
		
	}
	
	
	
	private static void addPatientFinanceDetails(String name) {
		
		FinanceDAO financeDAO = new FinanceDAOImpl();
		PatientDAO patientDAO = new PatientDAOImpl();
		DoctorDAO doctorDAO = new DoctorDAOImpl();
		
		Patient patient = new Patient();
		
		try {
			List<Patient> patients = patientDAO.getAllPatientDetails();
			if(patients.size()==0) {
				throw new PatientDetailsNotFound("patients data from Patient table is null");
			}
			else {
				for(Patient pat:patients) {
					if(pat.getPatientName().equalsIgnoreCase(name)) {
						 patient = pat;
						 break;
				} 
				}
			}
		}catch(PatientDetailsNotFound e) {
			System.out.println(e);
		}
		
		
		double regFee = patient.getAdmissionFee();
		double docFee = 0;
		PatientCase patCase = patient.getPatientCase();
		try {
			if(patCase==null) {
				throw new PatientCaseDetailsNotFound("Patient case details not found");
			}
			else {
			String docName = patCase.getAssociatedDoctorName();
			
			try {
				List<Doctor> doctors = doctorDAO.listAllDoctors();
				if(doctors.size()==0) {
					throw new DoctorDetailsNotFound("Empty doctors list");
				}
				else {
				for(Doctor doc:doctors) {
					if(doc.getDoctorName().equalsIgnoreCase(docName)) {
						docFee = doc.getDoctorFee();
				} 
				}
				}
				}catch(DoctorDetailsNotFound e) {
					System.out.println(e);
				}
				double medicineCost = patCase.getMedicineFee();
				Finance finance = new Finance(patient.getPatientId(),name, regFee, docFee, medicineCost,(regFee+docFee+medicineCost));
				financeDAO.add(finance);
				System.out.println("******************Finance Details Successfully Added******************");
			}
		}catch(PatientCaseDetailsNotFound e) {
			System.out.println("Patient case details not found");
		}
	
	}
	
	
	
	private static void getPatientBill(String name) {
		
		FinanceService fService = new FinanceServiceImpl();
		
		Finance finance1 = new Finance();
		
		try {
			finance1 = fService.getPatientReport(name);
			if(finance1==null) {
				throw new PatientFinanceDetailsNotFound("Patient finance record not found");
			}
			else {
				System.out.println("**********************************Patient bill is***************************");
				System.out.println("Patient id is "+finance1.getPatientId());
				System.out.println("Patient name is "+finance1.getPatientName());
				System.out.println("Patient admission fee is "+finance1.getRegistrationFee());
				System.out.println("Patient doctor fee is "+finance1.getDoctorFee());
				System.out.println("Patient medicines amount is "+finance1.getMedicinesAmount());
				System.out.println("Patient total fee is "+finance1.getTotalFee());
			}
			}catch(PatientFinanceDetailsNotFound e) {
				System.out.println("*********Please enter valid name**********");
			}
	}
	
	
	
	private static void updatePatientFinanceDetails(String name, double amount) {
		
		FinanceDAO financeDAO = new FinanceDAOImpl();
		
		int id = 0;
		List<Finance> finances = financeDAO.findAll();
		for(Finance fin:finances) {
			if(fin.getPatientName().equalsIgnoreCase(name)) {
				id = fin.getFinanceId();
			}
		}
		try {
			financeDAO.updateFee(id,amount);
			System.out.println("***********************Finance details successfully updated!*********************");
		} catch (PatientFinanceDetailsNotFound e) {
			System.out.println("Enter correct patient name");
		} catch (FinanceFeeColumnEmpty e) {
			System.out.println("Null value present in feeColumn");
		}
	}
	
	
	
	private static void removePatientFinanceDetails(String name) {
		
		FinanceDAO financeDAO = new FinanceDAOImpl();
		
		int id = 0;
		List<Finance> finances = financeDAO.findAll();
		for(Finance fin:finances) {
			if(fin.getPatientName().equalsIgnoreCase(name)) {
				id = fin.getFinanceId();
			}
		}
		try {
			financeDAO.remove(id);
			System.out.println("********************Finance details successfully removed!**************************");
		} catch (PatientFinanceDetailsNotFound e) {
			System.out.println("Enter correct patient name");
		}
	}
	
	
	
	private static void getAllFinanceDetails() throws DocumentException, FileNotFoundException {

		FinanceDAO financeDAO = new FinanceDAOImpl();
		
		System.out.println("***********Finance Details****************");
		List<Finance> finances = financeDAO.findAll();
		for(Finance fin:finances) {
			System.out.println(fin);
		}
		try {
			if(finances.size()==0) {
				throw new PatientFinanceDetailsNotFound("");
			}
			else {
			for(Finance fin:finances) {
				System.out.println(fin);
			}
			String file_name = "C:\\Users\\kethu greeshma\\Documents\\HMS\\patientsBill.pdf";
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file_name));
			document.open();
			for(Finance fin: finances) {
				document.add(new Paragraph("Patient Finance id is "+fin.getFinanceId()));
				document.add(new Paragraph("Patient id is "+fin.getPatientId()));
				document.add(new Paragraph("Patient name is "+fin.getPatientName()));
				document.add(new Paragraph("Patient admission fee is "+fin.getRegistrationFee()));
				document.add(new Paragraph("Patient doctor fee is "+fin.getDoctorFee()));
				document.add(new Paragraph("Patient medicinea amount is "+fin.getMedicinesAmount()));
				document.add(new Paragraph("Patient total fee is "+fin.getTotalFee()));
				document.add(new Paragraph("*******************************************************"));
			}
			document.close();
			System.out.println("***************** Patient finance details PDF successfully generated**************************");
			}
			}catch(PatientFinanceDetailsNotFound e) {
				System.out.println("Patient finance details not found");
			}
	}

}
