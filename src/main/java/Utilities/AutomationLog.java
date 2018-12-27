package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AutomationLog{
	
	private AutomationLog() {
		
	}
	public static Logger logger = LogManager.getLogger("Confirmation Automation");

	 private static Logger getLogger(){
		 return AutomationLog.logger;
	 }
		 
	public static void info(String string) {
		getLogger().info(string);
	}

	public static void error(String message) {
		getLogger().error(message);
	}
	
	public static void warn(String message) {
		getLogger().warn(message);
	}
	
	public static void debug(String message) {
		getLogger().debug(message);
	}
	
	public static void error(String message,Throwable throwable) {
		getLogger().error(message,throwable);
	}
	
}

