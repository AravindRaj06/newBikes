package com.newbikes.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.newbikes.utils.DataUtil;
import com.newbikes.utils.MyXLSReader;

import io.github.bonigarcia.wdm.WebDriverManager;


//MultipleTest branch


public class Base {
	public WebDriver driver;
	public static String baseURL;
	public String browserName;
	
	public Properties prop;
	public static MyXLSReader excelReader;

	public ExtentTest logg;
	protected static ExtentSparkReporter ext = new ExtentSparkReporter("ExtentReportGeneration.html");;
	public static ExtentReports report = new ExtentReports();

	public WebDriver openBrowser(String browserName) throws IOException {
		this.browserName = browserName;
		
		prop = new Properties();
		File file = new File("src\\test\\resources\\data.properties");
		FileInputStream fis = new FileInputStream(file);
		prop.load(fis);

		baseURL = prop.getProperty("url");

		if (browserName.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (browserName.equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		
		System.out.println("\nOpening : '"+browserName+"' browser");

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(baseURL);

		System.out.println("URL : "+baseURL+"\n\n");
		
		return driver;
	}
	
	@BeforeMethod(groups="smokeTest")
	public void BeforeSuite() {
		report.attachReporter(ext);
	}

	@AfterMethod(groups="smokeTest")
	public void flush() {
		report.flush();
	}
	
	@AfterClass(groups="smokeTest")
	public void tearDown() {
		if(driver!=null) {
			System.out.println("Quiting : '"+ browserName +"' browser\n");
			driver.quit();
		}
	}
	

}
