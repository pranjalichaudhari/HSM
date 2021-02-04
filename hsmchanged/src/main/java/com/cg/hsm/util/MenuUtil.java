package com.cg.hsm.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Scanner;

import com.cg.hsm.dao.DoctorDAO;
import com.cg.hsm.dao.FinanceDAO;
import com.cg.hsm.dao.PatientDAO;
import com.cg.hsm.daoimpl.DoctorDAOImpl;
import com.cg.hsm.daoimpl.FinanceDAOImpl;
import com.cg.hsm.daoimpl.PatientDAOImpl;
import com.cg.hsm.daoimpl.RoleDAOImpl;
import com.cg.hsm.daoimpl.UserDAOImpl;
import com.cg.hsm.domain.Doctor;
import com.cg.hsm.domain.Finance;
import com.cg.hsm.domain.InsurancePolicy;
import com.cg.hsm.domain.Patient;
import com.cg.hsm.domain.PatientCase;
import com.cg.hsm.domain.PatientHistory;
import com.cg.hsm.domain.Role;
import com.cg.hsm.domain.User;
import com.cg.hsm.exception.AssociatedDoctorNullException;
import com.cg.hsm.exception.DoctorDetailsNotFound;
import com.cg.hsm.exception.FeeNotFoundException;
import com.cg.hsm.exception.FinanceFeeColumnEmpty;
import com.cg.hsm.exception.InsufficientContactException;
import com.cg.hsm.exception.InsufficientDiseaseDescriptionException;
import com.cg.hsm.exception.InsufficientExperienceException;
import com.cg.hsm.exception.InsufficientHoursOfAvailabilityException;
import com.cg.hsm.exception.NameNotFoundException;
import com.cg.hsm.exception.PatientCaseDetailsNotFound;
import com.cg.hsm.exception.PatientDetailsNotFound;
import com.cg.hsm.exception.PatientFinanceDetailsNotFound;
import com.cg.hsm.service.FinanceService;
import com.cg.hsm.serviceimpl.FinanceServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;
/**
 *  This MenuUtil class will have patient and doctor interactions with the Admin
 * @author ANJUM
 *
 */
