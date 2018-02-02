package com.utility;

public class DynamicXML {

	static int count = 1;

	public static String creatXlHeader() {
		String head1="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		String head2="<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\" >";
		String head3="<suite name=\"Regression suite\" verbose=\"4\" parallel=\"tests\" thread-count=\""+ReadPropertyFile.loadProperty("threadCount")+"\" >";
		String head4="<listeners>\n<listener class-name=\"org.uncommons.reportng.HTMLReporter\"/>";
	      
		String head5="<listener class-name=\"org.uncommons.reportng.JUnitXMLReporter\"/>\n</listeners>";

		//String head6="<!ELEMENT parameters (nodeIP,platform,browser,remoteExecution) >";
		count = 1;
		
		
		return head1 + "\n" + head2 +"\n"+ head3 + "\n" + head4+"\n"+head5+"\n"; 
		

	}
	public static String creatReportNg() {

				String reportNG="org.uncommons.reportng.HTMLReporter";
				
				//String reportNG1="org.uncommons.reportng.JUnitXMLReporter">
	
		      String listener_HTML ="<class-name=\"" +reportNG+"\">";
		     
		  return listener_HTML;

	}
	
	//name="Parallel test suite" parallel="methods" thread-count="2"

	public static String startTestBlock(String classname) {

		String l1 = "\n<test name=\"CSWD_Test_Suite " + count + "\">";
		String l2 = " \n<classes>";
		String l3 = "\n<class name=\"" + classname + "\">";

		return l1 + l2 + l3;

	}

	public static String endTestBlock() {
		String l1 = "\n</classes>";
		String l2 = "\n</test>";
		count++;
		return l1 + l2;
	}
	public static String startParametersBlock() {

		String l1 = "\n<parameters>";
		return l1;

	}
	public static String addParameters(String nodeIP,String platform,String browser,String remoteExecute) {
		String param1 = "\n<parameter name=\"nodeIP\"  value=\""+ nodeIP +"\"/>";
		String param2 = "\n<parameter name=\"platform\"  value=\""+ platform +"\"/>";
		String param3 = "\n<parameter name=\"browser\"  value=\""+ browser +"\"/>";
		String param4 = "\n<parameter name=\"remoteExecution\"  value=\""+ remoteExecute +"\"/>";
		return param1+param2+param3+param4;
	}

	public static String endParametersBlock() {
		String l1 = "\n</parameters>";
		return l1;
	}
	
	
	public static String startMethodBlock() {

		return "\n<methods>";

	}

	public static String methodData(String methodname) {

		return "\n<include name=\"" + methodname + "\" >";
	}
	
	public static String endMethodDataBlock() {
		String l1 = "\n</include>";
		return l1;
	}
	public static String endMethodBlock() {
		String l1 = "\n</methods>";
		return l1;
	}
	public static String endClassBlock() {
		String l2 = "\n</class>";
		return l2;
	}

	public static String creatXlFooter() {

		return "\n</suite>";
	}

}
