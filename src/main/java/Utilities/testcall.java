package Utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class testcall {

	public static void main(String[] args) throws FileNotFoundException, YamlException {
		YamlReader yml;
		yml = new YamlReader(new FileReader("configuration/config.yml"));
		Object object = yml.read();
		Map map = (Map) object;
		Map map2 = (Map) map.get("Agents");
		Map<String,String> map3 = (Map<String, String>) map2.get("testingDB2");
		System.out.println(map3.get("userName"));
		System.out.println(map3.get("databasName"));

	}

}
