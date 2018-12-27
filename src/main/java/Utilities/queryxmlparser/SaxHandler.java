package Utilities.queryxmlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Utilities.queryxmlparser.model.Feature;
import Utilities.queryxmlparser.model.Prerequisite;
import Utilities.queryxmlparser.model.Query;
import Utilities.queryxmlparser.model.Scenario;
import Utilities.queryxmlparser.model.Scenarios;
import Utilities.queryxmlparser.model.Step;



public class SaxHandler extends DefaultHandler {

	Feature feature = null;
	Scenarios scenarios = null;
	List<Scenario> scenarioList = null;
	Scenario scenario = null;
	Map<String, String> preQuery = null;
	Map<String, String> stepQuery = null;
	Query query = null;
	Prerequisite prerequisite = null;
	Step step = null;
	StringBuilder builder = null;
	boolean preQueryFlag = false;
	boolean stepQueryFlag = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equalsIgnoreCase("feature")) {
			feature = new Feature();
		}
		else if (qName.equalsIgnoreCase("scenarios")) {
			scenarios = new Scenarios();
			scenarioList = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("scenario")) {
			scenario = new Scenario();
//			scenario.setScenarioNumber(Integer.parseInt(attributes.getValue("number")));
		} else if (qName.equalsIgnoreCase("prerequisite")) {
			prerequisite = new Prerequisite();
		} else if (qName.equalsIgnoreCase("prequeries")) {
			preQuery = new HashMap<>();
		} else if (qName.equalsIgnoreCase("prequery")) {
			builder = new StringBuilder();
			query = new Query();
			query.setName(attributes.getValue("name"));
			query.setType(attributes.getValue("job"));
			preQueryFlag = true;
		} else if (qName.equalsIgnoreCase("Step")) {
			step = new Step();
		} else if (qName.equalsIgnoreCase("queries")) {
			stepQuery = new HashMap<>();
		} else if (qName.equalsIgnoreCase("query")) {
			builder = new StringBuilder();
			query = new Query();
			query.setName(attributes.getValue("name"));
			query.setType(attributes.getValue("job"));
			stepQueryFlag = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("query")) {
			query.setContent(builder.toString());
			stepQuery.put(query.getName(), query.getContent());
			stepQueryFlag = false;
		} else if (qName.equalsIgnoreCase("prequery")) {
			query.setContent(builder.toString());
			preQuery.put(query.getName(), query.getContent());
			preQueryFlag = false;
		} else if (qName.equalsIgnoreCase("Step")) {
			scenario.getStepQuery().putAll(stepQuery);
		} else if (qName.equalsIgnoreCase("prerequisite")) {
			scenario.getPreQuery().putAll(preQuery);
		} else if (qName.equalsIgnoreCase("scenario")) {
			scenarioList.add(scenario);
		} else if (qName.equalsIgnoreCase("scenarios")) {
			scenarios.setScenarioList(scenarioList);
		} else if (qName.equalsIgnoreCase("feature")) {
			feature.setScenarios(scenarios);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

		String value = new String(ch, start, length).trim();
		if (value.length() == 0) {
			return; 
		} else if (preQueryFlag) {
			builder.append(value);
//			builder.append("  ");
		} else if (stepQueryFlag) {
			builder.append(value);
//			builder.append("  ");
		}
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}
	
	
}


