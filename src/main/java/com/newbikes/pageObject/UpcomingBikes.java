package com.newbikes.pageObject;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.newbikes.pageObject.objects.Bikes;
import com.newbikes.utils.ExcelOutput;
import com.newbikes.utils.Highlighter;
import com.newbikes.utils.MyXLSReader;

public class UpcomingBikes {

	private WebDriver driver;
	private String browserName;
	private String filePath = null;
	private String rootFilePath = "src//test//resources//Output//UpcomingBikesOutput//Screenshots//";

	public UpcomingBikes(WebDriver driver, String browserName) {
		this.driver = driver;
		this.browserName = browserName;
		this.rootFilePath += browserName + "\\";

		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@data-track-label='bikes']")
	WebElement newBikesMenu;

	@FindBy(xpath = "//span[@data-track-label='bikes-upcoming']")
	WebElement upcomingBikes;

	@FindBy(id = "makeId")
	WebElement manufacturer;

	@FindBy(xpath = "//span[@class='zw-cmn-loadMore']")
	WebElement viewMore;

	@FindBy(xpath = "//ul[@id='modelList']/child::*//strong")
	List<WebElement> hondaBikeNames;

	@FindBy(xpath = "//ul[@id='modelList']/child::*/div/div[3]/div[1]")
	List<WebElement> hondaBikePrice;

	@FindBy(xpath = "//ul[@id='modelList']/child::*/div/div[3]/div[2]")
	List<WebElement> hondaBikeLaunchDate;

	public boolean newBikesMenu() {

		try {
			Actions action = new Actions(driver);

			filePath = rootFilePath + "1. NewBikesMenu.png";
			Highlighter.highlightElement(driver, newBikesMenu, filePath);

			action.moveToElement(newBikesMenu).perform();

			System.out.println("Mouse hovers on 'New Bikes' field");
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean selectUpcomingBike() {
		try {
			filePath = rootFilePath + "2. UpcomingBikeMenu.png";
			Highlighter.highlightElement(driver, upcomingBikes, filePath);

			upcomingBikes.click();
			
            System.out.println("'Upcoming Bikes' option is selected");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean selectManufacturer(String bikeManufacturer) {
		try {
			Select select = new Select(manufacturer);

			filePath = rootFilePath + "3. ManufacturerDropdown.png";
			Highlighter.highlightElement(driver, manufacturer, filePath);

			select.selectByVisibleText(bikeManufacturer);
			
            System.out.println("Manufacturer '"+bikeManufacturer+"' is selected");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean viewMoreBikes() {
		try {
			Actions action = new Actions(driver);
			
			//WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		    //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='zw-cmn-loadMore']")));
			
			Thread.sleep(5000);
			
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true)", viewMore);	

			filePath = rootFilePath + "4. ViewMore.png";
			Highlighter.highlightElement(driver, viewMore, filePath);
			
			action.moveToElement(viewMore).click(viewMore).perform();
			
			System.out.println("'View More' button is clicked");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showOutputUpdateExcel() {
		try {
			
			String sheetName = "UpcomingBike_" + browserName;

			System.out.println("\nUpcoming Honda Bikes Below 4 Lakhs are as follows:");

			System.out.println("Creates excel Reader and imports sheets");
			
			MyXLSReader excelReader = bikeXlsReader(sheetName);

			printBikes(excelReader,  sheetName);
			System.out.println("Bike Model data is read successfully");

			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public MyXLSReader bikeXlsReader(String sheetName) throws IOException {
		MyXLSReader excelReader = new MyXLSReader(
				"src\\test\\resources\\Output\\UpcomingBikesOutput\\upcomingBikesTestOutput.xlsx");

		if (excelReader.isSheetExist(sheetName)) {
			excelReader.removeSheet(sheetName);
		}

		excelReader.addSheet(sheetName);
		excelReader.addColumn(sheetName, "Name");
		excelReader.addColumn(sheetName, "Price");
		excelReader.addColumn(sheetName, "Launch Date");
		
		return excelReader;
	}
	
	public void printBikes(MyXLSReader excelReader, String sheetName) {
		int sheetInd = 1;
		String name, launchDate, price;
		for (int bike = 0; bike < hondaBikeNames.size(); bike++) {
			name = hondaBikeNames.get(bike).getText().trim();
			price = hondaBikePrice.get(bike).getText().trim();
			launchDate = hondaBikeLaunchDate.get(bike).getText().trim();			
			
			if (Double.compare(getPriceInDouble(price), 4d) < 0) {
				System.out.println(name + " | " + price + " | " + launchDate);

				excelReader.setCellData(sheetName, "Name", sheetInd, name);
				excelReader.setCellData(sheetName, "Price", sheetInd, price);
				excelReader.setCellData(sheetName, "Launch Date", sheetInd, getLaunchDateValue(launchDate));

				sheetInd++;
			}
			
		}
		System.out.println("\nData updated to Excel");
	}

	public boolean showOutputCreateExcel() {
		try {
			
			String sheetName = "UpcomingBikes";
			String name, launchDate, price;
			ArrayList<Bikes> bikesArr = new ArrayList<Bikes>();

			System.out.println("\nUpcoming Honda Bikes Below 4 Lakhs are as follows:");
			for (int i = 0; i < hondaBikeNames.size(); i++) {
				name = hondaBikeNames.get(i).getText();
				price = hondaBikePrice.get(i).getText();
				launchDate = hondaBikeLaunchDate.get(i).getText();
				
				if (Double.compare(getPriceInDouble(price), 4d) < 0) {
					
					System.out.println(name + " | " + price + " | " + launchDate);
					
					Bikes bike = new Bikes(name, price, getLaunchDateValue(launchDate));
					bikesArr.add(bike);
				}
			}		
			ExcelOutput.upcomingBikesOutput(sheetName, bikesArr, browserName);
			
			System.out.println("\nData updated to excel file");
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}
	
	
	// ***********************************************************
	// local methods
	private Double getPriceInDouble(String price) {
		String[] priceValue = price.split(" ");
		double priceD;
		try {
			if (priceValue[1].contains(".")) {
				priceD = Double.parseDouble(priceValue[1]);
			} else {
				priceD = Double.parseDouble(priceValue[1].split(",")[0]) / 100;
			}
		} catch (Exception e) {
			return 0.0;
		}
		return priceD;
	}

	private String getLaunchDateValue(String launchDate) {
		try {
			String templaunchDate = launchDate.split(":")[1].trim();
			return templaunchDate;
		} catch (Exception e) {
			return "Unrevealed";
		}

	}
}
