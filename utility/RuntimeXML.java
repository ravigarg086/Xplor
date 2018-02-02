package com.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class RuntimeXML {
	
	public static String xlFile = null;

	public static File createBlankXMlFile(String packgecountryName,
			ArrayList<XLClassInfo> list) throws Exception {

		ArrayList<XLClassInfo> cmdata = new ArrayList<XLClassInfo>();
		cmdata = list;

		try {

			String filepath = ReadPropertyFile.loadFileProperties("XMLBASEPATH");

			xlFile = filepath + packgecountryName + ".xml";
			
			
			File file = new File(xlFile);
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					file));

			if (cmdata != null) {
				bufferedWriter.write(DynamicXML.creatXlHeader());
				
				String preclassname = cmdata.get(0).getClassName();
				String fullpath = fullPackageName(packgecountryName,
						preclassname) + preclassname;
				int count = 0;

				bufferedWriter.write(DynamicXML.startTestBlock(fullpath));
				
				bufferedWriter.write(DynamicXML.startMethodBlock());
				bufferedWriter.write(DynamicXML.methodData(cmdata.get(0)
						.getMethodName()));
				bufferedWriter.write(DynamicXML.addParameters(ExcelUtils.nodeIP.get(0), ExcelUtils.platform.get(0),ExcelUtils.browser.get(0), ExcelUtils.remoteExecution.get(0)));
				bufferedWriter.write(DynamicXML.endMethodDataBlock());
				
				for (count = 1; count < cmdata.size(); count++) {
					String classname = cmdata.get(count).getClassName();

					if (classname.equalsIgnoreCase(preclassname)) {
						
						bufferedWriter.write(DynamicXML.methodData(cmdata
								.get(count).getMethodName()));
						bufferedWriter.write(DynamicXML.addParameters(ExcelUtils.nodeIP.get(count), ExcelUtils.platform.get(count),ExcelUtils.browser.get(count), ExcelUtils.remoteExecution.get(count)));
						bufferedWriter.write(DynamicXML.endMethodDataBlock());
					} else {
						bufferedWriter.write(DynamicXML.endMethodBlock());
						bufferedWriter.write(DynamicXML.endClassBlock());
						bufferedWriter.write(DynamicXML.endTestBlock());

						preclassname = cmdata.get(count).getClassName();
						bufferedWriter.write(DynamicXML
								.startTestBlock(fullPackageName(
										packgecountryName, preclassname)
										+ preclassname));
						
						bufferedWriter.write(DynamicXML.startMethodBlock());
						bufferedWriter.write(DynamicXML.methodData(cmdata
								.get(count).getMethodName()));
						bufferedWriter.write(DynamicXML.addParameters(ExcelUtils.nodeIP.get(count), ExcelUtils.platform.get(count),ExcelUtils.browser.get(count), ExcelUtils.remoteExecution.get(count)));
						bufferedWriter.write(DynamicXML.endMethodDataBlock());
					}

				}
				bufferedWriter.write(DynamicXML.endMethodBlock());
				bufferedWriter.write(DynamicXML.endClassBlock());
				bufferedWriter.write(DynamicXML.endTestBlock());
				bufferedWriter.write(DynamicXML.creatXlFooter());
			}

			bufferedWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(), "createBlanckXmlFile",
					e.getMessage());
		}

		return null;
	}
	
	
	
	public static String fullPackageName(String packageCountryName,String className){
	//	String l1 = "test_class.";
		String commonPath = null;
		try {
			commonPath = "com.testScript.";
			/*if (className.matches("SS.*"))
				//commonPath = l1 + packageCountryName + "." + "login.";
				//commonPath = ReadPropertyFile.loadFileFolderPath("LOGINTEST");
			//	System.out.println("in if found ss");
				commonPath="com.Script.";
			else
				//commonPath = l1 + packageCountryName + "." + "guest.";
				//commonPath =  ReadPropertyFile.loadFileFolderPath("GUESTTEST");
				System.out.println("in else");
				commonPath="com.Script.";*/
		} catch (Exception e) {
			ErrorLogger.logError(e.getClass().getName(), "fullPackageName",
					e.getMessage());
		}

		return commonPath;
		
	}
	
	

}

