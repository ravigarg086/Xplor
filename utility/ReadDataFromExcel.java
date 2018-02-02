package com.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import jxl.write.WriteException;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadDataFromExcel {
	
	public String path;
	public  FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private static XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private  XSSFRow row = null;
	private  XSSFCell cell = null;
	
	
	public ReadDataFromExcel(String path) {
		// System.setProperty("org.apache.commons.logging.Log",
		// "org.apache.commons.logging.impl.Jdk14Logger");
		this.path = path;
		// System.out.println(path+"***");
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			//fis.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}

	/*public ReadDataFromExcel() {
		// TODO Auto-generated constructor stub
	}*/

	public ReadDataFromExcel(){
		// TODO Auto-generated constructor stub
	}

	public String getCellData(String sheetName, int colNum, int rowNum) {
		// System.out.println("inside the cell data method");
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
					|| cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				
				String cellText = String.valueOf(cell.getNumericCellValue());
				
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR)))
							.substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/"
							+ cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
				}
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum
					+ " does not exist  in xls";
		}
	}
	
	public void setCellData(String sheetName, String Result, int RowNum,
			int ColNum) throws Exception {
		try {
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(RowNum);
			cell = row.getCell(ColNum, row.RETURN_BLANK_AS_NULL);
			
			if (cell == null) {
				cell = row.createCell(ColNum);
				cell.setCellValue(Result);
			} else {
				cell.setCellValue(Result);
			}
			fis.close();
			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			throw (e);
		}

	}
		//Niki(Write cell data)
	public boolean setStatusCellData(String sheetName, String data, String testName) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			int colNum = 4;
			int rowNum = 1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);

			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				if (sheet.getRow(i).getCell(2).getStringCellValue()
						.equalsIgnoreCase(testName)) {
					rowNum = i;
					break;
				}
			}
			row = sheet.getRow(rowNum);
			if (row == null)
				row = sheet.createRow(rowNum);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);
			fis.close();
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
		
	public boolean updateStatusInExcel(String sheetName, String status, String tcName) {
		try {
			for (int i = 2; i < getRowCount(sheetName); i++) {
				String tc=getCellData(sheetName,2, i);
				if (getCellData(sheetName, 2, i).equals(tcName)) {
					setCellData(sheetName, status,(i-1),8);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
		
		
	public  void readcloseXLSheet() throws WriteException, IOException {

		try {

			//wrk1.write(new FileOutputStream(testSheetPath));
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(), "closeXLSheet",
					e.getMessage());
			throw e;
		}

	}
	
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
		sheet = workbook.getSheetAt(index);
		int number=sheet.getLastRowNum()+1;
		return number;
		}
		
	}
	
	
	public int getColumnCount(String sheetName){
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);	
		if(row==null)
			return -1;
		return row.getLastCellNum();
		
	}
	 
	/*public String getMapData(String headerName, HashMap<String, HashMap<String, String>>tempMap) {
		Initialize_Browser ib=new Initialize_Browser();
			if (headerName != null) {
				System.out.println("Current Tc: "+ib.currentTCName);
				String result = tempMap.get(ib.currentTCName).get(headerName).toString();
				System.out.println("result: "+result);
				return result;
			} else
				return null;

		}*/
	
	/*public HashMap<String, String> getDataInMapForCurrentTC(String sheetName, String tcName) {
		for (int rwIndx = 0; rwIndx <= getRowCount(sheetName); rwIndx++) {
			String tcID = getCellData(sheetName, 1, rwIndx);
			if(tcID.equals(tcName)){
				for (int colIndx = 0; colIndx < getColumnCount(sheetName); colIndx++) {
					Initialize_Browser.map.put(getCellData(sheetName, colIndx, 1).trim(),
							getCellData(sheetName, colIndx, rwIndx).trim());
				}
				break;
			}
		
		}
		return Initialize_Browser.map;

		}*/
	/*public String getMapData(String headerName, HashMap<String, String>tempMap) {
			if (headerName != null) {
				String result = tempMap.get(headerName).toString();
				System.out.println("result: "+result);
				return result;
			} else
				return null;

		}*/
	
	
	public String getDataScreenFlag(String sheetName, String testName){
		
		int colNum = 9;
		String flag="";
		try{
		for (int i = 1; i < getRowCount(sheetName); i++) {
			//System.out.println(sheet.getRow(i).getCell(2).getStringCellValue()+"   "+testName);
			if (sheet.getRow(i).getCell(2).getStringCellValue()
					.equalsIgnoreCase(testName)) {
				flag=getCellData(sheetName, colNum, i+1);
				
				break;
			}
		}
		
		}catch (Exception e) {
			ErrorLogger.logError(e.getClass().getName(), "getDataScreenFlag",
					e.getMessage());
		}
		return flag;
	}
	
	
	public String getMapData(String headerName, HashMap<String, HashMap<String, String>>tempMap) {
    	//System.out.println(tempMap+"---"+headerName+"---"+Initialize_Browser.currentTCName);
		if (headerName != null) {
			String result = tempMap.get(Thread.currentThread().getName()).get(headerName).toString();
			//System.out.println("result: "+result);
			return result;
		} else
			return null;

	}
	
	 // method to retrieve data from excel
	public HashMap<String, HashMap<String, String>> getDataInMap(String sheetName,
				String tcName) {
			//HashMap<String, HashMap<String, String>> tempMap = new HashMap<>();

			for (int rwIndx = 0; rwIndx <= getRowCount(sheetName); rwIndx++) {
				String tcID = getCellData(sheetName, 1, rwIndx);
				HashMap<String, String> tempDataMap = new HashMap<>();
				if(tcID.equals(tcName)){
				for (int colIndx = 0; colIndx < getColumnCount(sheetName); colIndx++) {
					tempDataMap.put(getCellData(sheetName, colIndx, 1).trim(),
							getCellData(sheetName, colIndx, rwIndx).trim());
				}
				Initialize_Browser.map.put(tcName, tempDataMap);
				}
			}
			return Initialize_Browser.map;
		}
		
}
