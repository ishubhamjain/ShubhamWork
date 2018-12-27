package someimpclasses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;

import Utilities.AutomationLog;
import Utilities.ExcelLib;
import cucumber.api.Scenario;
import cucumber.api.java.Before;

public class SceanrioHookSample {

	// @Before
	public void testing(Scenario scenario) throws FileNotFoundException, IOException {
		try {
			String sheetName = null;
			String featureName = null;
			String rawFeatureName = scenario.getId().split(";")[0].replace("-", " ");
			featureName = rawFeatureName.substring(0, 1).toUpperCase() + rawFeatureName.substring(1);
			AutomationLog.info("Feature Name =" + featureName);
			if (featureName.equalsIgnoreCase("Paper confirmation generation for various products")) {
				sheetName = "PaperConfirmation";
			} else if (featureName.equalsIgnoreCase("DTCC confirmation generation for various products")) {
				sheetName = "DTCCConfirm";
			} else if (featureName.equalsIgnoreCase("Swapswire confirmation generation for various products")) {
				sheetName = "SwapswireConfirm";
			} else {
				AutomationLog.info("Data Sheet not avaiable for feature = " + featureName);
				Assert.assertTrue("Data Sheet not avaiable for feature =" + featureName, false);
			}

			AutomationLog.info("*******************Scenario detail : " + scenario.getName() + "**********************");
			List<String> sheetval = ExcelLib.getRowValues(sheetName, scenario.getName());
			if (sheetval.isEmpty()) {
				AutomationLog.info("Scenario details not found = " + scenario.getName());
				Assert.assertTrue("Scenario details not found = " + scenario.getName(), false);
			}
			AutomationLog.info("Scenario name = " + scenario.getName());
			AutomationLog.info("Sheet Data = " + sheetval);
			/*
			 * bean.setTypeOfConfirm(sheetval.get(1)); bean.setProduct(sheetval.get(2));
			 * bean.setPostTradeEvent(sheetval.get(3));
			 * bean.setWorkflowStep(sheetval.get(4)); bean.setState(sheetval.get(5));
			 * bean.setDealEvent(sheetval.get(6));
			 */

			AutomationLog.info("Beans sets");
		} catch (Exception ex) {
			AutomationLog.error(ex.getMessage(), ex);
		}
		AutomationLog.info("Before hook is executed");
	}


	public boolean resultInDb(String step, int timeouttime) throws Throwable {
		boolean statusOfQuery = true;
		long lStartTime = System.nanoTime();
		long lEndTime;
		long output;
		long totaltime;
		int timeoutinmin = timeouttime;
		timeoutinmin = timeoutinmin * 60;
		while (statusOfQuery) {
			Boolean confirmationStateResult = null;
			if (step.equalsIgnoreCase("state")) {
				// confirmationStateResult = confirmationsDoa.getConfirmationState(bean);
				if (confirmationStateResult) {
					AutomationLog.info("StateCreationTimestampDetails is Found successfully");
					statusOfQuery = false;
				} else {
					AutomationLog.info(
							"Waiting DB to be ready after job finish and StateCreationTimestampDetails, No record found yet so waiting for 20 sec each for "
									+ timeoutinmin + " sec");
					TimeUnit.SECONDS.sleep(20);
				}
			} else if (step.equalsIgnoreCase("valueofp")) {
				// confirmationsDoa.getConfirmationDealEvent(bean);
				/*
				 * if (bean.getEventprocessingstatus().equalsIgnoreCase("P")) { AutomationLog.
				 * info("Value of Eventprocessingstatus is Found as P successfully");
				 * statusOfQuery = false; } else { AutomationLog.info(
				 * "Waiting pollar to change the Value of Eventprocessingstatus as P, No change found yet so waiting for 20 sec each for "
				 * +timeoutinmin+" sec"); TimeUnit.SECONDS.sleep(20); } } else if
				 * (step.equalsIgnoreCase("waitforpaper")) {
				 * //confirmationsDoa.getTicketIDForPaper(bean); if(bean.getTicketid()!=0) {
				 * AutomationLog.info("Value of PAPER_CONFIRMS is Found"); statusOfQuery =
				 * false; }else { AutomationLog.info(
				 * "Waiting DB to be ready to get value of PAPER_CONFIRMS, No record found yet so waiting for 20 sec each for "
				 * +timeoutinmin+" sec"); TimeUnit.SECONDS.sleep(20); } } else if
				 * (step.equalsIgnoreCase("waitforDTCC")) {
				 * //confirmationsDoa.getTicketIDForDTCC(bean); if(bean.getTicketid()!=0) {
				 * AutomationLog.info("Value of DTCC_CONFIRMS is Found"); statusOfQuery = false;
				 * }else { AutomationLog.info(
				 * "Waiting DB to be ready to get value of DTCC_CONFIRMS, No record found yet so waiting for 20 sec each for "
				 * +timeoutinmin+" sec"); TimeUnit.SECONDS.sleep(20); }
				 */
			}
		}
		return statusOfQuery;
	}
}
