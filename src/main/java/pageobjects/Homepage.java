package pageobjects;
/**
 * @author Shubham Jain
 * */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import automationframework.AutomationLog;


public class Homepage extends Page 
{
	private WebElement element = null;
    public Homepage(WebDriver driver)
    {
        super(driver);
    }

    public Homepage() {
    	super(driver);
	}

	public String homepageUrl()
    {
        return applicationUrl() + "/home/";
    }

    public static Homepage homePage()
    {
        return PageFactory.initElements(driver, Homepage.class);
    }

    public By getHomepageGreetingsLocator()
    {
		return null;
        
    }
    
	public WebElement button_MyAccount() throws Exception {
		try {
			element = driver.findElement(By.xpath(".//*[@id='account']/a"));
			AutomationLog.info("My Account Link is found on Home Page");
		} catch (Exception e) {
			AutomationLog.error("My Account Link is not found on Home Page");
			throw (e);
		}
		return element;
	}
}
