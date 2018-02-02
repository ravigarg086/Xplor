package com.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class ReadPropertyFile {

	public static String loadFileProperties(String key) {

		String testResult = null;
		String propertyValue = null;
		try {
			Properties properties = new Properties();
			FileInputStream fileInputStream = new FileInputStream(
					System.getProperty("user.dir")
							+ "\\src\\com\\properties\\configFile.properties");

			properties.load(fileInputStream);
			testResult = properties.getProperty(key);
			propertyValue = System.getProperty("user.dir") + testResult;
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(), "loadTestResult",
					e.getMessage());

		}

		return propertyValue;
	}

	public static String loadProperty(String key) {

		String testResult = null;
		try {
			Properties properties = new Properties();
			FileInputStream fileInputStream = new FileInputStream(
					System.getProperty("user.dir")
							+ "\\src\\com\\properties\\configFile.properties");

			properties.load(fileInputStream);
			testResult = properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(), "loadTestResult",
					e.getMessage());

		}

		return testResult;
	}
}
