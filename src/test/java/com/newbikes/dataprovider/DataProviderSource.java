package com.newbikes.dataprovider;

import java.lang.reflect.Constructor;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.newbikes.utils.DataUtil;
import com.newbikes.utils.MyXLSReader;

public class DataProviderSource {
	
	public static MyXLSReader excelReader;
	
	@SuppressWarnings("rawtypes")
	@DataProvider(name = "TestData", parallel = true)
	public static Object[][] getTestTypeData(Constructor constructor, ITestContext context) throws Exception{
		
		String testName = context.getName();
		String constructorName = constructor.getName();
		
		excelReader = new MyXLSReader("src\\test\\resources\\Input.xlsx");
		
		if("GoogleSignInTest".equals(testName) || "com.newbikes.test.GoogleSignInTest".equals(constructorName)) {
			System.out.println("Using GoogleSignInTest Data");
			Object[][] data = DataUtil.getTestData(excelReader, "GoogleSignIn", "SignIn");
			return data;
		}
		else if("UpcomingBikesTest".equals(testName) || "com.newbikes.test.UpcomingBikesTest".equals(constructorName)) {
			System.out.println("Using UpcomingBikesTest Data");
			Object[][] data = DataUtil.getTestData(excelReader, "UpComingBikes", "SignIn");
			return data;
		}
		else {
			System.out.println("Using Default Data");
			Object[][] data = DataUtil.getTestData(excelReader, "UsedCars", "SignIn");
			return data;
		}
	}
}
