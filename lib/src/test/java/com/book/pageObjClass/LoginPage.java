package com.book.pageObjClass;


import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePageObject {

	// TODO Auto-generated method stub
	private By loginButtonLocator = By.id("login");
	private By usernameLocator = By.id("userName");
	private By passwordLocator = By.id("password");
	private By submitButtonLocator = By.xpath("(//button[normalize-space()='Login'])[1]");
	private By elementsLocator = By.xpath("//h5[normalize-space()='Elements']");
	private By bookStoreAppLocator = By.xpath("//h5[normalize-space()='Book Store Application']");
	

	private String url ="https://demoqa.com";

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public LoginPage open() {
		driver.get(url);
		return this;
	}


	/** Execute log in */
	public BookStorePage logIn(String username, String password) {
		executeLogin(username, password);
		return new BookStorePage(driver);
	}


	public void negativeLogIn(String username, String password) {
		executeLogin(username, password);
	}


	private void executeLogin(String username, String password) {
		ScrollIntoViewAndClick(bookStoreAppLocator);
		click(loginButtonLocator);
		type(username, usernameLocator);
		type(decodePassword(password), passwordLocator);
		ScrollIntoViewAndClick(submitButtonLocator);
		//click(submitButtonLocator);
	}

	
	
	/** Wait for Test to be visible on the page */
	public void waitForElementText() {
		waitForVisibilityOf(elementsLocator);
	}

	public void getPageUrl() throws InterruptedException {
	String currentUrl=	getCurrentUrl();
	if(currentUrl.contains("https://demoqa.com/books")) {
		driver.navigate().to("https://demoqa.com/profile");
		Thread.sleep(6000);
	}
	}
	
	public String getErrorMessageText() {
		return find(elementsLocator).getText();
	}
	
	/** Decode the String */
	public static String decodePassword(String password) {
		byte[] decodedString=Base64.decodeBase64(password);
		String value=new String(decodedString);
		return value;
		
	}
	/** Encode the String */
	public static String encodePassword(String password) {
		
		byte[] encodedString=Base64.encodeBase64(password.getBytes());
		return new String(encodedString);
		
	}
}


