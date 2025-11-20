package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.Listeners.TestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

@Listeners(TestListener.class)
public class HomePageTest extends BaseTest {

	@Test(priority = 1)
	public void homePageTest() {
		String actualTitle = homePage.getHomePageTitle();
		Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);

	}

	@Test(priority = 2)
	public void homepageURLTest() {
		String actualpageUrl = homePage.HomePageUrl();
		Assert.assertEquals(actualpageUrl, prop.getProperty("url"));
	}

	@DataProvider(name = "getproductData")
	public Object[][] getproductData() {
		return new Object[][] { { "Macbook" }, { "iPhone" }, { "Samsung" } };
	}

	@Test(dataProvider = "getproductData", priority = 3)
	public void searchTest(String productName) {

		System.out.println("Searching for: " + productName);

		String actualSearchHeader = homePage.doSearch("Macbook");
		Assert.assertEquals(actualSearchHeader, "Search - Macbook");
	}

}
