package winiumPOC;

import java.io.IOException;

public class kill {

	public static void main(String[] args) throws IOException {
	/*	Runtime.getRuntime().exec("taskkill /F /IM Winium.Desktop.Driver");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");*/
	/*	Runtime.getRuntime().exec("taskkill /F /IM conhost.exe");
		Runtime.getRuntime().exec("taskkill /F /IM Winium.Desktop.Driver.exe");*/
		
	      Runtime.getRuntime().exec("taskkill /F /IM calc.exe");
	      Runtime.getRuntime().exec("taskkill /F /IM calc");
	        Runtime.getRuntime().exec("taskkill /F /IM Calculator");
	        Runtime.getRuntime().exec("taskkill /F /IM Calculator.exe");
		
		

	}

}
