package Utilities.queryxmlparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import Utilities.queryxmlparser.model.Feature;


public class XmlQueryparser {

/*	public static void main(String[] args) {
		XmlQueryparser parser = new XmlQueryparser();
		Feature feature = parser.getQueriesForFeature("xmlQueries/confirmationqueries/confirmations.xml");
		Map<String,String> allQuery = new HashMap<>();
		for(Scenario scenario : feature.getScenarios().getScenarioList()) {
			allQuery.putAll(scenario.getPreQuery());
			allQuery.putAll(scenario.getStepQuery());
			
		}
		System.out.println(allQuery.get("SELECT_ODB_TRANACTION_TABLE"));
	}*/
	private File getFile(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource(fileName).getFile());
	}
	/**
	 * sax parser implementation
	 * @param fileName
	 * @return
	 */
	/*public Feature getQueriesForFeature(String fileName) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		XmlQueryparser xml = new XmlQueryparser();
		SaxHandler handler = new SaxHandler();
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(new FileInputStream(xml.getFile(fileName)), handler);
//			saxParser.parse(xml.getFile(fileName), handler);
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return handler.getFeature();
	}*/
	/**
	 * following method invokes stax parser for parsing query xml
	 * @param fileName
	 * @return
	 */
	public Feature getQueriesForFeature(String fileName) {
		StaxParser stx = new StaxParser();
		return stx.readDoc(fileName);
	}

}


