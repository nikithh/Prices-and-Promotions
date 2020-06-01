package com.sapient.pps.screenshot;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CaptureScreenShot {
	private WebDriver webDriver;

	public CaptureScreenShot(WebDriver driver) {
		this.webDriver = driver;
	}

	public void takeScreenshot() {
		String path = "./target/cucumber-reports/Screenshots/";
		Calendar calendar = GregorianCalendar.getInstance();
		String fileName = calendar.get(Calendar.MILLISECOND) + ".png";
		TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
		File file = takesScreenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file, new File(path + fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String takeScreenshot(WebDriver driver) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File("./target/Screens/" + System.currentTimeMillis() + ".png");
		String errflpath = Dest.getAbsolutePath();
		try {
			FileUtils.copyFile(scrFile, Dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errflpath;

	}

}