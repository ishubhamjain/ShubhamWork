package winiumPOC;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;

import com.dropbox.core.v2.teamlog.TimeUnit;
import com.gargoylesoftware.css.parser.condition.PseudoClassCondition;
//note: https://stackoverflow.com/questions/52246372/which-tool-is-compatible-with-windows-10-for-extract-window-element-for-winium/52246647#52246647
public class winium {
	static WiniumDriver driver = null;
	public static void main(String[] args) throws InterruptedException, IOException {

		
	try {
		Runtime.getRuntime().exec("taskkill /F /IM WiniumDriver.exe");
		
		//TimeUnit.SECONDS.wait(5000);
		/*Thread.sleep(5000);
		File file = new File("E:\\workspace\\ShubhamWork\\src\\main\\resources\\DriverBinaries\\WiniumDriver.exe");
		WiniumDriverService service = new WiniumDriverService.Builder().usingDriverExecutable(file).usingPort(9999).withVerbose(true).withSilent(false).buildDesktopService();
		service.start();*/
		
		/*File file = new File("E:\\workspace\\ShubhamWork\\src\\main\\resources\\DriverBinaries\\WiniumDriver.exe");
		WiniumDriverService service = new WiniumDriverService.Builder().usingDriverExecutable(file).withVerbose(true).withSilent(false).buildDesktopService();
		System.setProperty("webdriver.winium.driver.desktop","E:\\workspace\\ShubhamWork\\src\\main\\resources\\DriverBinaries\\WiniumDriver.exe");
		DesktopOptions option = new DesktopOptions();
		String appPath = "C:\\Windows\\System32\\calc.exe";
		option.setApplicationPath(appPath);
        option.setDebugConnectToRunningApp(false);
        option.setLaunchDelay(2);
		WiniumDriver driver = new WiniumDriver(service, option);*/
		/*public static DesktopOptions options = new DesktopOptions { ApplicationPath = @"C:\Windows\System32\runas.exe", Arguments = @"user:Admin\administrator C:\WINDOWS\system32\calc.exe" };*/
		
		
		/*ProcessBuilder pb = new ProcessBuilder("WiniumDriver.exe");
		pb.directory(new File("E:\\workspace\\ShubhamWork\\src\\main\\resources\\DriverBinaries\\"));
		Process p = pb.start();
		p.waitFor();*/
		//////////////////////////////////////
		
		Desktop desktop = Desktop.getDesktop();
	    desktop.open(new File("E:\\workspace\\ShubhamWork\\src\\main\\resources\\DriverBinaries\\WiniumDriver.exe"));
	   
		
		
		Thread.sleep(5000);
		String appPath = "C:\\Windows\\System32\\calc.exe";
	//	WiniumDriver driver = null;
		DesktopOptions option = new DesktopOptions();
		option.setApplicationPath(appPath);
        option.setDebugConnectToRunningApp(false);
        option.setLaunchDelay(2);
		 System.setProperty("webdriver.winium.driver.desktop","E:\\workspace\\ShubhamWork\\src\\main\\resources\\DriverBinaries\\WiniumDriver.exe");
		driver = new WiniumDriver(new URL("http://localhost:9999"), option);
		//driver = new WiniumDriver(service, option);
		System.out.println("hello");

		///////////////////////////////
		
        Thread.sleep(1000);
        driver.findElement(By.name("Nine")).click();
        driver.findElement(By.name("Plus")).click();
        driver.findElement(By.name("Six")).click();
        driver.findElement(By.name("Equals")).click();
        
      //  driver.close();
        driver.quit();
        Runtime.getRuntime().exec("taskkill /F /IM WiniumDriver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
      
        
	}
	catch(Exception ex) {

	      driver.quit();
		System.out.println(ex.getMessage());
		Runtime.getRuntime().exec("taskkill /F /IM WiniumDriver.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
		Thread.sleep(5000);
	}
	}
}
