package automationframework;

import Utilities.Configuration;

/**
 * @author Shubham Jain
 *
 */
public class AutomationFramework 
{
    public static void initWithGlobalConfiguration(String globalConfigureationFileWithPath)
    {
        Configuration.globalConfiguration().setGlobalConfigurationFile(globalConfigureationFileWithPath);
    }

}
