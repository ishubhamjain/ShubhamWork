package Utilities;
/**
 * @author Shubham Jain
 * */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import DataBases.FunctionalId;


public class YmlReader {

	public static Map<String,FunctionalId> functionId = new HashMap<>();
	
	public static String readYmlProperty() {
		Properties prop = new Properties();
		String activeDB = null;
		try {
			prop.load(new FileInputStream("configuration/database.properties"));
			String[] activeDatabase = prop.getProperty("activeDatabase").split(",");
			for(String db : activeDatabase) {
				FunctionalId id = ymlLogic(db);
				functionId.put(db, id);
			}
			if(functionId.isEmpty()) {
				throw new Exception("Active DB Profile not found");
			}
		} catch (Exception ex) {
			AutomationLog.error(ex.getMessage());
		}
		return activeDB;
	}

	private static FunctionalId ymlLogic(String activeDatabase) throws YamlException, FileNotFoundException {
		YamlReader reader;
		reader = new YamlReader(new FileReader("configuration/config.yml"));
		Object object = reader.read();
		Map map = (Map) object;
		Map map2 = (Map) map.get("Agents");
		System.out.println("test"+map2);
		return mapFunctionalIdToBean((Map<String, String>) map2.get(activeDatabase),activeDatabase);
	}
	
	private static FunctionalId mapFunctionalIdToBean(Map<String, String> map,String activeDatabase) {
		FunctionalId id = new FunctionalId();
		id.setId(activeDatabase);
		id.setDbType(map.get("databaseType"));
		id.setHost(map.get("hostName"));
		id.setPort(map.get("port"));
		id.setUsername(map.get("userName"));
		id.setPassword(map.get("password"));
		id.setUseFunctionalId(map.get("usefunctionalid").equalsIgnoreCase("true") ? true :false);
		id.setEncryptPassword(map.get("encrytpassword"));
		return id;
	}

/*	public static String getProperty(String key) {
		if(functionId.isEmpty()) {
			readYmlProperty();
		}
		return functionId.get(key);
		
	}*/
	public static void main(String[] args) {
		readYmlProperty();
		System.out.println(functionId.size());
	}
}

