package com.utility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.relevantcodes.extentreports.ExtentTest;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class WriteExcelFile {

	public static int tcHedaerRow = 1;
	public static int row = 1;
	public static int col =1;
	public static int count = 0;
	public static Integer SN = 0;
	public static WritableWorkbook workbook = null;
	public static WritableSheet executionlog = null;
	public static WritableSheet summarysheet = null;
	public static Workbook wbook = null;
	static String previous_TCName;
	public static HashMap<String,Integer> columnCountMap = new HashMap<>();
	public static HashMap<String,Integer> rowCountMap = new HashMap<>();
	
	public static void createXlSheet(String application, String path)
			throws IOException, WriteException, BiffException {

		try {
			String timestamp = DateTimeHandle.getTimestamp();
			String timString1[] = timestamp.split(" ");
			String dateString1[] = timString1[0].split("/");
			String url1 = dateString1[0] + dateString1[1] + dateString1[2];
			String dateString2[] = timString1[1].split(":");
			String url2 = "_" + dateString2[0] + "_" + dateString2[1] + "_"
					+ dateString2[2];
			String url3 = url1 + url2;

			workbook = Workbook.createWorkbook(new File(path + "\\"
					+ application + "TestReport_" + url3 + ".xls"));

			executionlog = workbook.createSheet("executionlog", 0);

			Label label1 = new Label(0, 0, "TC_Name/TC_Result", createFormatHeader());
			executionlog.addCell(label1);
			count++;
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(), "createXlSheet",
					e.getMessage());

		}

	}
	
	public void createTCResultTable(){

		try {
			Label labelHeader = null;
			Label labelResult = null;
			
			labelHeader = new Label(0, tcHedaerRow,Thread.currentThread().getName(),createFormatHeader());
			labelResult = new Label(0, tcHedaerRow+1,"Result",createFormatHeader());
			executionlog.addCell(labelHeader);
			executionlog.addCell(labelResult);
			rowCountMap.put(Thread.currentThread().getName(),tcHedaerRow);
			getDataInMapForColumns(Thread.currentThread().getName(),  0);
			
			tcHedaerRow=tcHedaerRow+3;
		} catch (Exception e) {
			ErrorLogger.logError(e.getClass().getName(), "createTCResultTable",
					e.getMessage());
}

	}

	public static WritableCellFormat createFormatHeader() throws WriteException {
		WritableCellFormat fCellstatus = null;

		try {
			WritableFont wfontStatus = new WritableFont(
					WritableFont.createFont("Arial"),
					WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.BROWN);
			fCellstatus = new WritableCellFormat(wfontStatus);

			fCellstatus.setWrap(true);
			fCellstatus.setAlignment(jxl.format.Alignment.CENTRE);
			fCellstatus.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			fCellstatus.setBorder(jxl.format.Border.ALL,
			jxl.format.BorderLineStyle.MEDIUM, jxl.format.Colour.BLACK);
		} catch (Exception e) {
			ErrorLogger.logError(e.getClass().getName(), "createFormatHeader",
					e.getMessage());
			e.printStackTrace();
		}
		return fCellstatus;

	}

	public void writeInCell(String log, String status) {

		try {
			Label label1 = null;
			Label label2 = null;
			Label label3 = null;
			col=columnCountMap.get(Thread.currentThread().getName())+1;
			
			int rowNo=rowCountMap.get(Thread.currentThread().getName());
			
				if (status.equalsIgnoreCase("pass")
						|| status.equalsIgnoreCase("fail")) {
					label1 = new Label(col, 0,"Step_"+(col),
							createFormatHeader());
					label2 = new Label(col, rowNo, log,
							createFormatCellStatus(status));
					label3 = new Label(col, rowNo+1, status,
							createFormatCellStatus(status));
				}

				getDataInMapForColumns(Thread.currentThread().getName(),  col);
				System.out.println("previous tcName: "+Thread.currentThread().getName());
				
			executionlog.addCell(label1);
			executionlog.addCell(label2);
			executionlog.addCell(label3);
			
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(), "writeInCell",
					e.getMessage());
		}

	}

	
	public void getDataInMapForColumns(String currentActiveThread,  int count) {
		if(columnCountMap.containsKey(currentActiveThread))
			columnCountMap.replace(currentActiveThread, count);
		else
			columnCountMap.put(currentActiveThread,count);
		
		System.out.println("columnCountMap: "+columnCountMap);
}
	
	public WritableCellFormat createFormatCellStatus(String result)
			throws WriteException {
		WritableCellFormat fCellstatus = null;
		try {
			Colour colour = (result.equalsIgnoreCase("pass")) ? Colour.GREEN
					: Colour.RED;
			WritableFont wfontStatus = new WritableFont(
					WritableFont.createFont("Arial"),
					WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, colour);
			fCellstatus = new WritableCellFormat(wfontStatus);

			fCellstatus.setWrap(true);
			fCellstatus.setAlignment(jxl.format.Alignment.CENTRE);
			fCellstatus.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			fCellstatus.setBorder(jxl.format.Border.ALL,
			jxl.format.BorderLineStyle.MEDIUM, jxl.format.Colour.BLACK);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(),
					"createFormatCellStatus", e.getMessage());
			throw e;
		}
		return fCellstatus;
	}

	public static void closeXLSheet() throws WriteException, IOException {

		try {
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(), "closeXLSheet",
					e.getMessage());
			throw e;
		}

	}
}
