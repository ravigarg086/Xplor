package com.utility;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Initialize_Browser {

	
	public static DesiredCapabilities capability;
	public WebDriver driver;
	String path = "";
	public static ExtentReports report;
	public ExtentTest logger;
	public String currentTCName;
	public String currentTClassName;
	public WriteExcelFile excelWrite = new WriteExcelFile();
	public ReadDataFromExcel reader = new ReadDataFromExcel(
			ExcelUtils.testSheetPath);
	public static HashMap<String, HashMap<String, String>> map = new HashMap<>();
	public static HashMap<String, ExtentTest> loggerMap = new HashMap<>();
	public ScreenShot ss;

	public String getUrlValue() throws Exception {
		String url = null;
		if (reader.getMapData(CommonVariable.Portal, map).equalsIgnoreCase(
				"Manager")) {
			url = reader.getCellData("Application", 1, 2);
		} else if (reader.getMapData(CommonVariable.Portal, map)
				.equalsIgnoreCase("User")) {
			url = reader.getCellData("Application", 1, 3);
		} else if (reader.getMapData(CommonVariable.Portal, map)
				.equalsIgnoreCase("Manager Call")) {
			url = reader.getCellData("Application", 1, 4);
		} else if (reader.getMapData(CommonVariable.Portal, map)
				.equalsIgnoreCase("User Call")) {
			url = reader.getCellData("Application", 1, 5);
		}
		return url;
	}

	public void setup(String browser, String nodeIP, String platform,
			String remoteExecution) throws Exception {
		try {

			System.out.println("Threads in Setup Browser: "
					+ Thread.activeCount());
			System.out.println("Active Thread ID: "
					+ Thread.currentThread().getId());
			File file;
			
			if (remoteExecution.equals("Y")) {
				System.out.println("Before cmd");
				Runtime.getRuntime()
						.exec("cmd.exe /c cd \""
								+ "C:\\Nanda\\Selenium Server Standlone"
								+ "\" & start cmd.exe /k \"java  -jar selenium-server-standalone-3.3.1.jar -role hub\"");
				System.out.println("After cmd");
			}

			switch (browser) {
			case "firefox":

				path = getPath();
				file = new File(path, "//Drivers//geckodriver.exe");
				System.setProperty("webdriver.gecko.driver",
						file.getAbsolutePath());

				if (remoteExecution.equals("Y")) {
					capability = DesiredCapabilities.firefox();
					capability.setBrowserName(browser);
					capability.setPlatform(Platform.fromString(platform));
					System.out.println("After Platform");
					driver = new RemoteWebDriver(new URL("http://" + nodeIP
							+ ":1212/wd/hub"), capability);
					driver.manage().timeouts()
							.implicitlyWait(25, TimeUnit.SECONDS);

				} else {

					driver = new FirefoxDriver();
					driver.manage().timeouts()
							.implicitlyWait(25, TimeUnit.SECONDS);
				}
				break;
				
			case "internet explorer":

				path = getPath();
				file = new File(path, "//Drivers//IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver",
						file.getAbsolutePath());

				if (remoteExecution.equals("Y")) {

					capability = DesiredCapabilities.internetExplorer();
					capability.setBrowserName(browser);
					capability.setPlatform(Platform.fromString(platform));
					System.out.println("After Platform");
					driver = new RemoteWebDriver(new URL("http://" + nodeIP
							+ ":1212/wd/hub"), capability);
					driver.manage().timeouts()
							.implicitlyWait(25, TimeUnit.SECONDS);
				} else {

					driver = new InternetExplorerDriver();
					driver.manage().timeouts()
							.implicitlyWait(25, TimeUnit.SECONDS);
				}
				break;
			case "chrome":
				path = getPath();
				file = new File(path, "//Drivers//chromedriver.exe");
				System.setProperty("webdriver.chrome.driver",
						file.getAbsolutePath());

				if (remoteExecution.equals("Y")) {
					capability = DesiredCapabilities.chrome();
					capability.setBrowserName(browser);
					capability.setPlatform(Platform.fromString(platform));
					System.out.println("After Platform");
					driver = new RemoteWebDriver(new URL("http://" + nodeIP
							+ ":1212/wd/hub"), capability);
					driver.manage().timeouts()
					.implicitlyWait(25, TimeUnit.SECONDS);

				} else {
					
					driver = new ChromeDriver();
					driver.manage().timeouts()
					.implicitlyWait(25, TimeUnit.SECONDS);
				}
				break;
			default:
				file = new File("C:\\Nanda\\GeckoDriver\\geckodriver.exe");
				System.setProperty("webdriver.gecko.driver",
						file.getAbsolutePath());
				if (remoteExecution.equals("Y")) {
					capability.setBrowserName(browser);
					capability.setPlatform(Platform.fromString(platform));
					System.out.println("After Platform");
					driver = new RemoteWebDriver(new URL("http://" + nodeIP
							+ ":1212/wd/hub"), capability);
					driver.manage().timeouts()
					.implicitlyWait(25, TimeUnit.SECONDS);

					
				} else {
					FirefoxProfile firefoxProfile = new FirefoxProfile();
					driver = new FirefoxDriver(firefoxProfile);
					driver.manage().timeouts()
					.implicitlyWait(25, TimeUnit.SECONDS);
					
					break;
				}

			}
			// Maximize window
			driver.manage().window().maximize();

			String urlValue = getUrlValue();
			driver.navigate().to(urlValue);
		} catch (Exception e) {
			reader.updateStatusInExcel("CSWD_Test_Cases", "Fail", currentTCName);
			extent_Reportlogger("Test Step Execution failed", "LaunchBrowser",
					"Fail");
			excelWrite.writeInCell("LaunchBrowser Test Step Execution Failed",
					"Fail");
			ErrorLogger.logError(e.getClass().getName(), "LaunchBrowser",
					e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	public HashMap<String, ExtentTest> getDataInMapForCurrentTC(
			String currentActiveThread, ExtentTest logger) {
		loggerMap.put(currentActiveThread, logger);

		System.out.println("Logger MAp: " + loggerMap);
		return loggerMap;

	}

	public String getPath() {
		String path = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
		return path;
	}

	// Final logger function

	public void extent_Reportlogger(String result, String methodName,
			String tcStatus) throws Exception {

		ss = new ScreenShot(driver);
		System.out.println("Description:"
				+ loggerMap.get(Thread.currentThread().getName()).getTest()
						.getName());
		int index = loggerMap.get(Thread.currentThread().getName()).getTest()
				.getName().indexOf(":");
		currentTClassName = loggerMap.get(Thread.currentThread().getName())
				.getTest().getName().substring(0, index);
		// System.out.println("Description:"+loggerMap.get(Thread.currentThread().getName()).getDescription()+" Class name for screenshot:"+currentTClassName);
		String status = reader.getDataScreenFlag("CSWD_Test_Cases", Thread
				.currentThread().getName());
		// String status=reader.getDataScreenFlag("CSWD_Test_Cases",
		// currentTCName);
		System.out.println(Thread.currentThread().getName() + "&&&&^^^^^^^"
				+ status);
		if (tcStatus.equalsIgnoreCase("PASS")) {
			if (status.equalsIgnoreCase("Y")) {

				String reportLocation = ss.setDestinationForScreenshots(
						currentTClassName, Thread.currentThread().getName(),
						methodName);

				loggerMap.get(Thread.currentThread().getName()).log(
						LogStatus.PASS,
						methodName
								+ " "
								+ result
								+ loggerMap.get(
										Thread.currentThread().getName())
										.addScreenCapture(reportLocation));
			} else {

				loggerMap.get(Thread.currentThread().getName()).log(
						LogStatus.PASS, methodName + " " + result);
			}

		} else {
			String reportLocation = ss.setDestinationForScreenshots(
					currentTClassName, Thread.currentThread().getName(),
					methodName);
			loggerMap.get(Thread.currentThread().getName()).log(
					LogStatus.FAIL,
					methodName
							+ " "
							+ result
							+ loggerMap.get(Thread.currentThread().getName())
									.addScreenCapture(reportLocation));

		}

	}

	public WebDriver getWebDriver() {
		return driver;
	}

}
