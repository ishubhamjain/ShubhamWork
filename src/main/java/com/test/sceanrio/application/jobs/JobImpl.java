package com.test.sceanrio.application.jobs;

import java.util.List;

import Utilities.AutomationLog;
import Utilities.JobRunner;

public class JobImpl implements JobInterface {

	
	@Override
	public void runJob(List<String> commands) {
		StringBuilder bf = new StringBuilder();
		for(String command : commands) {
			bf.append(command);
			bf.append(";");
		}
		AutomationLog.info(bf.toString());
		JobRunner runner = new JobRunner();
		runner.runMe(bf.toString());
	}
	
	@Override
	public void importjob(String importjob) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Implement your jobs of unix here execution");
		System.out.println("Create an object of JobImp in stepdefination and call job functions");
		
	}

}
