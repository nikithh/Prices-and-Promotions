/**
 * @author Zubin/Preeti/Srinidhi
 * @see This class will load the given driver, and follows factory pattern 
 * @since April-2020 
 */
package com.sapient.pps.utility;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static WebDriver driver; 
    private static ChromeOptions options=new ChromeOptions();
    
    public static WebDriver getDriver(String driverName){
    	options.setHeadless(true);
        
        switch(driverName)
        {
        case "CHROME": 
            System.setProperty(Driver.CHROME.getDriver(), Driver.CHROME.getPath());
            driver = new ChromeDriver();
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }
}