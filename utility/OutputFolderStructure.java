package com.utility;

import java.io.File;

public class OutputFolderStructure {

	public static String poutputfolderpath = null;
	public static String outputfolderpath = null;
	public static String screenShotfolderpath = null;
	public static String extentReportfolderpath = null;
	public static String timestamp;
	public static String path = ReadPropertyFile.loadFileProperties("OUTPUTFILEPATH");

	public static String getstringtimestpam() {

		String timestamp = DateTimeHandle.getTimestamp();
		String timString1[] = timestamp.split(" ");
		String dateString1[] = timString1[0].split("/");
		String url1 = dateString1[0] + dateString1[1] + dateString1[2];
		String dateString2[] = timString1[1].split(":");
		String url2 = "_" + dateString2[0] + "_" + dateString2[1] + "_"
				+ dateString2[2];
		String url3 = url1 + url2;
		return url3;
	}

	public static void createOutputFolderPath() {

		try {
			timestamp=getstringtimestpam();
			String tempPath = path + timestamp;
			System.out.println("tempPath==" + tempPath);
			File file = new File(tempPath);
			boolean flag = file.mkdir();
			if (flag) {

				poutputfolderpath = tempPath;
				System.out.println("poutputfolderpath==" + poutputfolderpath);

			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(),
					"createOutputFolderPath", e.getMessage());
		}
	}

	/*
	 * public static void createOutputFolderPath(String path) {
	 * 
	 * 
	 * try {
	 * 
	 * File file = new File(path); boolean flag = file.mkdir(); if (flag) {
	 * outputfolderpath = path; } } catch (Exception e) { e.printStackTrace();
	 * ErrorLogger.logError(e.getClass().getName(), "createOutputFolderPath",
	 * e.getMessage()); }
	 * 
	 * }
	 */

	public static void createScreenShotFolderPath() {

		try {
			String path1 = path + timestamp;
			String tempPath = path1 + "\\screenshot";
			File file = new File(tempPath);
			boolean flag = file.mkdir();
			if (flag) {
				screenShotfolderpath = tempPath;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(),
					"createScreenShotFolderPath", e.getMessage());
		}
	}

	public static void createExtentReportFolderPath() {

		try {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdir();
			}
			String tempPath = path + timestamp;
			String tempPath1 = tempPath + "\\extentReport";
			File file = new File(tempPath1);
			boolean flag = file.mkdir();
			if (flag) {
				extentReportfolderpath = tempPath1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(),
					"extentReportfolderpath", e.getMessage());
		}

	}
}
