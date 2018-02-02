package com.utility;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot extends Test_BaseClass  {

	public ScreenShot(WebDriver driver) {
		this.driver=driver;
	}

public String setDestinationForScreenshots(String className, String tcName,String screenShotName) throws Exception{
	File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    String dest = OutputFolderStructure.screenShotfolderpath+"\\"+className+"\\"+tcName+"\\"+screenShotName+".png";
    System.out.println("Class Screenshot: "+ this.getClass().getName().substring(15));
    File file = new File(dest);
	//file.mkdir(); 
	FileUtils.copyFile(srcFile, file); 
return dest;
}

/*public String capture(String screenShotName) throws Exception{
    File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    
    String dest = OutputFolderStructure.screenShotfolderpath+"\\"+currentTClassName+"\\"+currentTCName+"\\"+screenShotName+".png";
    
    File destination = new File(dest); 
    FileUtils.copyFile(srcFile, destination); 
  return dest;
}*/

}
