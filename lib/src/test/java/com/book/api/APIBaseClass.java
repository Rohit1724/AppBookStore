package com.book.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class APIBaseClass {

	/*
	 * get API with out Token
	 */
	public Response getBookAPIMethod(String url,String apiUrl)  {

		RestAssured.baseURI=url;
		RequestSpecification http=RestAssured.given();
		Response res=http.request(Method.GET,apiUrl);
		JsonPath jsonPathEvaluator = res.jsonPath();
		return res;
	}	
	/*
	 * Any Post Api with Out Token
	 */
	public Response postBookAPIMethod(String url,String apiUrl,String user,String password)  {

		RestAssured.baseURI =url; 
		RequestSpecification http = RestAssured.given(); 
		http.given().headers("Content-Type","application/json");
		JSONObject requestParams = new JSONObject(); 
		requestParams.put("userName", user); 
		requestParams.put("password", password); 
		http.body(requestParams.toJSONString()); 
		Response response = http.request(Method.POST,apiUrl); 
		ResponseBody body = response.getBody(); 
		return response;
	}	
	/*
	 * generate new token id
	 */
	public String generateTokenAPIMethod(String url,String tokenApiUrl,String user,String password)  {

		RestAssured.baseURI =url; 
		RequestSpecification http = RestAssured.given(); 
		http.given().headers("Content-Type","application/json");
		JSONObject requestParams = new JSONObject(); 
		requestParams.put("userName", user); 
		requestParams.put("password", password); 
		http.body(requestParams.toJSONString()); 
		Response response = http.request(Method.POST,tokenApiUrl);
		ResponseBody body = response.getBody(); 
		JsonPath jsonPathEvaluator = response.jsonPath();
		String id=jsonPathEvaluator.getString("token");
		return id;

	}
	/*
	 * get API with out Token
	 */
	public Response getBookWithTokenAPIMethod(String url,String apiUrl,String token)  {

		RestAssured.baseURI=url;
		RequestSpecification http=RestAssured.given();
		http.given().header("Authorization", token).header("Content-Type", "application/json");
		Response res=http.request(Method.GET,apiUrl);
		JsonPath jsonPathEvaluator = res.jsonPath();
		return res;
	}
	/*
	 *  Delete Book with user
	 */
	public Response deleteBookAPIMethod(String url,String apiUrl,String token,String isbn,String userId)  {

		RestAssured.baseURI=url;
		RequestSpecification http=RestAssured.given();
		http.given().header("Authorization", token).header("Content-Type", "application/json");
		JSONObject requestParams = new JSONObject(); 
		requestParams.put("isbn", isbn); 
		requestParams.put("userId", userId); 
		http.body(requestParams.toJSONString());
		Response res=http.request(Method.DELETE,apiUrl);
		JsonPath jsonPathEvaluator = res.jsonPath();
		return res;
	}
	/*
	 *  Add book Collection
	 */
	public Response AddBookToCollection(String url,String apiUrl,String token,String isbn,String userId)  {

		RestAssured.baseURI=url;
		RequestSpecification http=RestAssured.given();
		http.given().header("Authorization", token).header("Content-Type", "application/json");
		JSONObject requestSubParams = new JSONObject();
		requestSubParams.put("isbn", isbn);
		JSONArray arr=new JSONArray();
		arr.add(requestSubParams);
		JSONObject requestParams = new JSONObject(); 
		requestParams.put("userId", userId); 
		requestParams.put("collectionOfIsbns", arr); 
		http.body(requestParams.toJSONString());
		Response res=http.request(Method.POST,apiUrl);
		JsonPath jsonPathEvaluator = res.jsonPath();
		return res;
	}
}
