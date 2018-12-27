package someimpclasses;

import java.util.concurrent.TimeUnit;
import org.junit.Assert;

import Utilities.AutomationLog;
import Utilities.JobRunner;


public class AUServer {
			
	static String jobname = null;
	static String instance=null;
	static int waittime;
	JobRunner jobrunner = new JobRunner();
	static AUServer autoserver = new AUServer();
	public void confirmAutosysJob(String instance,String jobname, String jobType, int waitTime) {
		this.jobname = jobname;
		this.instance=instance;
		this.waittime=waitTime;
		this.waittime=this.waittime*60;
		//Step 4:   -- -- -- Switch to Autosys server to run 149976_ConfirmEventGenAutomation_CLD service on UAT
		String command=". /opt/CA/WorkloadAutomationAE/autouser."+instance+"/autosys.ksh.tiServerName;echo $AUTOSERV";
		jobrunner.runMe(command);
		boolean envstatus;
		
		if(JobRunner.commandResults.contains(instance)) {
			AutomationLog.info("switched to "+instance);
			envstatus=true;
		}
		else {
			envstatus=false;
			AutomationLog.info("Failed to switched to "+instance);
			Assert.assertTrue(instance+" env not set",false);
		}

		if(envstatus) {
			AutomationLog.info("*********************************");
			String command2=". /opt/CA/WorkloadAutomationAE/autouser."+instance+"/autosys.ksh.tiServerName;ls;whoami;echo $AUTOSERV;autorep -J "+jobname+"  | grep "+jobname+"  | wc -l";
			AutomationLog.info(command2);
			AutomationLog.info("Start Connection");
			jobrunner.runMe(command2);
			AutomationLog.info("First commmand hit");
			String jobStatus = JobRunner.commandResults.substring(JobRunner.commandResults.length() - 3);
			AutomationLog.info("jobStatus ="+jobStatus);
			if(jobStatus.replace("\n", "").replace("\r", "").equalsIgnoreCase("1")) {
				AutomationLog.info("Yes job present");
			    /*String command3=". /opt/CA/WorkloadAutomationAE/autouser."+instance+"/autosys.ksh.tiServerName;ls;echo $AUTOSERV;autorep -J "+jobname+"  | grep "+jobname+"  | wc -l;sendevent -E KILLJOB -J "+jobname+"";
			    AutomationLog.info(command3);
			    JobRunner.runMe(command3, ConfigurationReader.autosysServerUsername(),ConfigurationReader.autosysServerPassword(),ConfigurationReader.autosysServerHost());
			    */
			    jobRunStatus(instance,jobname);
			    String jobStatus2 = JobRunner.commandResults.substring(JobRunner.commandResults.length() - 3);
			    AutomationLog.info("jobStatus2 ="+jobStatus2);
			    if(jobStatus2.replace("\n", "").replace("\r", "").equalsIgnoreCase("0")) {
			    	forceStart();
			    	waitforJobToBeZeroAfter("after");
			    }
			    else if(jobStatus2.replace("\n", "").replace("\r", "").equalsIgnoreCase("1")) {
			    	if(jobType.equalsIgnoreCase("NA")) {
			    	waitforJobToBeZero("before");
			    	waitforJobToBeZeroAfter("after");
			    	}
			    }
			    }
		}	
	}
		
