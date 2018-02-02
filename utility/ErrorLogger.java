package com.utility;


import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class ErrorLogger {
	
	
	private static final Logger LOGGER = Logger.getLogger("errorLogger");
	private static SimpleLayout layout = new SimpleLayout();
	private static FileAppender appender;
	private static String filePath = ReadPropertyFile.loadFileProperties("ERRORLOGGER");
	
	
	
	static {
		try {
		
			
			File myFile = new File(filePath);
			
			if (!myFile.exists()) {
				
				myFile.createNewFile();
				
			}
			
			appender = new FileAppender(layout,filePath, true);
			
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public static void logError(String className, String methodName, String exception) {
		System.out.println(appender.getFile());
		LOGGER.addAppender(appender);
		LOGGER.setLevel((Level) Level.ERROR);
		LOGGER.error(new Date().toString());
		LOGGER.error("ClassName :" + className);
		LOGGER.error("MethodName :" + methodName);
		LOGGER.error("Exception :" + exception);
		LOGGER.error("-----------------------------------------------------------------------------------");
	}
	

}
