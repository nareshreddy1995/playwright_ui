package com.qa.Factory.opencart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {

	Playwright playwright;
	Browser browser;
	BrowserContext browserContext;
	Page page;

	Properties properties;

	private static ThreadLocal<Browser> tlbrowser = new ThreadLocal<>();
	private static ThreadLocal<BrowserContext> tlbrowserContext = new ThreadLocal<>();
	private static ThreadLocal<Page> tlpage = new ThreadLocal<>();
	private static ThreadLocal<Playwright> tlplaywright = new ThreadLocal<>();

	public static Playwright getPlaywright() {
		return tlplaywright.get();
	}

	public static Browser getBrowser() {
		return tlbrowser.get();
	}

	public static BrowserContext getBrowserContext() {
		return tlbrowserContext.get();
	}

	public static Page getPage() {
		return tlpage.get();
	}

	public Page initBrowser(Properties prop) {
		String browserName = prop.getProperty("browser").trim();

		tlplaywright.set(Playwright.create());
		// playwright = Playwright.create();

		switch (browserName.toLowerCase()) {
		case "chromium":
			// playwright.chromium().launch(new
			// BrowserType.LaunchOptions().setHeadless(false));
			tlbrowser.set(playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;

		case "firefox":
			// playwright.firefox().launch(new
			// BrowserType.LaunchOptions().setHeadless(false));
			tlbrowser.set(playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));

			break;

		case "safari":
			// playwright.webkit().launch(new
			// BrowserType.LaunchOptions().setHeadless(false));
			tlbrowser.set(playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));

			break;

		case "chrome":
			// playwright.chromium().launch(new
			// BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
			tlbrowser.set(playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false)));

			break;

		default:

			System.out.println("please pass the correct browser name...");
			break;
		}

		tlbrowserContext.set(getBrowser().newContext());
		tlpage.set(getBrowserContext().newPage());
		getPage().navigate(prop.getProperty("url").trim());
		return page;

//		browserContext = browser.newContext();
//		page = browserContext.newPage();
//		page.navigate(prop.getProperty("url").trim());

	}

	/*
	 * this method is used to intialize the properites from config file
	 */
	public Properties init_prop() throws IOException {

		try {
			FileInputStream fis = new FileInputStream("./src/test/resource/config/config.properties");
			properties = new Properties();
			properties.load(fis);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;

	}

}
