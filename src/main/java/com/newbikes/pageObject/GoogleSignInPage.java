package com.newbikes.pageObject;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.newbikes.utils.Highlighter;

public class GoogleSignInPage {
	private WebDriver driver;
	private String browserName;
	private String filePath = null;
	private String rootFilePath = "src//test//resources//Output//GoogleSignInOutput//Screenshots//";

	public GoogleSignInPage(WebDriver driver, String browserName) {
		this.driver = driver;
		this.browserName = browserName;
		this.rootFilePath += browserName + "\\";

		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "forum_login_wrap_lg")
	WebElement signInButton;

	@FindBy(xpath = "//span[contains(text(),'Continue with Google')]")
	WebElement google;

	@FindBy(xpath = "//input[@id='identifierId']")
	WebElement email;

	@FindBy(xpath = "//span[contains(text(),'Next')]")
	WebElement emailNextButton;

	@FindBy(xpath = "//*[@id='view_container']/div/div")
	WebElement errorMessage;

	public boolean clickSignIn() {
		try {
			filePath = rootFilePath + "1. SignInButton.png";
			Highlighter.highlightElement(driver, signInButton, filePath);

			signInButton.click();
			
			System.out.println("The sign-in button is clicked");

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean googleSignIn() {
		try {
			Actions action = new Actions(driver);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			filePath = rootFilePath + "2. GoogleSignInButton.png";
			Highlighter.highlightElement(driver, google, filePath);

			// google.click();
			action.moveToElement(google).click(google).perform();
			
			System.out.println("'Continue with Google' button is clicked");

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean emailInput(String emailId) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.numberOfWindowsToBe(2));

			Set<String> windows = driver.getWindowHandles();
			Iterator<String> iterator = windows.iterator();
			iterator.next();
			String signInWindow = iterator.next();

			driver.switchTo().window(signInWindow);

			filePath = rootFilePath + "3. EmailIdField.png";
			Highlighter.highlightElement(driver, email, filePath);
			
			System.out.println("\nEmail ID: "+emailId+"\n");
			
			email.sendKeys(emailId);
			
			System.out.println("EmailID is entered");

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean emailNext() {
		try {
			Actions action = new Actions(driver);

			filePath = rootFilePath + "4. NextButton.png";
			Highlighter.highlightElement(driver, emailNextButton, filePath);

			action.moveToElement(emailNextButton).click(emailNextButton).perform();
			
			System.out.println("The 'Next' button is clicked");

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean getErrorMessage() {
		try {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String message = errorMessage.getText();

			filePath = rootFilePath + "5. ErrorMessage.png";
			Highlighter.highlightElement(driver, errorMessage, filePath);
			
			System.out.println("\nError Message:\n" + message+"\n");

			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
