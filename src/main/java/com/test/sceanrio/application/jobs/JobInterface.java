package com.test.sceanrio.application.jobs;

import java.util.List;

public interface JobInterface {

	public void runJob(List<String> command);
	public void importjob(String importjob) throws InterruptedException;
}