	public static void waitforJobToBeZero(String when) {
    	boolean statusOfJob=true;
    	long lStartTime = System.nanoTime();
    	long lEndTime;
    	long output;
    	long totaltime;
    	while(statusOfJob) {
    		AutomationLog.info("In while loop");
    		autoserver.jobRunStatus(instance,jobname);
		    String jobStatus3 = JobRunner.commandResults.substring(JobRunner.commandResults.length() - 3);
    		if(jobStatus3.replace("\n", "").replace("\r", "").equalsIgnoreCase("0")) {
    			if(when.equalsIgnoreCase("before")) {
    				autoserver.forceStart();
		    	statusOfJob=false;
    			}
    			else if(when.equalsIgnoreCase("after")){
    				statusOfJob=false;
    			}
		    }
    		else {
    			try {
    				AutomationLog.info("20 sec time out");
					TimeUnit.SECONDS.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    		
    		lEndTime = System.nanoTime();
    		 output = lEndTime - lStartTime;
    		 totaltime = output / 1000000000;
    		 AutomationLog.info("totaltime ="+ totaltime);
    		 AutomationLog.info("In Zero loop 3 m");
    		if(totaltime>=180) {  // 180 seconds = 3 minutes
    			break;
    		}
    		
    	}
	}
	
	public static void waitforJobToBeZeroAfter(String when) {
    	boolean statusOfJob=true;
    	long lStartTime = System.nanoTime();
    	long lEndTime;
    	long output;
    	long totaltime;
    	while(statusOfJob) {
    		AutomationLog.info("In while loop");
    		autoserver.jobRunStatus(instance,jobname);
		    String jobStatus3 = JobRunner.commandResults.substring(JobRunner.commandResults.length() - 3);
    		if(jobStatus3.replace("\n", "").replace("\r", "").equalsIgnoreCase("0")) {
    			if(when.equalsIgnoreCase("before")) {
    				autoserver.forceStart();
		    	statusOfJob=false;
    			}
    			else if(when.equalsIgnoreCase("after")){
    				statusOfJob=false;
    			}
		    }
    		else {
    			try {
    				AutomationLog.info("20 sec time out");
					TimeUnit.SECONDS.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    		
    		lEndTime = System.nanoTime();
    		 output = lEndTime - lStartTime;
    		 totaltime = output / 1000000000;
    		 AutomationLog.info("totaltime ="+ totaltime);
    		 AutomationLog.info("In After Zero loop 10 m");
    /*		 int min;
    		 if(when.equalsIgnoreCase("after")) {
    			 
    		 }
    		 else if(when.equalsIgnoreCase("before")) {
    			 
    		 }*/
    		 
    		/*if(totaltime>600) {  // 600 seconds = 10 min minutes
    			break;
    		}*/
    		 
    		 if(totaltime>waittime) {  // 600 seconds = 10 min minutes
     			break;
     		}
    		
    	}
	}
	
	public void forceStart() {
		AutomationLog.info("In force start");
		String command4=". /opt/CA/WorkloadAutomationAE/autouser."+instance+"/autosys.ksh.tiServerName;ls;echo $AUTOSERV;autorep -J "+jobname+"  | grep "+jobname+"  | wc -l;autorep -J  "+jobname+"  | grep  RU | wc -l;/opt/CA/WorkloadAutomationAE1136/autosys/bin/sendevent -E FORCE_STARTJOB -J "+jobname+"";
		AutomationLog.info(command4);
		AutomationLog.info("Second commmand hit");
		jobrunner.runMe(command4);
	}
	
	public JobRunner jobRunStatus(String instances,String jobnames) {
		 jobname=jobnames;
		 instance=instances;
		 String command6=". /opt/CA/WorkloadAutomationAE/autouser."+instance+"/autosys.ksh.tiServerName;ls;echo $AUTOSERV;autorep -J "+jobname+"  | grep "+jobnames+"  | wc -l;autorep -J  "+jobnames+"  | grep  RU | wc -l";
		 AutomationLog.info(command6);   
		 AutomationLog.info("Second commmand hit");
		 jobrunner.runMe(command6);
		 return jobrunner;
	}
	
	public JobRunner killJob(String instance, String jobnames) {
		jobname=jobnames;
		AutomationLog.info("In Kill Job");
		String command3=". /opt/CA/WorkloadAutomationAE/autouser."+instance+"/autosys.ksh.tiServerName;ls;echo $AUTOSERV;autorep -J "+jobname+"  | grep "+jobname+"  | wc -l;sendevent -E KILLJOB -J "+jobname+"";
	    AutomationLog.info(command3);
	    jobrunner.runMe(command3);
	    return jobrunner;
	}
}
