package Utilities.queryxmlparser.model;

import java.util.HashMap;
import java.util.Map;

public class Scenario {
	
	int scenarioNumber;
	Map<String,String> preQuery = new HashMap<>();
	Map<String,String> stepQuery = new HashMap<>();
	public Map<String, String> getPreQuery() {
		return preQuery;
	}
	public void setPreQuery(Map<String, String> preQuery) {
		this.preQuery = preQuery;
	}
	public Map<String, String> getStepQuery() {
		return stepQuery;
	}
	public void setStepQuery(Map<String, String> stepQuery) {
		this.stepQuery = stepQuery;
	}
	public int getScenarioNumber() {
		return scenarioNumber;
	}
	public void setScenarioNumber(int scenarioNumber) {
		this.scenarioNumber = scenarioNumber;
	}
	
	
}

