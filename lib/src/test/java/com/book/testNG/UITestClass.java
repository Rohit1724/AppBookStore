package com.book.testNG;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.book.baseClass.BaseTest;
import com.book.baseClass.MyRetry;
import com.book.pageObjClass.AddBookStorePage;
import com.book.pageObjClass.BookStorePage;
import com.book.pageObjClass.LoginPage;
import com.book.pageObjClass.ProfilePage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

public class UITestClass extends BaseTest {


	@Parameters({ "username", "password" })
	@Test(priority = 1, retryAnalyzer = MyRetry.class, enabled=true)
	@Description("Add Book to the Collection in Book Store App Using UI")
	@Epic("Test case 1")
	@Step("Add Book in Collection and Verify in Web Page")
	@Severity(SeverityLevel.NORMAL)
	public void AddBookToCollection(String username, String password) throws InterruptedException {
		/*
		 * for open Browser and enter URL
		 */
		LoginPage testLoginPage = new LoginPage(driver).open();
		/*
		 *  Navigate to book Store App
		 *  Enter User name and Password
		 *  Decode the Password
		 *  Enter Login Button
		 */
		
		testLoginPage.negativeLogIn(username, password);
		BookStorePage bookStorePage =new BookStorePage(driver);
		soft.assertTrue(bookStorePage.isLogOutButtonVisible(), "Logout Button is not visible.");
		testLoginPage.getPageUrl();
		ProfilePage profile=new ProfilePage(driver);
		profile.verifyBookFromProfilePage();
		profile.goToBookStoreButton();
		/*
		 * Add Book in Collection
		 */
		String bookName=bookStorePage.getBookName();
		bookStorePage.clickFirstBook();
		AddBookStorePage addPage=new AddBookStorePage(driver);
		String bookNameInAddBookPage=addPage.getBookNameAddBookPage();
		soft.assertEquals(bookName,bookNameInAddBookPage, "Selected Book or added book name not matched");
		addPage.clickAddCollectionButtom();
		String alertText=addPage.AlertText();
		String expAlertMessage="Book added to your collection.";
		soft.assertEquals(alertText,expAlertMessage, "Add Book to your collecection text not matched");
		testLoginPage.getPageUrl();
		/*
		 * verify book name in Collection
		 */
		String bookNameInProfilePage=bookStorePage.getBookName();
		soft.assertEquals(bookNameInProfilePage,bookName, "Selected book name not match in Profile");
		bookStorePage.clickLogOut();
		
	}

	@Parameters({ "username", "password" })
	@Test(priority = 2, enabled=true, retryAnalyzer = MyRetry.class )
	@Description("Add & Delete Book to the Collection in Book Store App using UI ")
	@Epic("Test case 2")
	@Step("Add & Delete Book in Collection and Verify In Web Page")
	@Severity(SeverityLevel.NORMAL)
	public void AddBookToCollectionAndDeleteBook(String username, String password) throws InterruptedException {
		/*
		 * for open Browser and enter URL
		 */
		LoginPage testLoginPage = new LoginPage(driver).open();
		/*
		 *  Navigate to book Store App
		 *  Enter User name and Password
		 *  Decode the Password
		 *  Enter Login Button
		 */
		testLoginPage.negativeLogIn(username, password);
		BookStorePage bookStorePage =new BookStorePage(driver);
		soft.assertTrue(bookStorePage.isLogOutButtonVisible(), "LogOut Button is not visible.");
		testLoginPage.getPageUrl();
		ProfilePage profile=new ProfilePage(driver);
		profile.verifyBookFromProfilePage();
		profile.goToBookStoreButton();
		/*
		 * Add Book in Collection
		 */
		String bookName=bookStorePage.getBookName();
		bookStorePage.clickFirstBook();
		AddBookStorePage addPage=new AddBookStorePage(driver);
		String bookNameInAddBookPage=addPage.getBookNameAddBookPage();
		soft.assertEquals(bookName,bookNameInAddBookPage, "Selected Book or added book name not matched");
		addPage.clickAddCollectionButtom();
		String alertText=addPage.AlertText();
		String expAlertMessage="Book added to your collection.";
		soft.assertEquals(alertText,expAlertMessage, "Add Book to your collecection text not matched");
		testLoginPage.getPageUrl();
		/*
		 * verify book name in Collection
		 */
		String bookNameInProfilePage=bookStorePage.getBookName();
		soft.assertEquals(bookNameInProfilePage,bookName, "Selected book name not match in Profile");
		/*
		 * Delete book name in Collection
		 */
		profile.deleteOneBookButton();
		profile.clickOkButtonOnPopup();
		String deleteBookText=addPage.AlertText();
		String expDeleteBookText="Book deleted.";
		soft.assertEquals(deleteBookText,expDeleteBookText, "Delete book Text not matched in Profile Alert");
		profile.verifyBookAfterDelete();
		bookStorePage.clickLogOut();
		
	}

	
}
