package MainRunner;

public @interface ExtendedCucumberOptions {

	String jsonReport();

	int retryCount();

	boolean detailedReport();

	boolean detailedAggregatedReport();

	boolean overviewReport();

	boolean toPDF();

	String outputFolder();

}
