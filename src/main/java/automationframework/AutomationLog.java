package automationframework;
/**
 * @author Shubham Jain
 *
 */
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class AutomationLog 
{
    private Logger logger = Logger.getLogger(AutomationLog.class.getSimpleName());
    private static AutomationLog automationLogInstance = null;
    private static FileHandler fh;  
    private AutomationLog()
    {
        logger.setUseParentHandlers(false);
        LogFormatter formatter = new LogFormatter();
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
 
        logger.addHandler(handler);
        
        ////////////// Adding Log File /////////////////
        
     // This block configure the logger with handler and formatter  
        try {
			fh = new FileHandler(Configuration.getConfigurationValueForProperty("LogFile"));
        }
         catch (Exception e) {
           System.out.println(e.getMessage());
		}  
        logger.addHandler(fh);  
        fh.setFormatter(formatter);  

        // the following statement is used to log any messages  
        logger.info(handler.toString());  
        
        /////////////// End File logger ///////////////////
    }

    private static AutomationLog getAutomationLog()
    {
        if (automationLogInstance == null)
        {
            automationLogInstance = new AutomationLog();
        }
        return automationLogInstance;
    }
    
    private static Logger getLogger()
    {
        return AutomationLog.getAutomationLog().logger;
    }

    public static void startTestCase(String sTestCaseName)
    {
        String logMessgaeFormat = formatMessageWithInvocationInformation("Begin Test case : " + sTestCaseName);
        getLogger().info(logMessgaeFormat);
    }

    public static void endTestCase(String sTestCaseName)
    {
        String logMessgaeFormat = formatMessageWithInvocationInformation("End Test case : " + sTestCaseName +"\n");
        getLogger().info(logMessgaeFormat);
    }

    public static void info(String message)
    {
        String logMessgaeFormat = formatMessageWithInvocationInformation(message);
        getLogger().info(logMessgaeFormat);
    }

    public static void warn(String message) 
    {
        String logMessgaeFormat = formatMessageWithInvocationInformation(message);
        getLogger().warning(logMessgaeFormat);
    }

    public static void error(String message) 
    {
        String logMessgaeFormat = formatMessageWithInvocationInformation(message);
        getLogger().severe(logMessgaeFormat);
    }

    public static void fatal(String message) 
    {
        String logMessgaeFormat = formatMessageWithInvocationInformation(message);
        getLogger().severe(logMessgaeFormat);
    }

    private static String formatMessageWithInvocationInformation(String message)
    {
        // TODO: While running on some virtual machines, VM might have turned off Permission for 
        // Getting stack trace. Add checks to make sure we do this only if VM allows.

        // 0 is getStackTrace, 1 is log methods of this class, 2 is this method and 3 is invoking method.
        int stackTraceDepth = 3;
        String className = Thread.currentThread().getStackTrace()[stackTraceDepth].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[stackTraceDepth].getMethodName();
        String simpleClassName = className.substring(className.lastIndexOf(".")+1, className.length());
        return "[ " + simpleClassName + " : " + methodName + " ] " +  message;
     }

    class LogFormatter extends Formatter
    {
        // TODO: Get the format from global config file.
        // Create a DateFormat to format the logger timestamp.
        private final DateFormat df = new SimpleDateFormat(Configuration.getConfigurationValueForProperty("dateformat-logging"));

        private final String applicationName = Configuration.getConfigurationValueForProperty("applicationName");

        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder(1000);
            builder.append(df.format(new Date(record.getMillis()))).append(" - ");
            builder.append("[").append(applicationName).append("] - ");
            builder.append("[").append(record.getLevel()).append("] - ");
            builder.append(formatMessage(record));
            builder.append("\n");
            return builder.toString();
        }

        public String getHead(Handler h) {
            return super.getHead(h);
        }

        public String getTail(Handler h) {
            return super.getTail(h);
        }
    }

}
