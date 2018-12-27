package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Utilities.AutomationLogCustom;

public class CueContactUsPage extends Page  {
	private WebElement element = null;
	public CueContactUsPage(WebDriver driver) {
		super(driver);
		
	}
	
	public CueContactUsPage() {
		super(driver);
		//this.driver=driver;
	}
	
	public WebElement textbox_name() throws Exception {
		try {
			element = driver.findElement(By.name("firstname"));
			AutomationLogCustom.info("My name is found on Home Page");
		} catch (Exception e) {
			AutomationLogCustom.error("My name is not found on Home Page");
			throw (e);
		}
		return element;
	}
	
	public WebElement textbox_email() throws Exception {
		try {
			element = driver.findElement(By.name("email"));
			AutomationLogCustom.info("My name is found on Home Page");
		} catch (Exception e) {
			AutomationLogCustom.error("My name is not found on Home Page");
			throw (e);
		}
		return element;
	}
	
	public WebElement textbox_message() throws Exception {
		try {
			element = driver.findElement(By.name("message"));
			AutomationLogCustom.info("My name is found on Home Page");
		} catch (Exception e) {
			AutomationLogCustom.error("My name is not found on Home Page");
			throw (e);
		}
		return element;
	}
	
	public WebElement textbox_company() throws Exception {
		try {
			element = driver.findElement(By.name("dasdasd"));
			AutomationLogCustom.info("My name is found on Home Page");
		} catch (Exception e) {
			AutomationLogCustom.error("My name is not found on Home Page");
			throw (e);
		}
		return element;
	}
}
