/**
 * @author : Zubin/Preeti/Srinidhi
 * @see : This class is to run test cases and generate extent report 
 * @since : April 2020
 **/
package com.sapient.pps.cucumber;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/main/resources"},
		glue = {"com.sapient.pps.step"},
	    plugin = {"pretty","html:target/cucumber-html.report","json:target/cucumber-json.report","com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/finalreport9.html"},
		monochrome = true,
		tags = {"~@Sanity"}
)


public class Testing {
	@AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig("src/main/resources/extent-config.xml");
		
    }
	
}
