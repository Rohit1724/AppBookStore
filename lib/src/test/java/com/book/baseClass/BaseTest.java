package com.book.baseClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;



public class BaseTest {

	// TODO Auto-generated method stub
	protected WebDriver driver;
	protected SoftAssert soft=new SoftAssert();
	@Parameters({ "browser", "environment" })
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, @Optional("chrome") String browser, @Optional("local") String environment) {
		//setProperties();
		switch (environment) {
		case "ui":
			driver = new BrowserDriverFactory(browser).createDriver();
			driver.manage().window().maximize();
			break;
		case "api":
			break;
		default:
			driver = new BrowserDriverFactory(browser).createDriver();
			driver.manage().window().maximize();
			break;
		}

	}

	private void setProperties() {
		Properties props = System.getProperties();
		try {
			props.load(new FileInputStream(new File("src/main/resources/test.properties")));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		soft.assertAll();
		driver.quit();
	}

	@AfterSuite(alwaysRun = true)
	public void allureReport() throws IOException {
		String currentDir = System.getProperty("user.dir");
		System.out.println("Current dir using System:" + currentDir+"\\allure-results");
		String value="allure serve "+ currentDir+"\\allure-results"; 
		ProcessBuilder builder = new ProcessBuilder(
				"cmd.exe", "/c", value);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		r.close();
		
	}
}
