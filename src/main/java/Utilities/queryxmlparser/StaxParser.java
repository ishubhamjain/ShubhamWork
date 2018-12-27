package Utilities.queryxmlparser;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import Utilities.queryxmlparser.model.Feature;
import Utilities.queryxmlparser.model.Prerequisite;
import Utilities.queryxmlparser.model.Query;
import Utilities.queryxmlparser.model.Scenario;
import Utilities.queryxmlparser.model.Scenarios;
import Utilities.queryxmlparser.model.Step;



public class StaxParser {
	
	private Feature feature = null;
	private Scenarios scenarios = null;
	private List<Scenario> scenarioList = null;
	private Scenario scenario = null;
	private Map<String, String> preQuery = null;
	private Map<String, String> stepQuery = null;
	private Query query = null;
	private Prerequisite prerequisite = null;
	private Step step = null;
	private StringBuilder builder = null;
	boolean preQueryFlag = false;
	boolean stepQueryFlag = false;
	
	/*public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
		StaxParser stx = new StaxParser();
		Feature feature = stx.readDoc("xmlQueries/paymentreset/PaymentReset.xml");
		Map<String,String> allQuery = new HashMap<>();
		for(Scenario scenario : feature.getScenarios().getScenarioList()) {
			allQuery.putAll(scenario.getPreQuery());
			allQuery.putAll(scenario.getStepQuery());
		}
		System.out.println(allQuery.get("GET_DEAL_FOR_PAYMENT_RESET"));
		System.out.println(allQuery.get("DELETE_NDB_PAYMENT"));

	}*/

