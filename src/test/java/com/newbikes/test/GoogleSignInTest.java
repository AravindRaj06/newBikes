package com.newbikes.test;

import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.newbikes.base.Base;
import com.newbikes.dataprovider.DataProviderSource;
import com.newbikes.pageObject.GoogleSignInPage;


public class GoogleSignInTest extends Base {

	GoogleSignInPage signInPage;
	String userName;

	@Factory(dataProvider = "TestData", dataProviderClass = DataProviderSource.class)
	public GoogleSignInTest(HashMap<String, String> hmap) {
		browserName = hmap.get("Browser");
		userName = hmap.get("Username");
	}

	@Test(priority = 1, groups = "googleSignInSmokeTest")
	public void testGoogleSignIn() throws IOException {
		driver = openBrowser(browserName);
		signInPage = new GoogleSignInPage(driver, browserName);

		logg = report.createTest("Google Sign-In Verification - " + browserName);

		logg.info("The " + browserName.toUpperCase() + " Browser is Launched");
		logg.info("Navigating to " + baseURL);
	}

	@Test(priority = 2, dependsOnMethods = "testGoogleSignIn", groups = "googleSignInSmokeTest")
	public void clickSignInButton() {
		try {
			Assert.assertTrue(signInPage.clickSignIn());
			logg.pass("The sign-in button is clicked");
		} catch (AssertionError e) {
			String failMessage = "Failed to click the sign-in button";
			logg.fail(failMessage);
			logg.skip("Test skipped");
			Assert.fail(failMessage);
		}
	}

	@Test(priority = 3, dependsOnMethods = "clickSignInButton")
	public void clickGoogleButton() {
		logg.info("Another window appears with options to login with Google or Facebook");
		try {
			Assert.assertTrue(signInPage.googleSignIn());
			logg.pass("'Continue with Google' button is clicked");
		} catch (AssertionError e) {
			logg.fail("Failed to click 'Continue with Google' button");
			logg.skip("Test skipped");
			Assert.fail();
		}
	}

	@Test(priority = 4, dependsOnMethods = "clickGoogleButton")
	public void enterUserName() {

		logg.info("SignIn page appears");
		signInPage.emailInput(userName);
		logg.info("EmailID is entered");
	}

	@Test(priority = 5, dependsOnMethods = "enterUserName")
	public void clickNextButton() {
		try {
			Assert.assertTrue(signInPage.emailNext());
			logg.pass("The 'Next' button is clicked");
		} catch (AssertionError e) {
			logg.fail("Failed to click the 'NEXT' button");
			logg.skip("Test skipped");
			Assert.fail();
		}
	}

	@Test(priority = 6, dependsOnMethods = "clickNextButton")
	public void getErrorMessage() {
		logg.info("Error message and 'Try again' button is displayed");

		try {
			Assert.assertTrue(signInPage.getErrorMessage());
			logg.pass("Error message is captured Successfully");
		} catch (AssertionError e) {
			logg.fail("Failed to capture the error message");
			logg.skip("Test skipped");
			Assert.fail();
		}
		logg.pass("Google sign In Test PASSED");
	}
}
