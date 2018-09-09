package Runner;

import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
/**
 * @author Shubham Jain
 * */
import org.junit.runner.RunWith;
import automationframework.AppDriver;
import automationframework.AutomationLog;
import automationframework.ScreenshotAndTestNgReporterListener;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.junit.Cucumber;
/*import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.ReportParser;
import net.masterthought.cucumber.Reportable;*/
import pageobjects.Page;
 
//@RunWith(Cucumber.class)
@ExtendedCucumberOptions(
		jsonReport = "target/cucumber.json",
		retryCount = 3,
		detailedReport = true,
		detailedAggregatedReport = true,
		overviewReport = true,
		toPDF = true,
		outputFolder = "target"
		)
@CucumberOptions(
		features = "classpath:Features"
		,glue={"stepDefinition"}
		,plugin = { "pretty", "html:target/cucumber-default-report", "json:target/cucumber.json","junit:target/cucumber.xml"}
//		,tags= {"@smoke"}  // Run tests in groups
//		,monochrome = false
//		,dryRun = true
		)
 
@RunWith(ExtendedCucumberRunner.class)
public class TestRunner {
    @BeforeSuite
    public static void setUp() {
    	AutomationLog.info("In Before Suite");
    }
    @AfterSuite
    public static void tearDown() {
        System.out.println("In After Suite");
        AppDriver.clearBrowserContext(Page.driver);
        AutomationLog.info("Quiting Webdriver Instances");
   }     
}


