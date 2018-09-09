package pageobjects;
/**
 * @author Shubham Jain
 * */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import automationframework.Configuration;


public class Page 
{
    public static WebDriver driver;

    public Page(WebDriver driver)
    {
        Page.driver = driver;
		PageFactory.initElements(driver, this);
    }

    protected String applicationUrl()
    {
        String protocol = "http://";
        if(isSecured())
        {
            protocol = "https://";
        }
        return (protocol + Configuration.getConfigurationValueForProperty("applicationURL"));
    }

    protected boolean isSecured()
    {
        return false;
    }

/*    public static Header header()
    {
        return PageFactory.initElements(driver, Header.class);
    }

    public static Footer footer() 
    {
        return PageFactory.initElements(driver, Footer.class);
    }

    public static ContentPagesLeftMenu contentPagesLeftMenu()
    {
        return PageFactory.initElements(driver, ContentPagesLeftMenu.class);
    }*/

    public String currentURL() throws Exception
    {
        return driver.getCurrentUrl();
    }

    public String currentPageTitle() throws Exception
    {
        return driver.getTitle();
    }

    protected String getTextfromElement(WebElement element) throws Exception
    {
        return element.getText();
    }

/*    public static SubNavigation subNavigation()
    {
        return PageFactory.initElements(driver, SubNavigation.class);
    }*/

    public String pageHeading() throws Exception
    {
        // TODO: To throw Expection for pages that do not have Page Headings 
        return "";
    }
}
