package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class YmlReader {
	
	public static String ReadYmlProperty(String key) throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.load(new FileInputStream("configuration/database.properties"));	
		String activeDatabase= prop.getProperty("activeDatabase");
	
		String value = ymlLogic(activeDatabase,key);
		System.out.println(value);
		return value;
	}
	
	public static String ymlLogic(String activeDatabase, String key) throws YamlException {
		String value;
		YamlReader yml;
		try {
			yml = new YamlReader(new FileReader("configuration/config.yml"));
			Object object = yml.read();
			Map map = (Map) object;
			Map map2 = (Map) map.get("Agents");
			Map<String,String> map3 = (Map<String, String>) map2.get(activeDatabase);
			value=map3.get(key);
			
			return value;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
