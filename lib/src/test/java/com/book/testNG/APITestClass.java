package com.book.testNG;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.book.api.APIBaseClass;
import com.book.baseClass.MyRetry;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
public class APITestClass {


	@Test(priority = 3,retryAnalyzer = MyRetry.class)
	@Description("Add Book to the Collection in Book Store App Using API")
	@Epic("Test case 3")
	@Step("Add Book in Collection and Verify in API")
	@Severity(SeverityLevel.NORMAL)
	public void AddBookToCollectionUsingAPI() throws InterruptedException {

		Random random = new Random(); 
		int y = random.nextInt(20); 
		String generatedString = RandomStringUtils.randomAlphabetic(4);
		String userName="Rohit@"+y+generatedString;
		String password="rEst@123";

		String url="https://demoqa.com";
		String apiBookURL="/BookStore/v1/Books";
		String apiUserCreateURL="/Account/v1/User";
		String tokenApiUrl="/Account/v1/GenerateToken";
		String authUserApiUrl="Account/v1/Authorized";

		APIBaseClass base=new APIBaseClass();
		/*
		 * Create New User and get User ID
		 */
		Response response=base.postBookAPIMethod(url,apiUserCreateURL,userName,password);
		JsonPath jsonPath = response.jsonPath();
		String userId=jsonPath.getString("userID");
		/*
		 * Generate Token id
		 */
		String tokenKey=base.generateTokenAPIMethod(url,tokenApiUrl,userName,password);
		String tokenID="Bearer "+tokenKey;
		/*
		 * Get Book Isbn Id
		 */
		Response bookListRespons= base.getBookAPIMethod(url,apiBookURL);
		JsonPath jsonPathBookList = bookListRespons.jsonPath();
		List<String> allBooksIsbn = jsonPathBookList.getList("books.isbn");
		String bookIsbnId=allBooksIsbn.get(0);
		/*
		 * Authorized User verify
		 */
		Response authResponse=base.postBookAPIMethod(url,authUserApiUrl,userName,password);
		String bodyAuth=authResponse.getBody().asString();
		Assert.assertEquals(bodyAuth.contains("true") , true , "User are not Authorized");
		Assert.assertEquals(authResponse.getStatusCode() , 200 , "User Authorized API not working ");
		/*
		 * Verify User has Book Add
		 */
		String userWithIDApiUrl="/Account/v1/User/"+userId;
		Response validUserBookRespons= base.getBookWithTokenAPIMethod(url,userWithIDApiUrl,tokenID);
		Assert.assertEquals(validUserBookRespons.getStatusCode() , 200 , "User has Authorized ");
		JsonPath userBookList = validUserBookRespons.jsonPath();
		List<String> userBooks = userBookList.getList("books");
		if(userBooks.size()>0) {
			// user has alrady same book then Delete first
			if(userBooks.contains(bookIsbnId)) {
				String deleteApi="/BookStore/v1/Book";
				Response deleteUserBookRespons=base.deleteBookAPIMethod(url, deleteApi, tokenID, bookIsbnId, userId);
				Assert.assertEquals(deleteUserBookRespons.getStatusCode() , 204 , "User Delete book API not working ");
			}

		}
		/*
		 *  Add book in Collection
		 */
		String addbookApi="/BookStore/v1/Books";
		Response addUserBookRespons=base.AddBookToCollection(url, addbookApi, tokenID, bookIsbnId, userId);
		Assert.assertEquals(addUserBookRespons.getStatusCode() , 201 , "Add User to Collection not Success");
		JsonPath jsonPath1 = addUserBookRespons.jsonPath();
		List<String> isbn = jsonPath1.getList("books.isbn");
		Assert.assertEquals(isbn.get(0), bookIsbnId , "Book ISBN Id Not match after Added Collection");
		
		/*
		 * Verify User Book Api either Book added in collection
		 */
		Response validAgainUserBookRespons= base.getBookWithTokenAPIMethod(url,userWithIDApiUrl,tokenID);
		JsonPath userBookList1 = validAgainUserBookRespons.jsonPath();
		List<String> userBooks1 = userBookList1.getList("books.isbn");
		Assert.assertEquals(userBooks1.contains(bookIsbnId), true , "In User Profile Book Id Not Represent");

	}
}
