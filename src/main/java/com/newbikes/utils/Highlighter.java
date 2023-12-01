package com.newbikes.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Highlighter {

	public static void highlightElement(WebDriver driver, WebElement element, String filePath) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);

		if (filePath != null) {
			try {
				Thread.sleep(1000);
				CaptureScreenshot.captureTestScreenshot(driver, filePath);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);

	}
}
