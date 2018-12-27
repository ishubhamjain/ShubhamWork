package winiumPOC;

import com.test.sceanrio.application.queryutil.ConfirmationQueryUtil;

import DataBases.DataBaseSource;

public class testXMLParser {

	public static void main(String[] args) {
		ConfirmationQueryUtil objConfirmationQueryUtil = new ConfirmationQueryUtil();
		System.out.println("test");
		System.out.println(objConfirmationQueryUtil.getQuery("SELECT_ECDB_DTCC_CONFIRMS_MESSAGE"));

		
		DataBaseSource.getInstance();
	}

}
