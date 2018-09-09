package Reporting;
/**
 * @author Shubham Jain
 *
 */
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.remote.BrowserType;


import automationframework.AppDriver;
import automationframework.Configuration;
import automationframework.ExcelLib;
import pageobjects.Page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * Note : This Class zip the folder of reports and sent the email via gmail. Make sure files do not contains any JS files or extention banned by gmail. 
 * Don't use automation log in this class, It will override all before logs of the same session
 * */
public class SendMailClass 
{
	public SendMailClass(){
		
	}
/*	static ExcelLib xl = new ExcelLib();
	static String browserType = xl.getXLcellValue("Controller", 1, 0);
	static String serverName = xl.getXLcellValue("Controller", 1, 1);*/
	
    private static String OSNAMES = System.getProperty("os.name").toLowerCase();
    static String OS = OSNAMES.split(" ")[0];
	static Date currentDate = new Date();
    static SimpleDateFormat dateFormatForFileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//dd/MM/yyyy
    static String fileDateFormet = dateFormatForFileName.format(currentDate);
//	static String testoutputfolderpath = System.getProperty("user.dir")+ File.separator +"target"+ File.separator+ "cucumber-html-reports";
//	static String zipFilefolderpath = System.getProperty("user.dir")+ File.separator +"reports"+File.separator+"results"+fileDateFormet;  // Zip Reports Files
	static String zipLogFilefolderpath = System.getProperty("user.dir")+ File.separator +"logs"+File.separator+"logs.txt";
/*	static File folder = new File(zipLogFilefolderpath);
	static File zipFile = new File(zipFilefolderpath);*/
	static String subject = "Shubham Automation Application Test Cucumber report";
	static String PlatformName = Configuration.getConfigurationValueForProperty("Report-Upload-Platform-Name");
	static String emailForsendingnotification = Configuration.getConfigurationValueForProperty("Email-Sending-Notification");
	static String PasswordForsendingnotification = Configuration.getConfigurationValueForProperty("Pass-Sending-Notification");
	
    public static void main(String[] args) throws IOException {
    	try {
			execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    }
	
    public static void execute() throws Exception
    {
    	
    //	ExcelLib xl = new ExcelLib();
    	
    	
    String FilenameLocalPath=ZipReports.zipFilePathName();
    
	String[] b = FilenameLocalPath.split("\\\\");
	String FileName =  b[b.length-1];
    
    String text = "Hi Greetings,\n This is Final Automation Test Report.\n This Auto-generated report please do not reply.\n Find the reports on "+PlatformName+" account, The Report file name is : "+FileName+".\n Please download your file from given URL: https://www.dropbox.com/home/Apps/AutomationResults/Apps/ShubhamCucumberReports";
    	//////////////////////////////////////////////////////////////////////////////////
    	////////////////////////Create a zip file of test-output folder /////////////////
    //    path of file which contains report
    	
  //      String path = zipFilefolderpath;
        String path2 = zipLogFilefolderpath;

     //   No of recipients of the report, you can have many separated by comma
        
        String mailto = "shubham.jain@cuelogic.co.in";
        String mailcc = "shubham.jain@cuelogic.co.in";
        String mailbcc = "shubham.jain@cuelogic.co.in";
        
        
        String[] to={mailto};
        String[] cc={mailcc};
        String[] bcc={mailbcc};

        
      //  email user name and password of sender 
   
   
        SendMailClass.sendMail(emailForsendingnotification,   
        					PasswordForsendingnotification,                     
                            "smtp.gmail.com",
                            "465",
                            "true",
                            "true",
                            "javax.net.ssl.SSLSocketFactory",
                            "false",
                             to,
                             cc,
                             bcc,
                            subject,
                            text,
                            path2,
                            "Test-Report");
      }

      public  static boolean sendMail(String userName,
                String passWord,
                String host,
                String port,
                String starttls,
                String auth,
                String socketFactoryClass,
                String fallback,
                String[] to,
                String[] cc,
                String[] bcc,
                String subject,
                String text,
                String attachmentPath,
                String attachmentName){

        //Object Instantiation of a properties file.
        Properties props = new Properties();

        props.put("mail.smtp.user", userName);

        props.put("mail.smtp.host", host);

        if(!"".equals(port)){
        props.put("mail.smtp.port", port);
        }

        if(!"".equals(starttls)){
            props.put("mail.smtp.starttls.enable",starttls);
            props.put("mail.smtp.auth", auth);
        }

        if(!"".equals(port)){
            props.put("mail.smtp.socketFactory.port", port);
        }
        if(!"".equals(socketFactoryClass)){
            props.put("mail.smtp.socketFactory.class",socketFactoryClass);
        }
        if(!"".equals(fallback)){
            props.put("mail.smtp.socketFactory.fallback", fallback);
        }

        try{

            Session session = Session.getDefaultInstance(props, null);

            MimeMessage msg = new MimeMessage(session);
            
            msg.setText(text);

            msg.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            //////////////////////////////////
			BodyPart objMessageBodyPart = new MimeBodyPart();

			objMessageBodyPart.setText(text);
			/////////////////////////////////
            messageBodyPart.setText(text);
            messageBodyPart.setContent(text, "text/html");
            DataSource source = new FileDataSource(attachmentPath);
                                  
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Logs.txt");
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(objMessageBodyPart);
/*
            /////////////////////////////// Add another File ////////////////
            
            DataSource source2 = new FileDataSource(attachmentPath2);  
            BodyPart messageBodyPart2 = new MimeBodyPart();           
            messageBodyPart2.setText(text);
            messageBodyPart2.setDataHandler(new DataHandler(source2));
            messageBodyPart2.setFileName(attachmentPath2);
            messageBodyPart2.setFileName("Reports.txt");
            multipart.addBodyPart(messageBodyPart2);
            
            /////////////////////////////////////////////////////////////////////
            */
            msg.setContent(multipart);
            
            msg.setFrom(new InternetAddress(userName));

            for(int i=0;i<to.length;i++){
                msg.addRecipient(Message.RecipientType.TO, new
InternetAddress(to[i]));
            }

            for(int i=0;i<cc.length;i++){
                msg.addRecipient(Message.RecipientType.CC, new
InternetAddress(cc[i]));
            }

            for(int i=0;i<bcc.length;i++){
                msg.addRecipient(Message.RecipientType.BCC, new
InternetAddress(bcc[i]));
            }

            msg.saveChanges();

            Transport transport = session.getTransport("smtp");

            transport.connect(host, userName, passWord);

            transport.sendMessage(msg, msg.getAllRecipients());

            transport.close();
			//closing .exe of chrome or phantom driver binaries
		//	AppDriver.clearBrowserContext(Page.driver);
            return true;

        } catch (Exception mex){
            mex.printStackTrace();
            return false;
        }
    }
}
