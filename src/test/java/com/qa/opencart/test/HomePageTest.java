package com.qa.opencart.test;

import org.testng.Assert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class HomePageTest extends BaseTest {

	@org.testng.annotations.Test
	public void homePageTest() {
		String actualTitle = homePage.getHomePageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);

	}

	@org.testng.annotations.Test
	public void homepageURLTest() {
		String actualpageUrl = homePage.HomePageUrl();
		Assert.assertEquals(actualpageUrl,prop.getProperty("url"));
	}

	public Object[][] getproductData() {
		return new Object[][] {

				{ "Macbook" }, { "oneplus" }, { "Samsung" }

		};
	}

	@org.testng.annotations.Test(dataProvider = "getproductData")
	public void searchTest() {

		String actualSearchHeader = homePage.doSearch("Macbook");
		Assert.assertEquals(actualSearchHeader, "Search - mac book");
	}

}
