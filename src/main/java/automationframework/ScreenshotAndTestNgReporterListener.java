package automationframework;
/**
 * @author Shubham Jain
 *
 */
//import mailReport.ReportFailListener;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
/*import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;*/

import cucumber.api.Scenario;
import pageobjects.Page;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class ScreenshotAndTestNgReporterListener {

    private static boolean createFile(File screenshot) {
        boolean fileCreated = false;

        if (screenshot.exists()) {
            fileCreated = true;
        } else {
            File parentDirectory = new File(screenshot.getParent());
            if (parentDirectory.exists() || parentDirectory.mkdirs()) {
                try {
                    fileCreated = screenshot.createNewFile();
                } catch (IOException errorCreatingScreenshot) {
                    errorCreatingScreenshot.printStackTrace();
                }
            }
        }
        return fileCreated;
    }

    private static void writeScreenshotToFile(WebDriver driver, File screenshot) {
        try {
            FileOutputStream screenshotStream = new FileOutputStream(screenshot);
            screenshotStream.write(((TakesScreenshot) Page.driver).getScreenshotAs(OutputType.BYTES));
            screenshotStream.close();
        } catch (IOException unableToWriteScreenshot) {
            System.err.println("Unable to write " + screenshot.getAbsolutePath());
            unableToWriteScreenshot.printStackTrace();
        }
    }
    
  //  @Override
    public void onTestFailure() {
        try {
            WebDriver driver = Page.driver;
            File screenShot=((TakesScreenshot)Page.driver).getScreenshotAs(OutputType.FILE);
            // extracting date for folder name.
            SimpleDateFormat dateFormatForFoldername = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
            Date currentDate = new Date();
            String folderDateFormat = dateFormatForFoldername.format(currentDate);
            // extracting date and time for snapshot file
            SimpleDateFormat dateFormatForFileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//dd/MM/yyyy
            String fileDateFormet = dateFormatForFileName.format(currentDate);
       //     ReportFailListener testcasename = new ReportFailListener();
       //     System.out.println("My Testcase name is ="+testcasename);
            String filefolder="./test-output"+"/Snap/"+folderDateFormat+"/";
            // Creating folders and files
            File screenshot = new File(filefolder+fileDateFormet+".jpeg");
            FileUtils.copyFile(screenShot, new File(screenshot.getPath()));
            if (createFile(screenshot)) {
                try {
                    writeScreenshotToFile(driver, screenshot);
                } catch (ClassCastException weNeedToAugmentOurDriverObject) {
                    writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
                }
                //Reports present test-ouput as a root for files so using path as snapfile
                extractFoldersAndReport();
                
                System.err.println("Screenshot captured successfully..!!");
            } else {
                System.err.println("Unable to create " + filefolder);
            }
            
        } catch (Exception ex) {
           // System.err.println("Unable to capture screenshot.!!");
            //ex.printStackTrace();
        }
    }
    
    public static void extractFoldersAndReport()
    {
        File dir= new File("./test-output"+File.separator+"Snap");

        File[] subDirs = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        String folder = null;
        ArrayList<String> allfolders = new ArrayList<String>();
        for (File subDir : subDirs) {
    		String splittest = subDir.toString().split("Snap")[1];
    		folder = splittest.split(Pattern.quote(File.separator))[1];
            allfolders.add(folder);
        }
        
        String lastValue = allfolders.get(allfolders.size() - 1);
        String snapfile="./Snap/"+lastValue+"/";
//        Reporter.log("<a href=\"" + snapfile + "\"><p align=\"left\">Add New PR screenshot at " + lastValue + "</p>");
       /*
                // If required all folders in snap folder
        for (String subDir : allfolders) {
        	System.out.println(subDir);
            String snapfile="./Snap/"+subDir+"/";
            Reporter.log("<a href=\"" + snapfile + "\"><p align=\"left\">Add New PR screenshot at " + subDir + "</p>");
        }
       */
    }
    public static void customScreenshot(){
        try {
        
            File screenShot=((TakesScreenshot)Page.driver).getScreenshotAs(OutputType.FILE);
            // extracting date for folder name.
            SimpleDateFormat dateFormatForFoldername = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
            Date currentDate = new Date();
            String folderDateFormat = dateFormatForFoldername.format(currentDate);
            // extracting date and time for snapshot file
            SimpleDateFormat dateFormatForFileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//dd/MM/yyyy
            String fileDateFormet = dateFormatForFileName.format(currentDate);
            String filefolder="./target"+"/cucumber-html-reports/"+"/Snap/"+"/CustomScreenShot/"+folderDateFormat+"/";
            // Creating folders and files
            File screenshot = new File(filefolder+fileDateFormet+".jpeg");
            FileUtils.copyFile(screenShot, new File(screenshot.getPath()));
            } 
            catch (Exception ex) {
                System.err.println("Unable to capture screenshot...");
                ex.printStackTrace();
            }
    }
    
    public static void customScreenshotOnFail(String ScenarioName){
        try {
            File screenShot=((TakesScreenshot)Page.driver).getScreenshotAs(OutputType.FILE);
            // extracting date for folder name.
            SimpleDateFormat dateFormatForFoldername = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
            Date currentDate = new Date();
            String folderDateFormat = dateFormatForFoldername.format(currentDate);
            // extracting date and time for snapshot file
            SimpleDateFormat dateFormatForFileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//dd/MM/yyyy
            String fileDateFormet = dateFormatForFileName.format(currentDate);
            String filefolder="./target"+"/cucumber-html-reports/"+"/Snap/"+"/FailCaseScreenShot/"+folderDateFormat+"/";
            // Creating folders and files
            File screenshot = new File(filefolder+ScenarioName+"-"+fileDateFormet+".jpeg");
            FileUtils.copyFile(screenShot, new File(screenshot.getPath()));
            } 
            catch (Exception ex) {
                System.err.println("Unable to capture screenshot...");
                ex.printStackTrace();
            }
    }
    
}