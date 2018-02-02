package com.utility;

import java.io.IOException;
import java.lang.reflect.Method;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.pages.Login.CSWD_LoginPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.sun.jna.platform.unix.X11.GC;

public class Test_BaseClass extends Initialize_Browser{

		CSWD_LoginPage objLogin;
		
		
		 @BeforeSuite
		    public void setUp() throws WriteException, BiffException, IOException
		    {
		    	report = new ExtentReports(
						OutputFolderStructure.extentReportfolderpath + "\\"
								+ "CSWD_ExtentReport.html", true);
		    }
		    
		    @BeforeClass
		    public void getCurrentClassName()
		    {
		    	System.out.println("Threads number before Class: "+Thread.activeCount());
		    	currentTClassName = this.getClass().getName().substring(15);
		    	System.out.println("Currently Running Class Name: Before Class "+ currentTClassName);
		    	//map = reader.getDataInMap(currentTClassName);   
		    }
		    
		    @Parameters({"browser","nodeIP","platform","remoteExecution"})
		    @BeforeMethod
		    public void getCurrentTCNameAndLaunchBrowser(String browser, String nodeIP, String platform, String remoteExecution,Method method) throws Exception
		    {
		    	currentTCName = method.getName();
		    	System.out.println("Active Thread ID in Method: "+ Thread.currentThread().getId());

		        Thread.currentThread().setName(currentTCName);
		        reader.getDataInMap(this.getClass().getName().substring(15),Thread.currentThread().getName());
		        excelWrite.createTCResultTable(); 
		        logger = report.startTest(this.getClass().getName().substring(15)+":"+currentTCName);
		        getDataInMapForCurrentTC(Thread.currentThread().getName(), logger);
				setup(browser,nodeIP,platform,remoteExecution);
				objLogin = new CSWD_LoginPage(driver);
				objLogin.login_To_CSWD_application();
		    }
		    
			@AfterMethod
		    public void driverClose(Method method)
			{
				
				currentTCName = method.getName();
				String result=loggerMap.get(currentTCName).getRunStatus().toString();
				System.out.println("TC Status: "+result);
				reader.updateStatusInExcel("CSWD_Test_Cases",result.toUpperCase(), currentTCName);
				//driver.close();
			//	driver.quit();
				//System.gc();
				report.endTest(loggerMap.get(currentTCName));
				WriteExcelFile.columnCountMap.remove(currentTCName);
				WriteExcelFile.rowCountMap.remove(currentTCName);
				loggerMap.remove(currentTCName);
				map.remove(currentTCName);
				report.flush();
				driver.close();
				driver.quit();
				
				
			}
		    
			
		    @AfterSuite
		    public void tearDown() throws WriteException, IOException, InterruptedException
		    {
		    	
		    	loggerMap.clear();
		    	map.clear();
		    	WriteExcelFile.columnCountMap.clear();
		    	WriteExcelFile.rowCountMap.clear();
				WriteExcelFile.closeXLSheet();
				report.close();
				
		    }
	}