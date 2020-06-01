/**
 * @author : Zubin/Preeti/Srinidhi
 * @see : This is the Driver class 
 * @since : April 2020
 **/
package com.sapient.pps.utility;

public enum Driver {

	CHROME("CHROME", "webdriver.chrome.driver","./Webdriver/chromedriver.exe");

	private final String name;
	private final String driver;
	private final String path;

	private Driver(String name, String driver, String path) {
		this.name = name;
		this.driver = driver;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public String getDriver() {
		return driver;
	}

	public String getPath() {
		return path;
	}

}