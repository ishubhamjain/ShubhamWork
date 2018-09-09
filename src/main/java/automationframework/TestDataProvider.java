package automationframework;
/**
 * @author Shubham Jain
 *
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class TestDataProvider 
{
    private static String TESTDATA_FILE_EXTENSION = ".csv";

    public static HashMap<String, HashMap<String, String>> getTestData(String testFilename)
    {
        String testDataDir = Configuration.getConfigurationValueForProperty("test-data-directory");
        String testDataFilePath = testDataDir + testFilename + TESTDATA_FILE_EXTENSION;

        HashMap<String, HashMap<String, String>> testData = new HashMap<String, HashMap<String,String>>();
        parseTestData(testDataFilePath, testData);

        return testData;
    }

    private static void parseTestData(String testDataFilePath, HashMap<String, HashMap<String, String>> testData)
    {
        BufferedReader fileReader = null;
        //Create the file reader
        try
        {
            fileReader = new BufferedReader(new FileReader(testDataFilePath));
            convertCSVFileDataToHashMap(fileReader, testData);
        }
        catch(FileNotFoundException fnfe)
        {
        	AutomationLog.info("Not able to read config file due to " + fnfe.toString() + " test data loading failed.");
        }
        catch (Exception e) 
        {
            AutomationLog.info("Not able to read config file due to " + e.toString() + " test data loading failed.");
        }
        finally
        {
            try
            {
                if (fileReader != null)
                {
                    fileReader.close();
                }
            }
            catch (IOException e) 
            {
                AutomationLog.info("Not able to close config file due to " + e.toString() + " test data might not have loaded correctly.");
            }
        }
    }

    private static void convertCSVFileDataToHashMap(BufferedReader fileReader, 
            HashMap<String, HashMap<String, String>> testData) throws IOException
    {
        String line = null;

        while ((line = fileReader.readLine()) != null)
        {
            // we don't want to parse comments and blank empty line.
            if (!isValidCSVLine(line))
                continue;

            addToHashMap(line, testData);
         }
     }

    private static void addToHashMap(String csvLine, HashMap<String, HashMap<String, String>> testData)
    {
        // TODO: Check if these can be moved to global config. check first if it makes sense to move
        // it in config.
        final String KEY_DELIMITER = ",";
        final String RECORD_DELIMITER = ";";
        final String VALUE_DELIMITER = "=";
        final int KEY = 0;
        final int VALUE = 1;

        //Get all keys available in line
        String[] keys = parseCSVLine(csvLine, KEY_DELIMITER);
        if (keys.length > 0)
        {
            HashMap<String,String> data = new HashMap<String, String>();
            String[] records = parseCSVLine(keys[1], RECORD_DELIMITER);
            for(String recordData : records)
            {
                  String [] datavalues = parseCSVLine(recordData, VALUE_DELIMITER);
                  if (datavalues.length > 0)
                  {
                      data.put(new String(datavalues[KEY]), new String(datavalues[VALUE]));
                  }
            }
            testData.put(new String(keys[0]), data);
        }
    }

    private static boolean isValidCSVLine(String csvLine)
    {
        final String COMMENT_CHARACTER= "#";
        if (csvLine != null && !csvLine.startsWith(COMMENT_CHARACTER) && csvLine.length() > 0)
        {
            return true;
        }

        return false;
    }

    /**
     * parseCSVLine function uses regex to skip delimiters that occur inside double quotes.
     * The first matching group will match a quote, then carry that to the end of the match.
     * so that we are assured to capture the entire value between but not including the quotes.
     * we also don't capture the commas unless they were embedded a quote delimited substring.
     *
     * Regex pattern explanation for CSV parsing with double quotes and delimiters.
     *
     * ^ = The beginning of a line.
     * ? =  Once or not at all.
     * * = zero or more.
     * [^\�]* = Any character except double quotes. zero or more time.
     * \" = double quote character.
     * () = create capture group.
     * ?! = Negative lookahead - Assert that it's impossible to match the following regex.
     * (?=  start zero length assertion look ahead.
     *
     * @param line String on which regex needs to be applied.
     * @param delimiter delimeter that we use to split the line into tokens.
     */
    public static String[] parseCSVLine(String line, String delimiter) {
        // Create a pattern to match breaks
        Pattern p = Pattern.compile(delimiter + "(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");
        // Split input with the pattern
        String[] fields = p.split(line);
        for (int i = 0; i < fields.length; i++) {
            // Get rid of residual double quotes
            fields[i] = fields[i].replace("\"", "");
            // Replace newline character.
            fields[i] = fields[i].replace("\\n", "\n");
        }
        return fields;
    }
}
