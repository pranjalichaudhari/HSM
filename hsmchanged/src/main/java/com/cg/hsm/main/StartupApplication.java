package com.cg.hsm.main;

import java.io.FileNotFoundException;

import com.cg.hsm.exception.FinanceFeeColumnEmpty;
import com.cg.hsm.exception.InsufficientExperienceException;
import com.cg.hsm.exception.InsufficientHoursOfAvailabilityException;
import com.cg.hsm.exception.PatientFinanceDetailsNotFound;
import com.cg.hsm.util.MenuUtil;
import com.itextpdf.text.DocumentException;

public class StartupApplication {

	public static void main(String args[]) throws InsufficientExperienceException, InsufficientHoursOfAvailabilityException, PatientFinanceDetailsNotFound, FinanceFeeColumnEmpty, FileNotFoundException, DocumentException
	{
		MenuUtil menuutil=new MenuUtil();
		menuutil.start();
	}

}
