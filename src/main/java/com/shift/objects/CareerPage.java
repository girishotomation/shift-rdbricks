package com.shift.objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CareerPage {
	WebDriver driver;

	public CareerPage(WebDriver driver) {
		this.driver = driver;
	}

	public List<WebElement> listOfLinkCareers() {

		return driver.findElements(By.xpath(".//a[@href='/careers']//span[contains(text(),'Careers')]"));
	}

	public WebElement labelLeadQa() {

		return driver.findElement(By.xpath("//h5[normalize-space()='Lead QA Automation Developer']"));
	}

}
