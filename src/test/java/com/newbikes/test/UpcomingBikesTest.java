package com.newbikes.test;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.newbikes.base.Base;
import com.newbikes.dataprovider.DataProviderSource;
import com.newbikes.pageObject.UpcomingBikes;
import com.newbikes.utils.DataUtil;
import com.newbikes.utils.MyXLSReader;

public class UpcomingBikesTest extends Base {

	UpcomingBikes bike;
	String manufacturer;

	@Factory(dataProvider = "TestData", dataProviderClass = DataProviderSource.class)
	public UpcomingBikesTest(HashMap<String, String> hmap) {
		browserName = hmap.get("Browser");
		manufacturer = hmap.get("Manufacturer");
	}

	@Test(priority = 1, groups = "upcomingBikesSmokeTest")
	public void testUpcomingBike() throws Exception {

		driver = openBrowser(browserName);
		bike = new UpcomingBikes(driver, browserName);

		logg = report.createTest("Upcoming Bikes Test Verification - " + browserName);

		logg.info("The " + browserName.toUpperCase() + " Browser is Launched");
		logg.info("Navigating to " + baseURL);
	}

	@Test(priority = 2, dependsOnMethods = "testUpcomingBike", groups = "upcomingBikesSmokeTest")
	public void hoverOnNewBikesMenu() {
		try {
			Assert.assertTrue(bike.newBikesMenu());
			logg.pass("Mouse hovers on 'New Bikes' field");
		} catch (AssertionError e) {
			logg.fail("Mouse failed to Hover on 'New Bikes' field");
			logg.skip("Test skipped");
			Assert.fail();
		}
	}

	@Test(priority = 3, dependsOnMethods = "hoverOnNewBikesMenu", groups = "upcomingBikesSmokeTest")
	public void clickUpcomingBikes() {
		logg.info("List of 'New Bikes' options are displayed");

		try {
			Assert.assertTrue(bike.selectUpcomingBike());
			logg.pass("'Upcoming Bikes' option is selected");
		} catch (AssertionError e) {
			logg.fail("Failed to select the option 'Upcoming Bikes'");
			logg.skip("Test skipped");
			Assert.fail();
		}
	}

	@Test(priority = 4, dependsOnMethods = "clickUpcomingBikes")
	public void selectManufacturer() {
		logg.info("'Upcoming-bikes' page is opened");

		try {
			Assert.assertTrue(bike.selectManufacturer(manufacturer));
			logg.pass("Manufacturer '" + manufacturer + "' is selected");
		} catch (AssertionError e) {
			logg.fail("Failed to select Manufacturer");
			logg.skip("Test skipped");
			Assert.fail();
		}
	}

	@Test(priority = 5, dependsOnMethods = "selectManufacturer")
	public void clickViewMoreButton() {
		logg.info("All upcoming '" + manufacturer + "' bikes are displayed");

		try {
			Assert.assertTrue(bike.viewMoreBikes());
			logg.pass("'View More' button is clicked");
		} catch (AssertionError e) {
			logg.fail("Failed to click 'View more' button");
			logg.skip("Test skipped");
			Assert.fail();
		}
	}

	@Test(priority = 6, dependsOnMethods = "clickViewMoreButton")
	public void printNewBikeModels() {
		logg.info("More bike models are displayed");

		try {
			Assert.assertTrue(bike.showOutputCreateExcel());
			logg.pass("Bike model data is read successfully");
		} catch (AssertionError e) {
			logg.fail("Failed to read bike model data");
			logg.skip("Test skipped");
			Assert.fail();
		}

		logg.info("Every upcoming '" + manufacturer + "' model bikes whoes price are lower than 4 lakhs "
				+ "are updated in the excel sheet located at \"src\\\\test\\\\resources\\\\Output\\\\UpcomingBikesOutput\\\\upcomingBikesTestOutput.xlsx\"");

		logg.pass("Upcoming Bikes Test PASSED");
	}
}
