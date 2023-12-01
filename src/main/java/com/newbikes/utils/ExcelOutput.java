package com.newbikes.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.newbikes.pageObject.objects.Bikes;
import com.newbikes.pageObject.objects.Cars;

public class ExcelOutput {

	public static void upcomingBikesOutput(String sheetName, ArrayList<Bikes> bikesArr, String browserName)
			throws IOException {
		// Create a new workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a sheet in that workbook
		XSSFSheet sheet = workbook.createSheet(sheetName);

		Row row1 = sheet.createRow(0);
		Cell cell1 = row1.createCell(0);
		Cell cell2 = row1.createCell(1);
		Cell cell3 = row1.createCell(2);
		cell1.setCellValue("Name");
		cell2.setCellValue("Price");
		cell3.setCellValue("Launch Date");

		int ind;
		for (ind = 0; ind < bikesArr.size(); ind++) {
			Row row = sheet.createRow(ind + 1);
			Cell nameCell = row.createCell(0);
			Cell priceCell = row.createCell(1);
			Cell launchDateCell = row.createCell(2);
			nameCell.setCellValue(bikesArr.get(ind).getName());
			priceCell.setCellValue(bikesArr.get(ind).getPrice());
			launchDateCell.setCellValue(bikesArr.get(ind).getLaunchDate());
		}

		showStatus(sheet, ind+2);

		// write the workbook to an output stream
		try (FileOutputStream fileOut = new FileOutputStream(
				"src\\test\\resources\\Output\\UpcomingBikesOutput\\UpcomingBikesOutput_" + browserName + ".xlsx")) {
			workbook.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}

		workbook.close();
	}

	public static void usedCarsOutput(String sheetName, ArrayList<Cars> carsArr, String browserName)
			throws IOException {
		// Create a new workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a sheet in that workbook
		XSSFSheet sheet = workbook.createSheet("UsedCars");

		Row row1 = sheet.createRow(0);
		Cell cell1 = row1.createCell(0);
		Cell cell2 = row1.createCell(1);
		Cell cell3 = row1.createCell(2);
		Cell cell4 = row1.createCell(3);
		Cell cell5 = row1.createCell(4);
		cell1.setCellValue("Name");
		cell2.setCellValue("Price");
		cell3.setCellValue("Fuel Type");
		cell4.setCellValue("Kms");
		cell5.setCellValue("Model");

		int ind;
		for (ind = 0; ind < carsArr.size(); ind++) {
			Row row = sheet.createRow(ind + 1);
			Cell nameCell = row.createCell(0);
			Cell priceCell = row.createCell(1);
			Cell fuelTypeCell = row.createCell(2);
			Cell kmsCell = row.createCell(3);
			Cell modelCell = row.createCell(4);
			nameCell.setCellValue(carsArr.get(ind).getName());
			priceCell.setCellValue(carsArr.get(ind).getPrice());
			fuelTypeCell.setCellValue(carsArr.get(ind).getFuelType());
			kmsCell.setCellValue(carsArr.get(ind).getKms());
			modelCell.setCellValue(carsArr.get(ind).getModel());
		}

		showStatus(sheet, ind+2);

		// write the workbook to an output stream
		try (FileOutputStream fileOut = new FileOutputStream(
				"src\\test\\resources\\Output\\UsedCarsOutput\\UsedCarsOutput_" + browserName + ".xlsx")) {
			workbook.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}

		workbook.close();
	}

	// Print current date and time
	public static void showStatus(XSSFSheet sheet, int ind) {
		Row row = sheet.createRow(ind++);
		Row date = sheet.createRow(ind++);
		Row time = sheet.createRow(ind++);

		Cell report = row.createCell(0);
		report.setCellValue("Status");

		Cell dateRow = date.createCell(0);
		dateRow.setCellValue("Date");
		Cell dateCell = date.createCell(1);
		dateCell.setCellValue("" + LocalDate.now());

		Cell timeRow = time.createCell(0);
		timeRow.setCellValue("Time");
		Cell timeCell = time.createCell(1);
		timeCell.setCellValue("" + LocalTime.now());
	}
}
