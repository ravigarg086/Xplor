package com.utility;

import java.io.FileInputStream;
import java.util.ArrayList;

import jxl.Sheet;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelUtils {

	public static String testSheetPath = ReadPropertyFile
			.loadFileProperties("TESTSHEET");
	public static XSSFWorkbook wrk1 = null;
	public static XSSFSheet sheet1 = null;
	
	public static ArrayList<XLClassInfo> list = new ArrayList<XLClassInfo>();
	
	public static ArrayList<String> nodeIP=new ArrayList<String>();
	public static ArrayList<String> remoteExecution=new ArrayList<String>();
	public static ArrayList<String> platform = new ArrayList<String>();
	public static ArrayList<String>browser=new ArrayList<String>();
	public static String hubIP = "";
	
	
	public static String getCellData(String sTestCase, int col, int row)
			throws Exception {

		String cellData = null;
		try {

			wrk1 = new XSSFWorkbook(new FileInputStream(testSheetPath));
			sheet1 = wrk1.getSheet(sTestCase);
			XSSFRow rowdata = sheet1.getRow(row);
			if (rowdata.getCell(col) != null)
			{
				cellData = rowdata.getCell(col).getStringCellValue();
			}
				
			else
				cellData = " ";
		} catch (Exception e) {
		}
		return cellData;
	}
	
	public static ArrayList<XLClassInfo> getexecutionData(String sheetName)
			throws Exception {

		int rowN = 1;
		int col = 3;
		list.clear();

		try {

			wrk1 = new XSSFWorkbook(new FileInputStream(testSheetPath));
			sheet1 = wrk1.getSheet(sheetName);
			System.out.println("no of rows in excel: "+sheet1.getLastRowNum());
			for (rowN = 1; rowN <= sheet1.getLastRowNum(); rowN++) {
				if (sheet1.getRow(rowN).getCell(col) != null) {
					if (sheet1.getRow(rowN).getCell(col).getStringCellValue()
							.equalsIgnoreCase("y")) {
						
						remoteExecution.add(sheet1.getRow(rowN).getCell(4).getStringCellValue());
						browser.add(sheet1.getRow(rowN).getCell(7).getStringCellValue());
						platform.add(sheet1.getRow(rowN).getCell(6).getStringCellValue());
						nodeIP.add(sheet1.getRow(rowN).getCell(5).getStringCellValue());
						
						list.add(new XLClassInfo(sheet1.getRow(rowN)
								.getCell(1).getStringCellValue(), sheet1
								.getRow(rowN).getCell(2).getStringCellValue()));
						
					}
				}
			}

		} catch (Exception e) {

		}

		return list;
	}
}

