package com.book.pageObjClass;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddBookStorePage extends BasePageObject{

	private By 	 bookListsLocator= By.xpath("//div[@id='title-wrapper']//label[@id='userName-value']");
	private By addCollectionButton=By.xpath("(//button[normalize-space()='Add To Your Collection'])[1]");

	public AddBookStorePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	public String getBookNameAddBookPage() {
		waitForVisibilityOf(bookListsLocator, Duration.ofSeconds(8));
		String bookName	=find(bookListsLocator).getText();
		return bookName;
	}
	public void clickAddCollectionButtom() {
		//waitForVisibilityOf(addCollectionButton, Duration.ofSeconds(8));
		ScrollIntoViewAndClick(addCollectionButton);
		
	}
	public String AlertText() throws InterruptedException {
	
		Alert alert=switchAlert();
		String alertText=getAlertText();
		acceptAlert(alert);
		return alertText;
	}
	
}
