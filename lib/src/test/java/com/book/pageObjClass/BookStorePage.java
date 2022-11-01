package com.book.pageObjClass;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BookStorePage extends BasePageObject{

	private By logOutButton = By.xpath("(//button[normalize-space()='Log out'])[1]");
	private By 	 bookListsLocator= By.xpath("//div[@class='rt-tr-group'][1]//div[@class='action-buttons']/span/a");


	public BookStorePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public boolean isLogOutButtonVisible() {
		waitForVisibilityOf(logOutButton, Duration.ofSeconds(10));
		return find(logOutButton).isDisplayed();
	}
	public void clickLogOut() {
		waitForVisibilityOf(logOutButton, Duration.ofSeconds(10));
		find(logOutButton).click();
	}
	public void clickFirstBook() {
		waitForVisibilityOf(bookListsLocator, Duration.ofSeconds(8));
		ScrollIntoViewAndClick(bookListsLocator);
	}

	public String getBookName() {
		waitForVisibilityOf(bookListsLocator, Duration.ofSeconds(8));
		String bookName	=find(bookListsLocator).getText();
		return bookName;
	}
}
