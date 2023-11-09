package com.shift.base;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.shift.data.DataClass;

public class TestBase {

	public static Properties prop;
	public static String rootPath;
	public static String browser;
	public static String newProdUrl;

	@BeforeSuite
	public void suiteInit() {
		// Initial setup to load config files/ data files like excel in the future
		try {
			rootPath = System.getProperty("user.dir");
			FileInputStream fis = new FileInputStream(rootPath + DataClass.PATH_CONFIG);
			prop = new Properties();
			prop.load(fis);
			newProdUrl = prop.getProperty("url.prod.new");
			browser = prop.getProperty("browser");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterSuite
	public void suiteTearDown() {
		// To flush out any open instances/ rogue applications in the OS after test
		// execution
		try {
			openFile(rootPath + DataClass.PATH_REPORTS);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Common methods used across tests if needed

	public static void scroll(WebDriver driver, int scrollByValue) {
		// To scroll webpage
		Integer.toString(scrollByValue);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + scrollByValue + ")");
	}

	public static void scrollToElementAndTakeSnapshot(WebDriver driver, WebElement element) {
		// takes 20 screenshots by scrolling down a webpage at regular intervals
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(2000);
			takeSnapShot(driver, genRandomAlphaNum(6));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void takeSnapShot(WebDriver driver, String ssName) {
		// Takes ascreenshot of the web app and stores it in Evidences folder
		try {
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileHandler.copy(file, new File(rootPath + "/Evidences/" + ssName + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String genRandomAlphaNum(int length) {
		// Generates random alphanumeric string
		String alphaNum = "0123456789abcdefghi";
		SecureRandom random = new SecureRandom();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(alphaNum.length());
			sb.append(alphaNum.charAt(index));
		}
		return sb.toString();
	}

	public static void openFile(String path) {
		try {
			File htmlFile = new File(path);
			Desktop.getDesktop().browse(htmlFile.toURI());
			System.out.println("Test report can be found at " + path);
		} catch (IOException e) {
			System.out.println("Test report can be found at " + path);
		}
	}

}