public class MenuUtil 
{
	String continuechoice;
	boolean result;
	public void start() throws InsufficientExperienceException, InsufficientHoursOfAvailabilityException, PatientFinanceDetailsNotFound, FinanceFeeColumnEmpty, DocumentException, FileNotFoundException 
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("**WELCOME TO HEALTH MANAGEMENT SERVICE**");
		System.out.println("Please Enter your role");
		System.out.println("1.Admin");
		System.out.println("2.Doctor");
		int role=sc.nextInt();
		sc.nextLine();
		UserDAOImpl userdaoimpl=new UserDAOImpl();
		if(role==1)
		{
			System.out.println("enter your username");
			String username=sc.nextLine();
			System.out.println("Enter your Password");
			String password=sc.nextLine();
			result = userdaoimpl.checkAdminAuthentication(username,password);
		}
		if(role==2)
		{
			System.out.println("enter your username");
			String username=sc.nextLine();
			System.out.println("Enter your Password");
			String password=sc.nextLine();	
			result= userdaoimpl.checkAuthentication(username,password);
		}
		if(result==true)
        {
		do
		{
		System.out.println("**HEALTH MANAGEMENT SERVICE**");
		System.out.println("1.Patient Dashboard");
		System.out.println("2.Doctor Dashbord");
		System.out.println("3.Finance Dashbord");
		System.out.println("4.Exit");
		System.out.println("Enter your choice : ");
		int choice=sc.nextInt();
		if(choice >= 1 && choice <= 4)
		{
		sc.nextLine();
		switch(choice)
		{
		/*
		 * Patient's Dashboard
		 */
		case 1 :
			System.out.println("**PATIENT**");
			System.out.println("1.Patient Registration");
			System.out.println("2.Update Patient Personal Details");
			System.out.println("3.Update Patient History Details");
			System.out.println("4.Update Patient policy");
			System.out.println("5.Delete Patient Details");
			System.out.println("6.List of Patients");
			int choice1=sc.nextInt();
			switch(choice1)
			{
			/*
			 * Registering a patient
			 */
			case 1 :
				sc.nextLine();
				Patient patient = new Patient();
				InsurancePolicy policy = new InsurancePolicy();

				System.out.println("-------Please Provide below details---------");
				System.out.println("Enter Patient's Name : ");
				String patientName=sc.nextLine();
				try {
					if(patientName.isBlank())
						throw new NameNotFoundException("No name provided for patient");
					else
						patient.setPatientName(patientName);
				}
				catch(NameNotFoundException exp) {
					exp.printStackTrace();
				}
				System.out.println("Enter Patient's Age : ");
				patient.setPatientAge(sc.nextInt());
				sc.nextLine();
				System.out.println("Enter Patient's Contact Number : ");

				long patientContact = sc.nextLong();

				int temp = String.valueOf(patientContact).length();
				try {
				if (temp > 10 || temp < 10)
					throw new InsufficientContactException("Your phone contact number must be equal to 10");
				else
					patient.setPatientContact(patientContact);
				}catch(InsufficientContactException exp) {
					exp.printStackTrace();
				}
				sc.nextLine();
				System.out.println("Enter Patient's Address : ");
				patient.setAddress(sc.nextLine());
				System.out.println("Enter Patient's Symptoms : ");
				patient.setSymptoms(sc.nextLine());
				System.out.println("Enter Patient's Admission Fees : ");
				float admissionFee=sc.nextFloat();
				try {
					if(admissionFee==0) {
						throw new FeeNotFoundException("admission fee is not provided");
					}
					else
						patient.setAdmissionFee(admissionFee);
				}
				catch(FeeNotFoundException exh) {
					exh.printStackTrace();
				}
				
				sc.nextLine();
				System.out.println("Do you want a new Policy ?");
				String choice11 = sc.nextLine();
				if (choice11.equalsIgnoreCase("yes")) {

					String num = new DecimalFormat("##").format(Math.random() * 1000000);
					policy.setPolicyId(patient.getPatientName().substring(0, 2) + num);
					policy.setPolicyName("Individual Star Health Policy");
					System.out.println("Your new policy Individual Star Health Policy got created!");
					
				} else {
			           }
				patient.setInsurancePolicy(policy);
                System.out.println("Have you gone through any treatment before : yes|no");
                String history=sc.nextLine();
                if(history.equalsIgnoreCase("yes"))
                {
				PatientHistory patienthistory = new PatientHistory();
				System.out.println("------Patient's History-------");
				System.out.println("Enter Patient's Blood Group :  ");
				patienthistory.setBloodGroup(sc.nextLine());
				System.out.println("Enter Patient's Previous Disease Description : ");
				patienthistory.setDiseaseName(sc.nextLine());
				System.out.println("Enter Treatment Status : ");
				patienthistory.setTreatmentStatus(sc.nextLine());
				System.out.println("Enter Diet Advised : ");
				patienthistory.setDietAdvised(sc.nextLine());
				System.out.println("Enter Report Details : ");
				patienthistory.setHistoryReports(sc.nextLine());
				patient.setPatientHistory(patienthistory);
				PatientDAOImpl impl = new PatientDAOImpl();
				impl.addPatient(patient);
                }
                else
                {	
                }
				PatientCase patientcase = new PatientCase();
				System.out.println("------Patient's Case-------");
				System.out.println("Enter Patient's Disease Description : ");
				String diseaseDescription = sc.nextLine();
				try {
					if (diseaseDescription.isEmpty()) {
						throw new InsufficientDiseaseDescriptionException("disease description is not provided");

					}
				} catch (InsufficientDiseaseDescriptionException exe) {
					exe.printStackTrace();

				}
				patientcase.setDiseaseDescription(diseaseDescription);
				System.out.println("Enter Patient's Medicine Description : ");
				patientcase.setMedicines(sc.nextLine());
				System.out.println("Enter Patient's Medicine Fee : ");
				float medicineFee=sc.nextFloat();
				try {
					if(medicineFee==0) {
						throw new FeeNotFoundException("medicine fee is not entered");
					}
					else
						patientcase.setMedicineFee(medicineFee);
				}
				catch(FeeNotFoundException exception) {
					exception.printStackTrace();
				}
				sc.nextLine();
				System.out.println("Enter Doctor Associated : ");
				DoctorDAOImpl doctordaoimpl=new DoctorDAOImpl();
				doctordaoimpl.listAllDoctors();
				String associatedDoctor = sc.nextLine();
				try {
					if (associatedDoctor.isEmpty()) {
						throw new AssociatedDoctorNullException("Associated Doctor is not given");
					}
					else {
						patientcase.setAssociatedDoctorName(associatedDoctor);
					}
				} catch (AssociatedDoctorNullException exp) {
					exp.printStackTrace();
				}
				
				System.out.println("Enter Patient's Current Treatment Description : ");
				patientcase.setCurrentTreatment(sc.nextLine());
				System.out.println("Enter Patient's Reports Taken : ");
				patientcase.setReports(sc.nextLine());
				
				patient.setPatientCase(patientcase);

				
				System.out.println("Patient Registered Successfully!");
				  break;
				  
				  /*
				   * Updating the patients details
				   */
			case 2 :
				System.out.println("------------Enter the data to be updated------------------");
				Patient patient1 = new Patient();
				System.out.println("Enter the id of the patient");
				int patientId = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter Patient's Name : ");
				String patientName1=sc.nextLine();
				try {
					if(patientName1.isBlank())
						throw new NameNotFoundException("No name provided for patient");
					else
						patient1.setPatientName(patientName1);
				}
				catch(NameNotFoundException exp) {
					exp.printStackTrace();
				}
				System.out.println("Enter Patient's Age : ");
				patient1.setPatientAge(sc.nextInt());
				System.out.println("Enter Patient's Contact Number : ");

				long patientContact1 = sc.nextLong();

				int temp1 = String.valueOf(patientContact1).length();
				try {
				if (temp1 > 10 || temp1 < 10)
					throw new InsufficientContactException("Your phone contact number must be equal to 10");
				else
					patient1.setPatientContact(patientContact1);
				}catch(InsufficientContactException exp) {
					exp.printStackTrace();
				}
				sc.nextLine();
				System.out.println("Enter Patient's Address : ");
				patient1.setAddress(sc.nextLine());
				System.out.println("Enter Patient's Symptoms: ");
				patient1.setSymptoms(sc.nextLine());
				System.out.println("Enter Patient's Admission Fees : ");
				float admissionFee1=sc.nextFloat();
				try {
					if(admissionFee1==0) {
						throw new FeeNotFoundException("admission fee is not provided");
					}
					else
						patient1.setAdmissionFee(admissionFee1);
				}
				catch(FeeNotFoundException exh) {
					exh.printStackTrace();
				}
				
				sc.nextLine();
				PatientDAOImpl impl1 = new PatientDAOImpl();
				impl1.updatePatient(patient1, patientId);
				System.out.println("Patient Updated Successfully!");
				  break;
				  /*
				   * Updating patient's history
				   */
			case 3 :
				System.out.println("----------Patient's History------------");
				System.out.println("Enter the Id of the Patient");
				int patientId1 = sc.nextInt();
				sc.nextLine();
				PatientHistory patientHistory = new PatientHistory();
				System.out.println("Enter Patient disease");
				patientHistory.setDiseaseName(sc.next());
				sc.nextLine();
				System.out.println("Enter the BloodGroup");
				patientHistory.setBloodGroup(sc.next());
				sc.nextLine();
				System.out.println("Enter Diet advised to the patient");
				patientHistory.setDietAdvised(sc.next());
				sc.nextLine();
				System.out.println("Enter the treatment status of the patient");
				patientHistory.setTreatmentStatus(sc.next());
				sc.nextLine();
				System.out.println("Enter the reports name");
				patientHistory.setHistoryReports(sc.next());
				sc.nextLine();
				PatientDAO patientDao = new PatientDAOImpl();
				Patient patient2 = new Patient();
				patientDao.updatePatient(patient2 , patientId1);
				  break;
				  /*
				   * Updating patient's Insurance policy
				   */
			case 4 :
				System.out.println("----------Policy Information------------");
				System.out.println("Enter Patient Id: ");
				int patientId11 = sc.nextInt();
				PatientDAOImpl impl11 = new PatientDAOImpl();
				impl11.updateInsurancePolicy(patientId11);
				System.out.println("Update Successful");
				  break;
				  /*
				   * Deleting a patient
				   */
			case 5 :
				System.out.println("**Deleting details** ");
				//Patient patient2 = new Patient();
				System.out.println("Enter Patient id : ");
				int patientId2 = sc.nextInt();
				PatientDAOImpl impl2 = new PatientDAOImpl();
				impl2.deletePatient(patientId2);
				System.out.println("**Deleted patient Details**");
				  break;
				  /*
				   * List of Patients
				   */
			case 6 :
				PatientDAOImpl impl3 = new PatientDAOImpl();
				impl3.getAllPatientDetails();
				  break;
		    default :
		    	break;
			}
			break;
			/*
			 * Doctors Dashboard
			 */
		case 2 :
			System.out.println("**Doctor**");
			System.out.println("1.Doctor Registration");
			System.out.println("2.Update Doctor Details");
			System.out.println("3.Update Doctor Fee Details");
			System.out.println("4.Delete Doctor Details");
			System.out.println("5.List of Doctors");
			int choice2=sc.nextInt();
			switch(choice2)
			{
			/*
			 * Doctor Registration
			 */
			case 1 :
				System.out.println("-------Please Provide below details---------");
				Doctor doctor = new Doctor();
				sc.nextLine();
				System.out.println("Enter Doctor's Name : ");
				String doctorName = sc.nextLine();
				try {
					if(doctorName.isBlank())
						throw new NameNotFoundException("no name is provided");
					else
						doctor.setDoctorName(doctorName);
				}catch(NameNotFoundException exception) {
					exception.printStackTrace();
				}
				System.out.println("Create your username");
				String username1 = sc.next();
				doctor.setUsername(username1);
				
				System.out.println("Create your password");
				String password1 = sc.next();
				doctor.setPassword(password1);
				
				System.out.println("Enter Doctor's Contact Number : ");
				long doctorContact = sc.nextLong();
				int temp = String.valueOf(doctorContact).length();
				try {
					if(temp<10 || temp>10)
						throw new InsufficientContactException("contact is provided is insufficient");
					else
						doctor.setContactNumber(doctorContact);
				}catch(InsufficientContactException exception) {
					exception.printStackTrace();

				}
				
				
				sc.nextLine();
				
				System.out.println("Enter Number Of Hours Doctor is Available : ");
				int hours = sc.nextInt();
				try {
				if (hours < 4)
					throw new InsufficientHoursOfAvailabilityException("Your Hours of Availability is Insufficient");
				else
					doctor.setHoursOfAvailability(hours);
				}catch(InsufficientHoursOfAvailabilityException  exception) {
					exception.printStackTrace();
				}
				sc.nextLine();
				System.out.println("Enter Doctor's Specializaion : ");
				doctor.setSpecialization(sc.nextLine());
				System.out.println("Enter Doctor's Degree Name : ");
				doctor.setDegree(sc.nextLine());
				System.out.println("Enter Doctor's Total Years of Experience : ");
				int years = sc.nextInt();
				try {
				if (hours < 4)
					throw new InsufficientExperienceException("Your Years of Experience is Insufficient");
				else
					doctor.setYearsOfExperience(years);
				}catch(InsufficientExperienceException exception) {
					exception.printStackTrace();
				}
				sc.nextLine();
				System.out.println("Enter Doctor's Fees: ");
				float doctorFee=sc.nextFloat();
				try {
					if(doctorFee==0) {
						throw new FeeNotFoundException("Doctor fee is not provided");
					}
					else
						doctor.setDoctorFee(doctorFee);
				}
				catch(FeeNotFoundException excepion) {
					excepion.printStackTrace();
				}
				sc.nextLine();
				DoctorDAOImpl impl = new DoctorDAOImpl();
				impl.addDoctor(doctor);
				
				User user=new User();
				user.setUserId(username1);
				user.setUserPassword(password1);
				user.setStatus("Active");
				user.setCreatedBy("Admin");
				user.setCreatedOn(new Date(System.currentTimeMillis()));
				UserDAOImpl userdaoimpl1=new UserDAOImpl();
				userdaoimpl1.addAuthentication(user);
				
				
				Role role1 = new Role();
				role1.setRoleName("Doctor");
				role1.setUsername(username1);
				role1.setPassword(password1);
				role1.setUpdatedDate(new Date(System.currentTimeMillis()));
				RoleDAOImpl roleImpl = new RoleDAOImpl();
				roleImpl.registerRole(role1);
				
				
				
				System.out.println("Doctor Registered Successfully!");
				break;
				  /*
				   * Update Doctor details
				   */
			case 2 :
				System.out.println("-------Updating details-------- ");
				Doctor doctor1 = new Doctor();
				System.out.println("Enter Doctor id : ");
				int doctorId = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter Doctor's Name : ");
				String doctorName1 = sc.nextLine();
				try {
					if(doctorName1.isBlank())
						throw new NameNotFoundException("no name is provied");
					else
						doctor1.setDoctorName(doctorName1);
				}catch(NameNotFoundException exception) {
					exception.printStackTrace();
				}
				System.out.println("Enter Doctor's Contact Number : ");
				long doctorContact1 = sc.nextLong();
				int temp1 = String.valueOf(doctorContact1).length();
				try {
					if(temp1<10 || temp1>10)
						throw new InsufficientContactException("contact is provided is insufficient");
					else
						doctor1.setContactNumber(doctorContact1);
				}catch(InsufficientContactException exception) {
					exception.printStackTrace();

				}
				
				
				sc.nextLine();
				System.out.println("Enter Number Of Hours Doctor is Available : ");
				doctor1.setHoursOfAvailability(sc.nextInt());
				sc.nextLine();
				System.out.println("Enter Doctor's Specializaion : ");
				doctor1.setSpecialization(sc.nextLine());
				System.out.println("Enter Doctor's Degree Name : ");
				doctor1.setDegree(sc.nextLine());
				System.out.println("Enter Doctor's Total Number of Experience : ");
				doctor1.setYearsOfExperience(sc.nextInt());
				sc.nextLine();
				System.out.println("Enter Doctor's Fees: ");
				float doctorFee1=sc.nextFloat();
				try {
					if(doctorFee1==0) {
						throw new FeeNotFoundException("Doctor fee is not provided");
					}
					else
						doctor1.setDoctorFee(doctorFee1);
				}
				catch(FeeNotFoundException excepion) {
					excepion.printStackTrace();
				}

				DoctorDAOImpl doctorDaoImpl = new DoctorDAOImpl();
				doctorDaoImpl.updateDoctor(doctor1, doctorId);
				System.out.println("-------Updated Doctor Details-------- ");
				  break;
				  /*
				   * Updating Doctors fee
				   */
			case 3 :
				System.out.println("Enter Doctor id : ");
				int doctorId1 = sc.nextInt();
				System.out.println("Enter Doctor's updated Fee : ");
				int updatedDoctorFee = sc.nextInt();
				DoctorDAOImpl doctorDaoImpl1 = new DoctorDAOImpl();
				doctorDaoImpl1.updateDoctorFee(doctorId1, updatedDoctorFee);
				System.out.println("-------Updated Doctor Fees-------- ");
				  break;
				  /*
				   * Deleting doctor details
				   */
			case 4 :
				System.out.println("Enter Doctor id : ");
				int doctorId11 = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter Doctor username : ");
				String docusername = sc.nextLine();
				DoctorDAOImpl doctorDaoImpl11 = new DoctorDAOImpl();
				doctorDaoImpl11.deleteDoctor(doctorId11);
				UserDAOImpl userdaoimpl2=new UserDAOImpl();
				userdaoimpl2.updateStatus(docusername);			
				System.out.println("-------Deleted Doctor Details-------- ");
				  break;
				  /*
				   * List of all Doctors
				   */
			case 5 :
				DoctorDAOImpl doctorDaoImpl2 = new DoctorDAOImpl();
				doctorDaoImpl2.listAllDoctors();
				  break;
			default :
					  break;
			}
		break;
		case 3 :
			System.out.println("**Finance**");
			System.out.println("1. Adding Patient finance Details"); // patient details into finance table
			System.out.println("2. Bill generation and reports"); // patient report & bill details
			System.out.println("3. Update Financial Details of a patient"); // update 
			System.out.println("4. Remove patient finance details"); 
			System.out.println("5. Get all finance details"); 
			int choice3=sc.nextInt();
			sc.nextLine();
			switch(choice3)
			{
			/*
			 *  Adding patient finance details into finance table
			 */
			case 1 :
				FinanceDAO financeDAO = new FinanceDAOImpl();
				//PatientDAO patientDAO = new PatientDAOImpl();
				DoctorDAO doctorDAO = new DoctorDAOImpl();
				System.out.println("Enter the patient name");
				String name = sc.nextLine();
				Patient patient = new Patient();
				PatientDAO patientDAO = new PatientDAOImpl();
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
						System.out.println("**Finance Details Successfully Added**");
					}
				}catch(PatientCaseDetailsNotFound e) {
					System.out.println("Patient case details not found");
				}
				break;
				/*
				 * Generating bill and reports of a patient
				 */
			case 2 :
				System.out.println("Enter the patient name");
				String name1 = sc.nextLine();
				Finance finance1 = new Finance();
				FinanceService fService = new FinanceServiceImpl();
				finance1 = fService.getPatientReport(name1);
				System.out.println("**Patient report is**");
				System.out.println("Patient id is "+finance1.getPatientId());
				System.out.println("Patient name is "+finance1.getPatientName());
				System.out.println("Patient admission fee is "+finance1.getRegistrationFee());
				System.out.println("Patient doctor fee is "+finance1.getDoctorFee());
				System.out.println("Patient medicines amount is "+finance1.getMedicinesAmount());
				System.out.println("Patient total fee is "+finance1.getTotalFee());
				  break;
				  /*
				   * update fee of the patient
				   */
			case 3 :
				FinanceDAO financeDAO7 = new FinanceDAOImpl();
				System.out.println("Enter Patient Name");
				String name2 = sc.nextLine();
				System.out.println("Enter the updated amount");
				double amount = sc.nextDouble();
				sc.nextLine();
				int id = 0;
				List<Finance> finances = financeDAO7.findAll();
				for(Finance fin:finances) {
					if(fin.getPatientName().equalsIgnoreCase(name2)) {
						id = fin.getFinanceId();
					}
				}
				try {
					financeDAO7.updateFee(id,amount);
					System.out.println("**Finance details successfully updated!**");
				} catch (PatientFinanceDetailsNotFound e) {
					System.out.println("Enter correct patient name");
				} catch (FinanceFeeColumnEmpty e) {
					System.out.println("Null value present in feeColumn");
				}
				  break;
				  /*
				   * Deleting financial details of a patient
				   */
			case 4 :
				System.out.println("Enter Patient Name");
				String name3 = sc.nextLine();
				int id2 = 0;
				FinanceDAO financeDAO2 = new FinanceDAOImpl();
     
        		List<Finance> finances3 = financeDAO2.findAll();
        		for(Finance fin:finances3) {
        			if(fin.getPatientName().equalsIgnoreCase(name3)) {
        				id2 = fin.getFinanceId();
        			}
        		}
        		try {
        			financeDAO2.remove(id2);
        			System.out.println("**Finance details successfully removed!***");
        		} catch (PatientFinanceDetailsNotFound e) {
        			System.out.println("Enter correct patient name");
        		}
				  break;
				  /*
				   *  list of finance details 
				   */
			case 5 :
				System.out.println("**Finance Details***");
				FinanceDAO financeDAO3 = new FinanceDAOImpl();
				
				System.out.println("**Finance Details**");
				List<Finance> finances5 = financeDAO3.findAll();
				for(Finance fin:finances5) {
					System.out.println(fin);
				}
				try {
					if(finances5.size()==0) {
						throw new PatientFinanceDetailsNotFound("");
					}
					else {
					for(Finance fin:finances5) {
						System.out.println(fin);
					}
					String file_name = "C:\\Users\\Nadeemsharief\\Desktop\\changedhsm\\bills\\patientsBill.pdf";
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream(file_name));
					document.open();
					for(Finance fin: finances5) {
						document.add(new Paragraph("Patient Finance id is "+fin.getFinanceId()));
						document.add(new Paragraph("Patient id is "+fin.getPatientId()));
						document.add(new Paragraph("Patient name is "+fin.getPatientName()));
						document.add(new Paragraph("Patient admission fee is "+fin.getRegistrationFee()));
						document.add(new Paragraph("Patient doctor fee is "+fin.getDoctorFee()));
						document.add(new Paragraph("Patient medicinea amount is "+fin.getMedicinesAmount()));
						document.add(new Paragraph("Patient total fee is "+fin.getTotalFee()));
						document.add(new Paragraph("******************"));
					}
					document.close();
					System.out.println("**Patient finance details PDF successfully generated**");
					}
					}catch(PatientFinanceDetailsNotFound e) {
						System.out.println("Patient finance details not found");
					}		 
				break;
				default :
					break;
			}
			case 4 :
				System.out.println(" Thank You ");
				System.exit(0);
				break;
			default :
				break;
			
		}
		
	}
		else
		{
			System.out.println("choose between 1 and 4");
		}
		System.out.println("Do you want to continue Yes or No");
		continuechoice=sc.next();
		if(continuechoice.equalsIgnoreCase("no")) {
			System.out.println("Thanks for using the application");
		}
	
		}while(continuechoice.equalsIgnoreCase("yes"));
		sc.close();
        }
		else
		{
			System.out.println("Please enter correct userid and password ");
		}
	}
}