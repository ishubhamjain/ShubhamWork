package com.shubham.test.stepDefination;
/**
 * @author Shubham Jain
 * */
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.AutomationLogCustom;
import automationframework.AutomationTestCaseVerification;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import pageobjects.CueContactUsPage;
import pageobjects.CueHomePage;
//import mailReport.SendMailClass;
import pageobjects.Homepage;
import pageobjects.LoginPage;
import pageobjects.Page;

public class Test_Steps4 extends AutomationTestCaseVerification{
	
	Homepage home=new Homepage();
	LoginPage loginpage=new LoginPage(Page.driver);
	CueHomePage cueHome = new CueHomePage(Page.driver);
	CueContactUsPage cpage = new CueContactUsPage(Page.driver);
	
	public Test_Steps4() {
		invoke();
	}
	
	@Given("^Test1$")
    public void Test1() throws Exception
    {
		System.out.println("Test1");

    }
	
/*	@Given("^Test1$")
    public void Test1() throws Exception
    {
		System.out.println("Test1");
		WebDriverWait wait = new WebDriverWait(Page.driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(cueHome.By_link_Contact()));
		JavascriptExecutor executor = (JavascriptExecutor)Page.driver;
		executor.executeScript("arguments[0].click();", cueHome.link_Contact());
		System.out.println("Test1");
		cueHome.link_Contact().click();
	//	cueHome.link_Contact().sendKeys("shubham stack");
		cpage.textbox_name().sendKeys("Shubham Jain");
		cpage.textbox_email().sendKeys("shubham.jain@cuelogic.co.in");
		//cpage.textbox_message().sendKeys("8421485744");
		cpage.textbox_message().sendKeys("cuelogic");
    }*/
	
	@Given("^Test2$")
    public void Test2() throws Exception
    {
		System.out.println("Test2");

    }
	
	@Given("^Test3$")
    public void Test3() throws Exception
    {
		System.out.println("Test3");
    }
	
	@Given("^fail testase$")
	public void failTestcase() throws Exception {
		cpage.textbox_company().sendKeys("cuelogic");
	}
	
	@After
	public void afterMethod(Scenario scenario) {
	    if(scenario.isFailed()) {
	         scenario.embed(((TakesScreenshot)Page.driver).getScreenshotAs(OutputType.BYTES), "image/png");
	         AutomationLogCustom.error("Screenshot has been taken and embed with HTML report");
	    }
	}

}
