package com.book.pageObjClass;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ProfilePage extends BasePageObject{

	private By logOutButton = By.xpath("(//button[normalize-space()='Log out'])[1]");
	private By bookListsLocator = By.xpath("//div[@class='rt-tr-group']//div[@class='action-buttons']/span/a");
	private By bookDeleteIconLocator = By.xpath("(//span[@id='delete-record-undefined'])[1]");
	private By bookDeleteOkButton = By.xpath("(//button[normalize-space()='OK'])[1]");
	private By goToBookStoreButton = By.xpath("(//button[normalize-space()='Go To Book Store'])[1]");
	private By deleteAllBookButton=By.xpath("(//button[@id='submit'])[3]");


	public ProfilePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	/*
	 *  Verify Book list and delete all book in profile page
	 */
	public void verifyBookFromProfilePage() throws InterruptedException {
		List<WebElement> bookList=	findAll(bookListsLocator);
		if(bookList.size()>0) {
				for(int i=0; i<bookList.size(); i++){
		   // pressDeleteIcon(bookDeleteIconLocator);
					deleteOneBookButton();
			//ScrollIntoViewAndClick(deleteAllBookButton);
			//pressOkButton(bookDeleteOkButton);
			clickOkButtonOnPopup();
			Thread.sleep(2000);
			Alert alert=switchAlert();
			getAlertText();
			acceptAlert(alert);
			}
		}
	}
	/*
	 *  click go to button on profile page
	 */
	public void goToBookStoreButton()  {
		ScrollIntoViewAndClick(goToBookStoreButton);
	}
	/*
	 *  delete one book on profile page
	 */
	public void deleteOneBookButton()  {
		pressDeleteIcon(bookDeleteIconLocator);
	}
	/*
	 *  Delete all book on profile page
	 */
	public void deleteAllBookButton()  {
		ScrollIntoViewAndClick(deleteAllBookButton);
	}
	/*
	 * click ok button on Pop Up box
	 */
	public void clickOkButtonOnPopup()  {
		pressOkButton(bookDeleteOkButton);
	}
	public void verifyBookAfterDelete() throws InterruptedException {
		List<WebElement> bookList=	findAll(bookListsLocator);
		if(bookList.size()>0) {
			Assert.assertTrue(false, "In Book List not deleted");
		}
	}
}