	public Feature readDoc(String string) {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		 
        XMLEventReader eventReader = null;
		try {
			eventReader = factory.createXMLEventReader(new FileReader(getClass().getClassLoader().getResource(string).getFile()));
			while (eventReader.hasNext())
	        {   XMLEvent event = eventReader.nextEvent();
	 
	            if (event.isStartElement())
	            {
	                StartElement element = (StartElement)event;
	                
	                if(element.getName().toString().equalsIgnoreCase("feature")) {
	        			feature = new Feature();
	        		}
	        		else if (element.getName().toString().equalsIgnoreCase("scenarios")) {
	        			scenarios = new Scenarios();
	        			scenarioList = new ArrayList<>();
	        		} else if (element.getName().toString().equalsIgnoreCase("scenario")) {
	        			scenario = new Scenario();
	        		} else if (element.getName().toString().equalsIgnoreCase("prerequisite")) {
	        			prerequisite = new Prerequisite();
	        		} else if (element.getName().toString().equalsIgnoreCase("prequeries")) {
	        			preQuery = new HashMap<>();
	        		} else if (element.getName().toString().equalsIgnoreCase("prequery")) {
	        			query = getQueryObject(element);
	        			preQueryFlag = true;
	        		} else if (element.getName().toString().equalsIgnoreCase("Step")) {
	        			step = new Step();
	        		} else if (element.getName().toString().equalsIgnoreCase("queries")) {
	        			stepQuery = new HashMap<>();
	        		} else if (element.getName().toString().equalsIgnoreCase("query")) {
	        			query = getQueryObject(element);
	        			stepQueryFlag = true;
	        		}
	               
	            }
	 
	            if (event.isEndElement())
	            {
	                EndElement element = (EndElement) event;
	        		if (element.getName().toString().equalsIgnoreCase("query")) {
	        			stepQuery.put(query.getName(), query.getContent());
	        			stepQueryFlag = false;
	        		} else if (element.getName().toString().equalsIgnoreCase("prequery")) {
	        			preQuery.put(query.getName(), query.getContent());
	        			preQueryFlag = false;
	        		} else if (element.getName().toString().equalsIgnoreCase("Step")) {
	        			scenario.getStepQuery().putAll(stepQuery);
	        		} else if (element.getName().toString().equalsIgnoreCase("prerequisite")) {
	        			scenario.getPreQuery().putAll(preQuery);
	        		} else if (element.getName().toString().equalsIgnoreCase("scenario")) {
	        			scenarioList.add(scenario);
	        		} else if (element.getName().toString().equalsIgnoreCase("scenarios")) {
	        			scenarios.setScenarioList(scenarioList);
	        		} else if (element.getName().toString().equalsIgnoreCase("feature")) {
	        			feature.setScenarios(scenarios);
	        		}
	        	
	            }
	 
	            if (event.isCharacters())
	            {
	                Characters element = (Characters) event;
	                if (preQueryFlag || stepQueryFlag )
	                {
	                	query.setContent(element.getData());
	                }
	                
	            }
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
 
        
		return feature;
	}

	private Query getQueryObject(StartElement element) {
		Query query = new Query();
		Iterator<Attribute> iterator = element.getAttributes();
        while (iterator.hasNext())
        {
            Attribute attribute = iterator.next();
            QName name = attribute.getName();
            String value = attribute.getValue();
            if("name".equalsIgnoreCase(name.toString())) {
            	query.setName(value);
            }else if("type".equalsIgnoreCase(name.toString())) {
            	query.setType(value);
            }
        }
        return query;
	}

	/*private Feature readDoc(String string) {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try {
			XMLStreamReader eventReader = factory.createXMLStreamReader(getClass().getClassLoader().getResourceAsStream(string));
			while (eventReader.hasNext()) {
	            int eventType = eventReader.next();
	            switch (eventType) {
	                case XMLStreamReader.START_ELEMENT:
	                    String elementName = eventReader.getLocalName();
	                    if(elementName.equalsIgnoreCase("feature")) {
	            			feature = new Feature();
	            		}
	            		else if (elementName.equalsIgnoreCase("scenarios")) {
	            			scenarios = new Scenarios();
	            			scenarioList = new ArrayList<>();
	            		} else if (elementName.equalsIgnoreCase("scenario")) {
	            			scenario = new Scenario();
	            		} else if (elementName.equalsIgnoreCase("prerequisite")) {
	            			prerequisite = new Prerequisite();
	            		} else if (elementName.equalsIgnoreCase("prequeries")) {
	            			preQuery = new HashMap<>();
	            		} else if (elementName.equalsIgnoreCase("prequery")) {
	            			query = new Query();
	            			query.setName(eventReader.getAttributeValue(null, "name"));
	            			query.setType(eventReader.getAttributeValue(null, "type"));
	            			query.setContent(readData(eventReader).toString());
	            		} else if (elementName.equalsIgnoreCase("Step")) {
	            			step = new Step();
	            		} else if (elementName.equalsIgnoreCase("queries")) {
	            			stepQuery = new HashMap<>();
	            		} else if (elementName.equalsIgnoreCase("query")) {
	            			query = new Query();
	            			query.setName(eventReader.getAttributeValue(null, "name"));
	            			query.setType(eventReader.getAttributeValue(null, "type"));
	            			query.setContent(readData(eventReader).toString());
	            		}
	                    break;
	                case XMLStreamReader.END_ELEMENT:
	                	 String elementNameEnd = eventReader.getLocalName();
	                	if (elementNameEnd.equalsIgnoreCase("query")) {
	            			stepQuery.put(query.getName(), query.getContent());
	            		} else if (elementNameEnd.equalsIgnoreCase("prequery")) {
	            			preQuery.put(query.getName(), query.getContent());
	            		} else if (elementNameEnd.equalsIgnoreCase("Step")) {
	            			scenario.getStepQuery().putAll(stepQuery);
	            		} else if (elementNameEnd.equalsIgnoreCase("prerequisite")) {
	            			scenario.getPreQuery().putAll(preQuery);
	            		} else if (elementNameEnd.equalsIgnoreCase("scenario")) {
	            			scenarioList.add(scenario);
	            		} else if (elementNameEnd.equalsIgnoreCase("scenarios")) {
	            			scenarios.setScenarioList(scenarioList);
	            		} else if (elementNameEnd.equalsIgnoreCase("feature")) {
	            			feature.setScenarios(scenarios);
	            		}
	                    break;
	                default:
	                    break;

	            }
	        }
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return feature;
	}

	private StringBuilder readData(XMLStreamReader reader) throws XMLStreamException {
		StringBuilder result = new StringBuilder();
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.CHARACTERS:
                	break;
                case XMLStreamReader.CDATA:
                    result.append(reader.getText());
                    break;
                case XMLStreamReader.END_ELEMENT:
                default:
                    break;
            }
        }
        return result;
	}*/

}

