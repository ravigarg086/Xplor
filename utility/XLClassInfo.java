package com.utility;


public class XLClassInfo {
	
	
	private String className;
	private String methodName;
	public XLClassInfo(String className, String methodName) {
		super();
		this.className = className;
		this.methodName = methodName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	

}
