package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automationframework.AutomationLog;

public class CueHomePage extends Page  {
	private WebElement element = null;
	private static By by=null;
	public CueHomePage(WebDriver driver) {
		super(driver);
		
	}
	
	public CueHomePage() {
		super(driver);
		//this.driver=driver;
	}
	
	public By By_link_Contact() throws Exception {
		try {
			//by = By.xpath("(//a[contains(.,'Learn')])[1]");
			by = By.xpath("(//a[contains(@href,'contact-us')])[1]");
			//by = By.cssSelector("#navbar > ul > li:nth-child(7) > a");
			//by = By.name("q");
			AutomationLog.info("My contact Link is found on Home Page");
		} catch (Exception e) {
			AutomationLog.error("My contact Link is not found on Home Page");
			throw (e);
		}
		return by;
	}
	
	
	public WebElement link_Contact() throws Exception {
		try {
			element = driver.findElement(By_link_Contact());
			AutomationLog.info("My contact Link is found on Home Page");
		} catch (Exception e) {
			AutomationLog.error("My contact Link is not found on Home Page");
			throw (e);
		}
		return element;
	}
}
