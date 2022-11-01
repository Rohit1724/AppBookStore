package com.book.pageObjClass;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject {

	protected WebDriver driver;
	WebDriverWait wait;
	public BasePageObject(WebDriver driver) {
		this.driver = driver;
		
	}
	
	 
	/** Open page with given URL */
	protected void openUrl(String url) {
		driver.get(url);
	}


	/** Find element using given locator */
	protected WebElement find(By locator) {
		return driver.findElement(locator);
	}


	/** Find all elements using given locator */
	protected List<WebElement> findAll(By locator) {
		return driver.findElements(locator);
	}


	/** Click on element with given locator when its visible */
	protected void click(By locator) {
		waitForVisibilityOf(locator, Duration.ofSeconds(5));
		find(locator).click();
	}


	/** Type given text into element with given locator */
	protected void type(String text, By locator) {
		waitForVisibilityOf(locator, Duration.ofSeconds(10));
		find(locator).sendKeys(text);
	}


	/** Get URL of current page from browser */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}


	/** Get title of current page */
	public String getCurrentPageTitle() {
		return driver.getTitle();
	}


	/** Get source of current page */
	public String getCurrentPageSource() {
		return driver.getPageSource();
	}

	
	/** Scroll Down page  */
	public void ScrollIntoViewAndClick(By locator)
	{
		WebElement element = find(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
		element.click();
	}

	/**
	 * will switch to iFrame automatically
	 * 
	 * @param xpath
	 */
	public void switchFrameDynamic(String xpath) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		List<WebElement> frame = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//iframe")));
		for (int i = 0; i < frame.size(); i++) {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(i));
			if (driver.findElements(By.xpath(xpath)).size() > 0) {
				break;
			}
		}
	}

	/**
	 * will switch to iFrame automatically
	 * 
	 * @param By
	 */
	public void switchFrameDynamic(By xpath) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		List<WebElement> frame = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//iframe")));
		for (int i = 0; i < frame.size(); i++) {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(i));
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath));
				break;
			} catch (Exception e) {
				System.out.println("Element not available on frame: iFrame" + i);
			}
		}
	}

	/**
	 * Switch to frame as per index parameter
	 * 
	 * @param index of frame
	 */
	public void switchFrameindex(int i) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(i));

	}

	/**
	 * Switch to frame as per Locator of iFrame
	 * 
	 * @param Locator of frame
	 */

	public void switchFramelocator(String str) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(str));

	}

	
	public void switchDefaultFrame() {

		driver.switchTo().defaultContent();

	}
	
	
	/** Wait for specific ExpectedCondition for the given Duration */
	private void waitFor(ExpectedCondition<WebElement> condition, Duration timeOut) {
		timeOut = timeOut != null ? timeOut : Duration.ofSeconds(60);
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(condition);
	}


	/**
	 * Wait for given number of seconds for element with given locator to be visible
	 * on the page
	 */
	protected void waitForVisibilityOf(By locator, Duration... timeOut) {
		int attempts = 0;
		while (attempts < 2) {
			try {
				waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
						(timeOut.length > 0 ? timeOut[0] : null));
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
	}
	
	/** Click Delete Icon on Profile Book List */
	public void pressDeleteIcon(By locator ) {
		waitForVisibilityOf(locator, Duration.ofSeconds(10));
		find(locator).click();
		
	}
	
	/** Click ok Button on popup box in Profile Book List */
	public void pressOkButton(By locator ) {
		waitForVisibilityOf(locator, Duration.ofSeconds(10));
		find(locator).click();
		
	}
	/** Switch Alert Pop-UP Box */
	public Alert switchAlert() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.alertIsPresent());
		// Switching to Alert
		Alert alert = driver.switchTo().alert();
		return alert;
		
	}
	/** get Alert Text value  
	 * @throws InterruptedException */
	public String getAlertText() throws InterruptedException {
		Thread.sleep(2000);
		// Capturing alert message.
		String alertMessage= driver.switchTo().alert().getText();
		return alertMessage;
		
	}
	/** Accept alert 
	 * @throws InterruptedException */
	public void acceptAlert(Alert alert) throws InterruptedException {
		// Accepting alert		
		alert.accept();
		Thread.sleep(2000);
		
	}
	/** dismiss alert 
	 * @throws InterruptedException */
	public void dismissAlert(Alert alert) throws InterruptedException {
		// Accepting alert		
		alert.dismiss();
		Thread.sleep(2000);
		
	}
}
