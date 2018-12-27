package automationframework;
/**
 * @author Shubham Jain
 *
 */
import java.util.HashMap;

import Utilities.AutomationLogCustom;
import Utilities.Configuration;
import cucumber.api.java.Before;
import pageobjects.Homepage;
import pageobjects.LoginPage;
import pageobjects.Page;

public class AutomationTestCase 
{
	Homepage homePage = Homepage.homePage();
	LoginPage loginPage = new LoginPage(Page.driver);
    protected HashMap<String, HashMap<String, String>> testCaseData;
    private static boolean initialized = false;
    private static boolean loginInitialized = false;
	String initializationType = Configuration.getConfigurationValueForProperty("execution-type");
	String LoginType = Configuration.getConfigurationValueForProperty("login-each-session");

    private String executingTestCaseName = null;
    private String executingTestCaseFileName = null;

    public AutomationTestCase() 
    {
        this.executingTestCaseName = this.getClass().getSimpleName();
        this.executingTestCaseFileName = executingTestCaseName;
    }

    protected AutomationTestCase(String executingTestCaseName) 
    {
        this.executingTestCaseName = this.getClass().getSimpleName();
        this.executingTestCaseFileName = executingTestCaseName;
    }
    
    @Before
	public void invoke() {
    try {
    	if(initializationType.equalsIgnoreCase("single")) {
	     if (!initialized){
	            initialized = true;
	              setup();   
	            AutomationLogCustom.info("Setting up Single Instance Type invokation");
	        }
			else {
				invokeData();
				AutomationLogCustom.info("Instance is already invoked");
			}
	      }
    	else if(initializationType.equalsIgnoreCase("multiple")) {
    	 //   consetup();
 	          setup();    
    		  AutomationLogCustom.info("Setting up Multiple Instance Type invokation");
    	}
    	else {
    		AutomationLogCustom.error("Wrong Instance Type invokation parameter. Please check the configurations");
    	}
    }
    catch(Exception ex) {
    	AutomationLogCustom.error("Exception occured in setup");
    	AutomationLogCustom.error(ex.getMessage());
    	ex.printStackTrace();
    }
  } 
    
    public void setup() 
    {
        AutomationLogCustom.startTestCase(executingTestCaseName);
        String browserToUse = Configuration.getConfigurationValueForProperty("browser");
        new Page(AppDriver.getDriver(browserToUse));
    	Credentials ValidCredentials = getGlobalUserCredentials();
        try {
        	loginPage.doSuccessfulLogin(ValidCredentials.getEmail(), ValidCredentials.getPassword());
        	AutomationLogCustom.startTestCase("Login done");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
       // populate test case data from csv
        testCaseData = TestDataProvider.getTestData(executingTestCaseFileName);
    }
    
/*    public void consetup() 
    {
        AutomationLog.startTestCase(executingTestCaseName);
		new Page(AppDriver.getDriver(Configuration.getConfigurationValueForProperty("browser")));
       // populate test case data from csv
        testCaseData = TestDataProvider.getTestData(executingTestCaseFileName);
    }*/
    
    
    public void invokeData() 
    {
        AutomationLogCustom.startTestCase(executingTestCaseName);
        Credentials ValidCredentials = getGlobalUserCredentials();
    	if(LoginType.equalsIgnoreCase("yes")) {
            try {
            	loginPage.doSuccessfulLogin(ValidCredentials.getEmail(), ValidCredentials.getPassword());
            	AutomationLogCustom.startTestCase("Login done");
    		} catch (Exception e) {
    			System.out.println(e.getMessage());
    		}
    	}else if(LoginType.equalsIgnoreCase("no")){
    		 if (!loginInitialized){
    			 loginInitialized = true;
    	          try {
    	            	loginPage.doSuccessfulLogin(ValidCredentials.getEmail(), ValidCredentials.getPassword());
    	            	AutomationLogCustom.startTestCase("Login done");
    	    		} catch (Exception e) {
    	    			System.out.println(e.getMessage());
    	    		}
    		 }
    		 else {
    	    		AutomationLogCustom.startTestCase("Continues Login session");
    		 }
    	}else {
    		AutomationLogCustom.startTestCase("check configiration login-each-session parameter is wrong");
    	}
        testCaseData = TestDataProvider.getTestData(executingTestCaseFileName);
    }
    
    public static Credentials getGlobalUserCredentials()
    {
        String email = Configuration.getConfigurationValueForProperty("email");
        String password = Configuration.getConfigurationValueForProperty("password");
        Credentials credential =new Credentials(email, password);
        return credential;
    }
    
    public void cleanup()
    {

    //	Page.driver.quit();  // As per suggestion by shubham

        AutomationLogCustom.endTestCase(executingTestCaseName);
        
        if (testCaseData != null)
            testCaseData.clear();

        AppDriver.clearBrowserContext(Page.driver);
    }

    public void testcasePassed(String customMessage) 
    {
        AutomationLogCustom.info(executingTestCaseName + " " + customMessage);
    }

    public void testcaseFailed(String customMessage) 
    {
       // AutomationLog.info(executingTestCaseName + " " + customMessage);
    	AutomationLogCustom.info("Failed report to send = " + customMessage);
		//Quitting the driver and all pages
		AppDriver.clearBrowserContext(Page.driver);
    }    
}
