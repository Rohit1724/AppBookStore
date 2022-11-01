package com.book.baseClass;

import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserDriverFactory {
	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private String browser;
	//private Logger log;

	public BrowserDriverFactory(String browser) {
		this.browser = browser.toLowerCase();
		//this.log = log;
	}

	public WebDriver createDriver() {
		//log.info("Create local driver: " + browser);

		switch (browser) {
		case "chrome":
			// initialization Driver as chrome
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
			break;

		case "firefox":
			// initialization Driver as firefox
			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
			break;

		default:
			// initialization Driver as chrome
			
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
			break;
		}
		//java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
		return driver.get();
	}
}
