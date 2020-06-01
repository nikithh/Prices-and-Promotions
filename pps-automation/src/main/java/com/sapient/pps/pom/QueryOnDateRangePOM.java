/**
 * @author : Zubin
 * @see : This POM is for Query On Date Range Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class QueryOnDateRangePOM {

	private WebDriver driver;

	public QueryOnDateRangePOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "username")
	private WebElement username;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(id="retailer-login-btn")
	private WebElement loginBtn;
	
	@FindBy(id="promotion-dropdown")
	private WebElement promotionBtn;
	
	@FindBy(id="query-on-date-btn")
	private WebElement queryOnDateRangeBtn;

	@FindBy(id = "app-header")
	private WebElement header;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement queryOnDateRangeModal;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[4]/h6")
	private WebElement endDateReq;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[3]/h6")
	private WebElement startDateReq;

	@FindBy(id = "query-submit")
	private WebElement showButton;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div/h1")
	private WebElement promotionsTab;

	@FindBy(id = "startDate-query")
	private WebElement startDate;

	@FindBy(id = "endDate-query")
	private WebElement endDate;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[4]/div/label[1]/span[1]")
	private WebElement zone;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[4]/div/label[2]/span[1]")
	private WebElement cluster;

	public void sendUserName(String username) {
		this.username.sendKeys(username);
		;
	}

	public void sendPassword(String password) {
		this.password.sendKeys(password);
	}

	public void clickLoginBtn() {
		this.loginBtn.click();
	}

	public void queryOnDateRangeBtn() {
		this.queryOnDateRangeBtn.click();
	}

	public String header() {
		return this.header.getText();
	}
	
	public void promotionBtn() {
		this.promotionBtn.click();
	}

	public String queryOnDateRangeModal() {
		return this.queryOnDateRangeModal.getText();
	}

	public void showButton() {
		this.showButton.click();
	}

	public String endDateReq() {
		return this.endDateReq.getText();
	}

	public String promotionsTab() {
		return this.promotionsTab.getText();
	}

	public void startDate(String startDate) {
		this.startDate.click();
		this.startDate.sendKeys(startDate);
	}

	public void endDate(String endDate) {
		this.endDate.click();
		this.endDate.sendKeys(endDate);
	}

	public void selectZone() {
		this.zone.click();
	}

	public void selectCluster() {
		this.cluster.click();
	}

}