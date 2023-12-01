package com.newbikes.test;

import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.newbikes.base.Base;
import com.newbikes.dataprovider.DataProviderSource;
import com.newbikes.pageObject.UsedCars;

public class UsedCarsTest extends Base {

	UsedCars cars;

	@Factory(dataProvider = "TestData", dataProviderClass = DataProviderSource.class)
	public UsedCarsTest(HashMap<String, String> hmap) {
		browserName = hmap.get("Browser");
	}

	@Test(priority = 1, groups = "usedCarsSmokeTest")
	public void testUsedCars() throws Exception {

		driver = openBrowser(browserName);
		cars = new UsedCars(driver, browserName);

		logg = report.createTest("Used Cars Test Verification - " + browserName);

		logg.info("The " + browserName.toUpperCase() + " Browser is Launched");
		logg.info("Navigating to " + baseURL);
	}

	@Test(priority = 2, dependsOnMethods = "testUsedCars", groups = "usedCarsSmokeTest")
	public void hoverOnUsedCars() {
		try {
			Assert.assertTrue(cars.usedCarsMenu());
			logg.pass("Mouse hovered on 'Used Cars' field");
		} catch (AssertionError e) {
			logg.fail("Mouse failed to hover on 'Used Cars' field");
			logg.skip("Test skipped");
			Assert.fail();
		}
	}

	@Test(priority = 3, dependsOnMethods = "hoverOnUsedCars", groups = "usedCarsSmokeTest")
	public void clickChennai() {
		logg.info("List of locations are displayed");

		try {
			Assert.assertTrue(cars.selectChennaiCity());
			logg.pass("Option 'Chennai' is selected ");
		} catch (AssertionError e) {
			logg.fail("Failed to select option 'Chennai'");
			logg.skip("Test skipped");
			Assert.fail();
		}
	}

	@Test(priority = 4, dependsOnMethods = "clickChennai")
	public void clickAllPopularCars() {
		logg.info("Used cars in chennai are displayed");

		try {
			Assert.assertTrue(cars.clickAllPopularCarsInput());
			logg.pass("All the Popular Car Model check boxes are clicked");
		} catch (AssertionError e) {
			logg.fail("Failed to click Popular Car Model check boxes");
			logg.skip("Test skipped");
			Assert.fail();
		}
	}

	@Test(priority = 5, dependsOnMethods = "clickAllPopularCars")
	public void updateCarModelsInExcel() throws IOException {
		try {
			Assert.assertTrue(cars.showOutputCreateExcel());
			logg.pass("All the Popular Cars are displayed on the console");
		} catch (AssertionError e) {
			logg.fail("Failed to display all Popular Cars");
			logg.skip("Test skipped");
			Assert.fail();
		}
		logg.info("All the chosen Popular Cars are updated in the Excel Sheet located at "
				+ "src\\\\test\\\\resources\\\\Output\\\\UsedCarsOutput\\\\usedCarsTestOutput.xlsx\"");
		logg.pass("Used Cars Test Verification PASSED");
	}
}
