package automationframework;
/**
 * @author Shubham Jain
 *
 */
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class WaitFor 
{
    public static WebElement presenceOfTheElement(WebDriver driver, final By elementIdentifier) 
    {
        // Wait time and polling time get it from global configuration.
        String globalPageTimeoutProperty = Configuration.getConfigurationValueForProperty("global-page-timeout");
        long globalPageTimeout = Long.parseLong(globalPageTimeoutProperty);

        String globalPageElementPollingTimeoutProperty = Configuration.getConfigurationValueForProperty("global-page-element-polling-timeout");
        long globalPageElementPollingTimeout = Long.parseLong(globalPageElementPollingTimeoutProperty);

        Wait<WebDriver> wait =
                new FluentWait<WebDriver>(driver).withTimeout(globalPageTimeout, TimeUnit.SECONDS)
                                                 .pollingEvery(globalPageElementPollingTimeout, TimeUnit.SECONDS)
                                                 .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>()
                {
                    public WebElement apply(WebDriver driver) {
                           return driver.findElement(elementIdentifier);
                    }
                });
    }

    public static void waitForPageToLoad(WebDriver driver, final String textToBePresent, final By elementToBePresent)
    {
        ExpectedCondition < Boolean > pageLoad = new ExpectedCondition < Boolean > () 
        {
            public Boolean apply(WebDriver driver)
            {
                boolean isPageLoaded = isloadComplete(driver);
                if (!isPageLoaded)
                    return false;

                try
                {
                    String actext = driver.findElement(elementToBePresent).getText();
                    if (textToBePresent.equals(actext))
                        return true;
                }
                catch (Exception e)
                {
                    return false;
                }
                return false;
            }
        };

        waitUntil(driver, pageLoad);
    }

    public static void ElementToBeDisplayed(WebDriver driver, final By elementToBePresent)
    {
        ExpectedCondition < Boolean > pageLoad = new ExpectedCondition < Boolean > () 
        {
            public Boolean apply(WebDriver driver)
            {
                boolean isPageLoaded = isloadComplete(driver);
                if (!isPageLoaded)
                    return false;

                try
                {
                	boolean val = driver.findElement(elementToBePresent).isDisplayed();
                    if(val==true)
                    	return true;
                }
                catch (Exception e)
                {
                    return false;
                }
                return false;
            }
        };

        waitUntil(driver, pageLoad);
    }
    public static void waitForPageToLoad(WebDriver driver)
    {
        ExpectedCondition<Boolean> pageLoad = new ExpectedCondition<Boolean>()
        {
            @Override
            public Boolean apply(WebDriver driver)
            {
                return isloadComplete(driver);
            }
        };

        waitUntil(driver, pageLoad);
    }

    private static void waitUntil(WebDriver driver, ExpectedCondition < Boolean > pageLoad)
    {
        String globalPageTimeoutProperty = Configuration.getConfigurationValueForProperty("global-page-timeout");
        long globalPageTimeout = Long.parseLong(globalPageTimeoutProperty);

        Wait < WebDriver > wait = new WebDriverWait(driver, globalPageTimeout);
        try
        {
            wait.until(pageLoad);
        }
        catch (Throwable pageLoadWaitError)
        {
            AutomationLog.info("Timeout during page load");
        }
    }

    private static boolean isloadComplete(WebDriver driver)
    {
        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("loaded")
                || ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    }

    public static void waitUntilElementIsLoaded(WebDriver driver, By elementLocator)
    {
        Integer elementwaitTimeout = Integer.parseInt(Configuration.getConfigurationValueForProperty("global-page-element-polling-timeout"));
        WebDriverWait wait = new WebDriverWait(driver, elementwaitTimeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
    }

    public static void waitUntilElementIsLoaded(WebDriver driver, WebElement element)
    {
        Integer elementwaitTimeout = Integer.parseInt(Configuration.getConfigurationValueForProperty("global-page-element-polling-timeout"));
        WebDriverWait wait = new WebDriverWait(driver, elementwaitTimeout);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void sleepFor(int millis) throws InterruptedException
    {
        Thread.sleep(millis);
    }
}
