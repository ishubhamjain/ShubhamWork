/*package Reporting;
*//**
 * @author Shubham Jain
 * *//*
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

import automationframework.AutomationLog;
*//**
 * Note : This Code only working and designed for Linux/Mac platform. Call/Use this class for linux/Mac platform only.
 * *//*
public class AWSUpload {

	    int iExitValue;
	    String sCommandString;
        static String Pemfilepath={.pem filepath};
        static String AWSAccountPublicIPAndPath = "reports_path";
        static String targetFolder = null;
        static String shellSciptLocation = "sh AWSUploadShell.sh";

        
    	static Date currentDate = new Date();
        static SimpleDateFormat dateFormatForFileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//dd/MM/yyyy
        static String fileDateFormet = dateFormatForFileName.format(currentDate);
     	static String testoutputfolderpath = System.getProperty("user.dir")+ File.separator +"target"+ File.separator+ "cucumber-html-reports";
    	static String zipFilefolderpath = System.getProperty("user.dir")+ File.separator +"reports"+File.separator+"results"+fileDateFormet+".zip";  // Zip Reports Files
    	static File folder = new File(testoutputfolderpath);
    	static File zipFile = new File(zipFilefolderpath);
        
	    public void runScript(String command){
	        sCommandString = command;
	        CommandLine oCmdLine = CommandLine.parse(sCommandString);
	        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
	        oDefaultExecutor.setExitValue(0);
	        try {
	            iExitValue = oDefaultExecutor.execute(oCmdLine);
	        } catch (ExecuteException e) {
	            System.err.println("Execution failed.");
	            AutomationLog.error("Error: " + e.getMessage());
	            e.printStackTrace();
	        } catch (IOException e) {
	            System.err.println("permission denied.");
	            AutomationLog.error("Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	   static public File createShellScript() {
	        String filename = "AWSUploadShell.sh";
	        File fstream = new File(filename);
	        
	        
	        ////////////////////////////////////////
	        
	        try {
				String targetFolder =   ZipReports.zipFolder(folder, zipFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        ////////////////////////////////////////

	        try{
	             // Create file 
	   	     SimpleDateFormat dateFormatForFoldername = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
	            Date currentDate31 = new Date();
	            String folderDateFormat = dateFormatForFoldername.format(currentDate31);

	            PrintStream out = new PrintStream(new FileOutputStream(fstream));
	       	    String pass = "\"password\"";
	            out.println("echo "+pass+" | "+"sudo -S scp -i "+Pemfilepath+" -r "+targetFolder+" "+ AWSAccountPublicIPAndPath + folderDateFormat +"/");
	            out.println("echo \"Success\"");
	        
	            //Close the output stream
	            out.close();
	        }catch (Exception e){//Catch exception if any
	            System.err.println("Error: " + e.getMessage());
	            AutomationLog.error("Error: " + e.getMessage());
	        }
	        return fstream;

	   }
	    public static void main(String[] args) {
	        AWSUpload testScript = new AWSUpload();
	        createShellScript();
	        testScript.runScript(shellSciptLocation);  // before running it give 600 chmod permission to pem file
	    }
	}

*/