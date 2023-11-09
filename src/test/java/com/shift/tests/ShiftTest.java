package com.shift.tests;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.shift.base.TestBase;
import com.shift.data.DataClass;
import com.shift.objects.CareerPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ShiftTest extends TestBase {
	WebDriver driver;

	@BeforeMethod	
	public void setUp() {
		try {
			if (browser.equalsIgnoreCase("CH")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions opt = new ChromeOptions();
				opt.addArguments("--remote-allow-origins=*");
				driver = new ChromeDriver(opt);
			}
			if (browser.equalsIgnoreCase("ED")) {
				WebDriverManager.edgedriver().setup();
				EdgeOptions opt = new EdgeOptions();
				opt.addArguments("--remote-allow-origins=*");
				driver = new EdgeDriver(opt);
			}
			if (browser.equalsIgnoreCase("CR")) {
				WebDriverManager.chromiumdriver().setup();
				ChromeOptions opt = new ChromeOptions();
				opt.setBinary(rootPath + DataClass.PATH_DRIVERS);
				opt.addArguments("--remote-allow-origins=*");
				driver = new ChromeDriver(opt);
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 0)
	public void validateUrlTest() {
		boolean result1 = false;
		String oldProdUrl = prop.getProperty("url.prod.old");
		try {

			driver.get(oldProdUrl);
			Thread.sleep(3000);
			String currentUrl = driver.getCurrentUrl();
			if (currentUrl.equalsIgnoreCase(newProdUrl)) {
				result1 = true;
			} else {
				result1 = false;
			}
			// testNG native report
			assertTrue(result1, "Navigation Failed to go to the correct Redirected URL");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 1)
	public void leadQaPositionTest() {
		boolean result1 = false;
		try {
			CareerPage careers = new CareerPage(driver);
			driver.get(newProdUrl);
			careers.listOfLinkCareers().get(0).click();
			Thread.sleep(2000);
			assertTrue(driver.getCurrentUrl().contains("https://www.rdbrck.com/careers"),
					"Could not find the expected URL in career webpage");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			try {
				careers.labelLeadQa();
				TestBase.scrollToElementAndTakeSnapshot(driver, careers.labelLeadQa());
				result1 = true;
			} catch (Exception nse) {
				result1 = false;
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			// testNG Native report
			assertTrue(result1, "Lead QA Automation Developer was NOT Found in Careers page");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
