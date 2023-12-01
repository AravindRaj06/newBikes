package com.newbikes.pageObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.newbikes.pageObject.objects.Cars;
import com.newbikes.utils.ExcelOutput;
import com.newbikes.utils.Highlighter;
import com.newbikes.utils.MyXLSReader;

public class UsedCars {
	private WebDriver driver;
	private String browserName;
	private String filePath = null;
	private String rootFilePath = "src//test//resources//Output//UsedCarsOutput//Screenshots//";

	public UsedCars(WebDriver driver, String browserName) {
		this.driver = driver;
		this.browserName = browserName;
		this.rootFilePath += browserName + "\\";

		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[contains(text(),'Used Cars')]")
	WebElement usedCarsMenu;

	@FindBy(xpath = "//span[@onclick=\"goToUrl('/used-car/Chennai')\"]")
	WebElement chennaiCity;

	@FindBy(xpath = "//input[@name='bycarid']")
	List<WebElement> carsInputBox;

	@FindBy(xpath = "//span[@class='ucCounth']/parent::*")
	WebElement totalCars;

	@FindBy(xpath = "//div[@class='pl-30 zw-sr-paddingLeft']/child::a")
	List<WebElement> carNames;

	@FindBy(xpath = "//div[@class='pt-10']")
	List<WebElement> carPrices;

	@FindBy(xpath = "//ul[@class='zw-sr-specification mt-30']/li[1]")
	List<WebElement> carFuelType;

	@FindBy(xpath = "//ul[@class='zw-sr-specification mt-30']/li[2]")
	List<WebElement> carKms;

	@FindBy(xpath = "//ul[@class='zw-sr-specification mt-30']/li[3]")
	List<WebElement> carModelNumber;

	// ********************************************************************
	// SAME METHOD WITH DIFFERENT LOGIC
	@FindBy(xpath = ""
			+ "")
	List<WebElement> carsModelALL;

	public boolean usedCarsMenu() {
		try {
			Actions actions = new Actions(driver);

			filePath = rootFilePath + "1. UsedCarsMenu.png";
			Highlighter.highlightElement(driver, usedCarsMenu, filePath);

			actions.moveToElement(usedCarsMenu).perform();
			
            System.out.println("Mouse hovered on 'Used Cars' field");
            
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean selectChennaiCity() {
		try {
			System.out.println("List of locations are displayed");
			
			filePath = rootFilePath + "2. UsedCarsInChennai.png";
			Highlighter.highlightElement(driver, chennaiCity, filePath);

			chennaiCity.click();
			
			System.out.println("'Chennai' option is clicked");
			
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean clickAllPopularCarsInput(){
		try {
			System.out.println("Used cars in chennai are displayed");
			
			JavascriptExecutor js = (JavascriptExecutor) driver;

			for (WebElement car : carsInputBox) {
				js.executeScript("arguments[0].click();", car);
			}

			System.out.println("Every Popular cars are selected"); 
			
			Thread.sleep(2000);

			filePath = rootFilePath + "3. TotalPopularCar.png";
			Highlighter.highlightElement(driver, totalCars, filePath);
			
			long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

			System.out.println("Scrolling ... ");
			while (true) {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
				Thread.sleep(2000);

				long newHeight = (long) js.executeScript("return document.body.scrollHeight");
				if (newHeight == lastHeight) {
					break;
				}
				lastHeight = newHeight;
			}
			
			System.out.println("Scrolled to the end of the page\n");
			System.out.println("All the Popular Car Model check boxes are clicked");
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// method to update excel sheeet with data on the website
	public boolean showOutputUpdateExcel() throws IOException {
		try {
			String sheetName = "UsedCars_" + browserName;

			System.out.println("\nAll popular Used Cars are as follows:");

			MyXLSReader excelReader = carsXlsReader(sheetName);

			System.out.println("\nTotal number of cars: " + carNames.size());

			printCarDetails(excelReader, sheetName);

			System.out.println("\nData updated to Excel");
			System.out.println("\nAll the Popular Cars are displayed on the console\n");

			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public MyXLSReader carsXlsReader(String sheetName) throws IOException {
		MyXLSReader excelReader = new MyXLSReader(
				"src\\test\\resources\\Output\\UsedCarsOutput\\usedCarsTestOutput.xlsx");

		if (excelReader.isSheetExist(sheetName)) {
			excelReader.removeSheet(sheetName);
		}

		excelReader.addSheet(sheetName);
		excelReader.addColumn(sheetName, "Name");
		excelReader.addColumn(sheetName, "Price");
		excelReader.addColumn(sheetName, "Fuel Type");
		excelReader.addColumn(sheetName, "Kms");
		excelReader.addColumn(sheetName, "Model");

		return excelReader;
	}

	public void printCarDetails(MyXLSReader excelReader, String sheetName) {
		String name, price, fuelType, kms, model;
		int sheetInd = 1;
		for (int car = 0; car < carNames.size(); car++) {
			name = carNames.get(car).getText().trim();
			price = carPrices.get(car).getText().trim();
			fuelType = carFuelType.get(car).getText().trim();
			kms = carKms.get(car).getText().trim();
			model = carModelNumber.get(car).getText().trim();

			System.out.println("Car Name : " + name + " | " + "Price : " + price + " | " + "Fuel Type : " + fuelType
					+ " | " + "Kms : " + kms + " | " + "Model : " + model);

			excelReader.setCellData(sheetName, "Name", sheetInd, name);
			excelReader.setCellData(sheetName, "Price", sheetInd, price);
			excelReader.setCellData(sheetName, "Fuel Type", sheetInd, fuelType);
			excelReader.setCellData(sheetName, "Kms", sheetInd, kms);
			excelReader.setCellData(sheetName, "Model", sheetInd, model);

			sheetInd++;
		}
	}

	// method to create excel sheeet and upload (FAST METHOD)
	public boolean showOutputCreateExcel() throws IOException {
		try {
			String sheetName = "UsedCars";
			
			String name, price, fuelType, kms, model;
			ArrayList<Cars> carsArr = new ArrayList<Cars>();

			System.out.println("\nTotal number of cars: " + carNames.size());
			
			System.out.println("\nAll popular Used Cars are as follows:");
			
			for (int i = 0; i < carNames.size(); i++) {
				name = carNames.get(i).getText().trim();
				price = carPrices.get(i).getText().trim();
				fuelType = carFuelType.get(i).getText().trim();
				kms = carKms.get(i).getText().trim();
				model = carModelNumber.get(i).getText().trim();

				System.out.println("Car Name : " + name + " | " + "Price : " + price + " | " + "Fuel Type : " + fuelType
						+ " | " + "Kms : " + kms + " | " + "Model : " + model);

				Cars cars = new Cars(name, price, fuelType, kms, model);
				carsArr.add(cars);
			}
			
			ExcelOutput.usedCarsOutput(sheetName, carsArr, browserName);
			
			System.out.println("\nData updated to Excel");
			System.out.println("All the Popular Cars are displayed on the console\n");
		
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// ***********************************************************************************
	// SAME METHOD WITH DIFFERENT LOGIC (UPDATE EXCEL SHEET)

	public boolean showOutputUpdateExcel2() throws IOException {
		try {
			String sheetName = "UsedCars_" + browserName;

			System.out.println("All popular Used Cars are as follows:");

			MyXLSReader excelReader = carsXlsReader(sheetName);

			System.out.println("Total number of cars: " + carsModelALL.size());

			printCarDetails2(excelReader, sheetName);

			System.out.println("Data updated to Excel");

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void printCarDetails2(MyXLSReader excelReader, String sheetName) {
		String name, price, fuelType, kms, model;
		
		int sheetInd = 1;
		for (WebElement car : carsModelALL) {

			name = car.findElement(By.xpath(".//a")).getText();
			price = car.findElement(By.xpath(".//div[@class='pt-10']")).getText();
			fuelType = car.findElement(By.xpath(".//li[1]")).getText();
			kms = car.findElement(By.xpath(".//li[2]")).getText();
			model = car.findElement(By.xpath(".//li[3]")).getText();

			System.out.println("Car Name : " + name + " | " + "Price : " + price + " | " + "Fuel Type : " + fuelType
					+ " | " + "Kms : " + kms + " | " + "Model : " + model);

			excelReader.setCellData(sheetName, "Name", sheetInd, name);
			excelReader.setCellData(sheetName, "Price", sheetInd, price);
			excelReader.setCellData(sheetName, "Fuel Type", sheetInd, fuelType);
			excelReader.setCellData(sheetName, "Kms", sheetInd, kms);
			excelReader.setCellData(sheetName, "Model", sheetInd, model);

			sheetInd++;
		}
	}
	
	//******************************************************************************************
}
