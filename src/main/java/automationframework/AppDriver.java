package automationframework;
/**
 * @author Shubham Jain
 *
 */

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import automationframework.Configuration;

import pageobjects.Page;

public class AppDriver {
	private static String OSNAMES = System.getProperty("os.name").toLowerCase();
	private static final String FIREFOX = "MFF";
	private static final String CHROME = "GC";
	private static final String PhantomJS = "HL";
	private static final String RemoteChrome = "RGC";
	private static final String RemoteFirefox = "RMMF";
	private static String directory = Configuration.getConfigurationValueForProperty("directory");

	private static final ExcelLib xl = new ExcelLib();

	private static FirefoxProfile getFireFoxProfile() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.downLoad.folderList", 0);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "Applications/zip");
		return profile;
	}

	private static void setPropertyForBrowserFF(String OS) {

		if (OS.equalsIgnoreCase("mac")) {
			//System.setProperty("webdriver.firefox.bin", Configuration.macFirefoxName());
		}else if (OS.equalsIgnoreCase("windows")) {
			System.setProperty("webdriver.gecko.driver", directory + "geckodriver.exe");
		}
	}

	private static void setPropertyForBrowserGC(String OS) {
		if (OS.equalsIgnoreCase("windows")) {
			System.setProperty("webdriver.chrome.driver", directory + "chromedriver.exe");
		} else if (OS.equalsIgnoreCase("linux")) {
			System.setProperty("webdriver.chrome.driver", directory + "chromedriver");
		} else if (OS.equalsIgnoreCase("mac")) {
			System.setProperty("webdriver.chrome.driver", directory + "chromedrivermac");
		}
	}

	private static void setPropertyForPhantomJS(String OS) {

		if (OS.equalsIgnoreCase("windows")) {
			System.setProperty("phantomjs.binary.path", directory + "phantomjs.exe");
		}
		if (OS.equalsIgnoreCase("linux")) {
			System.setProperty("phantomjs.binary.path", directory + "phantomjslinux64");
		}
	}
	
	private static DesiredCapabilities getChromeCapabilities(String OS) {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		if (OS.equalsIgnoreCase("windows")) {
			capabilities.setCapability("chrome.binary", directory + "chromedriver.exe");
		} else if (OS.equalsIgnoreCase("linux")) {
			capabilities.setCapability("chrome.binary",	directory + "chromedriver");
		} else if (OS.equalsIgnoreCase("mac")) {
			capabilities.setCapability("chrome.binary",	directory + "chromedrivermac");
		}
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		return capabilities;
	}
	
	private static Capabilities getPhantomCapabilities(String OS) {
		DesiredCapabilities capabilities = null;
		ArrayList<String> cliArgsCap = new ArrayList<String>();
		capabilities = DesiredCapabilities.phantomjs();
		cliArgsCap.add("--web-security=false");
		cliArgsCap.add("--ssl-protocol=any");
		cliArgsCap.add("--ignore-ssl-errors=true");
		capabilities.setCapability("takesScreenshot", true);
		capabilities.setJavascriptEnabled(true);
		/*		capabilities.setCapability(
		    PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
		capabilities.setCapability(
		    PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
		        new String[] { "--logLevel=2" });*/
		return capabilities;
	}
	
	private static Capabilities getRemoteChromeCapabilities() {
        DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability("version", "");
		cap.setCapability("platform", "LINUX");
		return cap;
	}
	
	private static Capabilities getRemoteFirefoxCapabilities() {
        	DesiredCapabilities cap = DesiredCapabilities.firefox();
	        cap.setCapability("version", "");
	        cap.setCapability("platform", "LINUX");
		return cap;
	}

	public static WebDriver getDriver(String browserToUse) {
		String OS = OSNAMES.split(" ")[0];
		WebDriver driver = null;
		String ExcelTest = xl.getXLcellValue("Controller", 1, 0);
		//System.out.println("My Excel Test = "+ExcelTest);
		String browserType = browserToUse;
		if (browserType.equalsIgnoreCase(FIREFOX)) {
			setPropertyForBrowserFF(OS);
			driver = new FirefoxDriver();
		} else if (browserType.equalsIgnoreCase(CHROME)) {
			setPropertyForBrowserGC(OS);
			driver = new ChromeDriver();
			
		}else if (browserType.equalsIgnoreCase(PhantomJS)) {
			setPropertyForPhantomJS(OS);
			//driver = new PhantomJSDriver(getPhantomCapabilities(OS));
		}
		else if (browserType.equalsIgnoreCase(RemoteChrome)) {
			setPropertyForBrowserGC(OS);
			Capabilities cap =getRemoteChromeCapabilities();
	        try {
	        	driver= new RemoteWebDriver(new URL("http://"+Configuration.RemoteURLIPAndPort()+"/wd/hub"), cap);
			} catch (MalformedURLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		else if (browserType.equalsIgnoreCase(RemoteFirefox)) {
			setPropertyForBrowserGC(OS);
			Capabilities cap =getRemoteFirefoxCapabilities();
	        try {
				driver= new RemoteWebDriver(new URL("http://"+Configuration.RemoteURLIPAndPort()+"/wd/hub"), cap);
			} catch (MalformedURLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
	    else if (!browserType.equalsIgnoreCase(PhantomJS) | !browserType.equalsIgnoreCase(CHROME) | !browserType.equalsIgnoreCase(FIREFOX) | !browserType.equalsIgnoreCase(RemoteChrome) | !browserType.equalsIgnoreCase(RemoteFirefox)) {
	    	AutomationLog.error("Invalid browser name. Please check your browser Input");
	    	clearBrowserContext(driver);
	}

		driver.manage().window().maximize();

		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(Configuration.applicationUnderTestURL()); 
	//	AutomationLog.info("Current URL="+driver.getCurrentUrl());
		WaitFor.waitForPageToLoad(driver);
		return driver;
	}

	public static void clearBrowserContext(WebDriver driver) {
		try {
		if (Page.driver != null) {
			Page.driver.quit();
			killChromePhantomInstance(Page.driver);
		}
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			Page.driver.quit();
		}
	}

	public static void killChromePhantomInstance(WebDriver driver) {
		String OS = OSNAMES.split(" ")[0];
		try{
/*			if(Page.driver instanceof PhantomJSDriver & OS.equalsIgnoreCase("windows"))
			{
				Runtime.getRuntime().exec("taskkill /F /IM phantomjs.exe");
			}*/
			if(driver instanceof ChromeDriver & OS.equalsIgnoreCase("windows"))
			{
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			}
		}
		catch(Exception ex)
		{
			AutomationLog.error(ex.getMessage());
		}
	}
}
