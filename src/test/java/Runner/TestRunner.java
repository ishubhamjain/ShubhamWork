package Runner;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Shubham Jain
 * */
import org.junit.runner.RunWith;

import MainRunner.AfterSuite;
import MainRunner.BeforeSuite;
import MainRunner.ExtendedCucumberOptions;
import MainRunner.ExtendedCucumberRunner;
import MainRunner.TestingConstants;
import Utilities.AutomationLog;
import Utilities.YmlReader;
import automationframework.AppDriver;
import cucumber.api.CucumberOptions;
import pageobjects.Page;
 
//@RunWith(Cucumber.class)
@ExtendedCucumberOptions(
		jsonReport = "target/cucumber.json",
		retryCount = 0,
		detailedReport = true,
		detailedAggregatedReport = true,
		overviewReport = true,
		toPDF = true,
		outputFolder = "target"
		)
@CucumberOptions(
		features = "classpath:Features"
		//,glue={"stepDefinition"}
		,glue=TestingConstants.GLUE
		,plugin = { "pretty", "html:target/cucumber-default-report", "json:target/cucumber.json","junit:target/cucumber.xml"}
//		,tags= {"@smoke"}  // Run tests in groups
//		,monochrome = false
//		,dryRun = true
		)
 
@RunWith(ExtendedCucumberRunner.class)
public class TestRunner {
    @BeforeSuite
    public static void setUp() throws FileNotFoundException, IOException {
    	AutomationLog.info("In Before Suite");
    	System.out.println(YmlReader.ReadYmlProperty("databasName"));
    	
    }
    @AfterSuite
    public static void tearDown() {
        System.out.println("In After Suite");
        AppDriver.clearBrowserContext(Page.driver);
        AutomationLog.info("Quiting Webdriver Instances");
   }     
}


