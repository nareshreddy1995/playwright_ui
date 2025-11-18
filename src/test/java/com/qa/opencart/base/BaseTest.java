package com.qa.opencart.base;

import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.microsoft.playwright.Page;
import com.qa.Factory.opencart.PlaywrightFactory;
import com.qa.opencart.pages.HomePage;

public class BaseTest {

	PlaywrightFactory pf;
	Page page;
	protected Properties prop;

	protected HomePage homePage;

	@BeforeTest
	public void setUp() throws IOException {

		pf = new PlaywrightFactory();
		prop=pf.init_prop();
		page = pf.initBrowser(prop);
		homePage = new HomePage(page);
	}

	@AfterTest
	public void tearDown() {
		page.context().browser().close();
	}
}
