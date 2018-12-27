package interfaces;


import java.util.HashMap;
import java.util.Map;

import Utilities.queryxmlparser.XmlQueryparser;
import Utilities.queryxmlparser.model.Feature;
import Utilities.queryxmlparser.model.Scenario;



public class BaseQueryUtil implements QueryUtil {
	
	Map<String,String> allQuery = new HashMap<>();
	
	@Override
	public void xmlparser(String fileName) {
		XmlQueryparser parser = new XmlQueryparser();
		Feature feature = parser.getQueriesForFeature(fileName);
		for(Scenario scenario : feature.getScenarios().getScenarioList()) {
			allQuery.putAll(scenario.getPreQuery());
			allQuery.putAll(scenario.getStepQuery());
		}

	}
	
	public String getQuery(String query) {
		return allQuery.get(query);
	}

}

