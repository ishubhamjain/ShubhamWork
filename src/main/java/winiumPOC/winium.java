package winiumPOC;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;

import com.dropbox.core.v2.teamlog.TimeUnit;
import com.gargoylesoftware.css.parser.condition.PseudoClassCondition;

import ch.ethz.ssh2.StreamGobbler;
//note: https://stackoverflow.com/questions/52246372/which-tool-is-compatible-with-windows-10-for-extract-window-element-for-winium/52246647#52246647
public class winium {
	static WiniumDriver driver = null;
	static Process p = null ;
	public static void main(String[] args) throws InterruptedException, IOException {

		
	try {
		//Runtime.getRuntime().exec("taskkill /F /IM Winium.Desktop.Driver.exe");
	
        try {
            String[] command = {"cmd.exe", "/C", "Start", "E:\\workspace\\ShubhamWork\\src\\main\\resources\\DriverBinaries\\Winium.Desktop.Driver.exe"};
            p =  Runtime.getRuntime().exec(command);           
        } catch (IOException ex) {
        }
        
/*        System.out.println("process name"+ getProcessIDs("WiniumDriver.exe"));
       String processID = getProcessIDs("WiniumDriver.exe").get(0);*/
		//p.waitFor();
		//////////////////////////////////////
		
		/*Desktop desktop = Desktop.getDesktop();
	    desktop.open(new File("E:\\workspace\\ShubhamWork\\src\\main\\resources\\DriverBinaries\\WiniumDriver.exe"));
	   */
		
		
		Thread.sleep(5000);
		String appPath = "C:\\Windows\\System32\\calc.exe";
		DesktopOptions option = new DesktopOptions();
		option.setApplicationPath(appPath);
        option.setDebugConnectToRunningApp(false);
        option.setLaunchDelay(2);
		 //System.setProperty("webdriver.winium.driver.desktop","E:\\workspace\\ShubhamWork\\src\\main\\resources\\DriverBinaries\\WiniumDriver.exe");
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
       // driver.quit();
        System.out.println("process id srat");
        System.out.println("process id srat ends");
        System.out.println("start exit");

        Runtime.getRuntime().exec("taskkill /F /IM Winium.Desktop.Driver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM Calculator.exe");
        System.out.println("start exit 3");
        
	}
	catch(Exception ex) {
		System.out.println(ex.getMessage());
	}
	}
	
	  /**
     * Get all PIDs for a given name and send CTRL-C to all
     * @param processName
     * @return
     */
    public static List<String> sendCTRLC(String processName) {
        // get all ProcessIDs for the processName
        List<String> processIDs = getProcessIDs(processName);
        System.out.println("" + processIDs.size() + " PIDs found for " + processName + ": " + processIDs.toString());
        for (String pid : processIDs) {
            // close it
            sendCTRLC(Integer.parseInt(pid));
        }
        return processIDs;
    }

    /**
     * Send CTRL-C to the process using a given PID
     * @param processID
     */
    public static void sendCTRLC(int processID) {
        System.out.println(" Sending CTRL+C to PID " + processID);
        try {
            Process p = Runtime.getRuntime().exec("cmd /c ext\\SendSignalC.exe " + processID);
            //StreamGobbler.class
           // StreamGobbler.StreamGobblerLOGProcess(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get List of PIDs for a given process name
     * @param processName
     * @return
     */
    public static List<String> getProcessIDs(String processName) {
        List<String> processIDs = new ArrayList<String>();
        try {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist /v /fo csv");
            BufferedReader input = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (!line.trim().equals("")) {
                    // Pid is after the 1st ", thus it's argument 3 after splitting
                    String currentProcessName = line.split("\"")[1];
                    // Pid is after the 3rd ", thus it's argument 3 after splitting
                    String currentPID = line.split("\"")[3];
                    if (currentProcessName.equalsIgnoreCase(processName)) {
                        processIDs.add(currentPID);
                    }
                }
            }
            input.close();
        }
        catch (Exception err) {
            err.printStackTrace();
        }
        return processIDs;

    }
}
