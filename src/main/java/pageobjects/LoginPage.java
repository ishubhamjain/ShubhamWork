package pageobjects;
/**
 * @author Shubham Jain
 * */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automationframework.AutomationLog;
import automationframework.Configuration;
import automationframework.ExcelLib;
import automationframework.WaitFor;

import org.junit.Assert;

public class LoginPage extends Page {
	private WebElement element = null;
	Homepage home=new Homepage();
//	static ExcelLib xl = new ExcelLib();

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	protected boolean isSecured() {
		return true;
	}

	public Homepage doSuccessfulLogin(String username, String password) throws Exception {
		Homepage element = null;
		try {
			// Validate App URL here

			 //validateAppURL();

			doLoginWithCredentials(username, password);

			validateLogin();
			element = Homepage.homePage();

		} catch (Exception e) {
			AutomationLog.error("Login failed.");
			throw (e);
		}
		return element;
	}

	private void validateLogin() {
		// TODO Auto-generated method stub

		
	  String expcted = "Incorrect username or password.";
	  String actual ="";
  	
	  if(!driver.findElements(By.xpath("//div[@class='nNote nFailure']")).isEmpty()){
		  
		  element = driver.findElement(By.xpath("//div[@class='nNote nFailure']"));
		  actual = element.getText();
	
	  }
  	
 
  	
  	//System.out.println("Login text = " +actual);
  	
  	if(actual.equals(expcted)){
  		AutomationLog.error("Login details are invalid");
  		Assert.fail("Login details are invalid");
  	}
  	else {
  		AutomationLog.info("Login Test passed");	
  	}
}

/*	private void validateAppURL() {

 //   	String expcted = xl.getXLcellValue("MainTemplate", 8, 1);
    	String actual = driver.getTitle();
    	if(actual.equals(expcted)){
    		AutomationLog.info("URL Test passed");
    	}
    	else {
    		AutomationLog.error("App URL is invalid, Getting Title as ="+actual);
    		Assert.fail("App URL is invalid");
    	}
	}*/

	private void doLoginWithCredentials(String username, String password) throws Exception {
		try {
			home.button_MyAccount().click();
			WebElement userNameTextBox = txtbx_UserName();
			userNameTextBox.clear();
			userNameTextBox.sendKeys(Configuration.getConfigurationValueForProperty("email"));
			WebElement passwordTextBox = txtbx_Password();
			passwordTextBox.clear();
			passwordTextBox.sendKeys(Configuration.getConfigurationValueForProperty("password"));
			button_LoginToAccount().click();
			Thread.sleep(5000);
		} catch (Exception e) {
			AutomationLog.error("Login Failed");
			throw (e);
		}
	}

	public WebElement txtbx_UserName() throws Exception {
		try {

			element = driver.findElement(By.id("log"));
			AutomationLog.info("Username text box found on the Login Page");
		} catch (Exception e) {
			AutomationLog.error("UserName text box was not found on the Login Page");
			throw (e);
		}
		return element;
	}

	public WebElement txtbx_Password() throws Exception {
		try {
			element = driver.findElement(By.id("pwd"));
			AutomationLog.info("Password text box found on the Login Page");
		} catch (Exception e) {
			AutomationLog.error("Password text box was not found on the Login Page");
			throw (e);
		}
		return element;
	}

	public WebElement button_LoginToAccount() throws Exception {
		try {
			element = driver.findElement(By.id("login"));
		//	element = driver.findElement(By.id("loginnn"));
			AutomationLog.info("Login to Account button found on the Login Page");
		} catch (Exception e) {
			AutomationLog.error("Login to Account button was not found on the Login Page");
			throw (e);
		}
		return element;
	}
	
	public WebElement button_SingOutToAccount() throws Exception {
		try {
			element = driver.findElement(By.xpath(".//*[@id='account_logout']/a"));
			AutomationLog.info("SignOut Element Found");
		} catch (Exception e) {
			AutomationLog.error("SignOut Element not Found");
			throw (e);
		}
		return element;
	}

	public String getPageUrl() {
		return (applicationUrl() + "/login");
	}
}
