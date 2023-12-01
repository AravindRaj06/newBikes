package com.newbikes.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CaptureScreenshot {

	public static void captureTestScreenshot(WebDriver driver, String filePath){
		//Takes screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		 try {
	            FileUtils.copyFile(screenshot, new File(filePath));
	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }
	}

	
}
