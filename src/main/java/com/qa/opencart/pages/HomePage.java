package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class HomePage {

	Page page;

	// 1. Locators..

	private String search = "input[name='search']";
	private String searchIcon = "div#search button";
	private String searchpaheHeader = "div#content h1";
	private String loginLink = "//a[text()='Login']";
	private String myAccountLink = "//span[text()='My Account']";

	// 2. page Constructor

	public HomePage(Page page) {

		this.page = page;

	}

	// 3. page action methods..

	public String getHomePageTitle() {

		String title = page.title();
		System.out.println("page title :" + title);
		return title;

	}

	public String HomePageUrl() {

		String url = page.url();
		System.out.println("page url :" + url);
		return url;

	}

	public String doSearch(String productName) {

		page.fill(searchIcon, productName);
		page.click(productName);
		String header = page.textContent(searchpaheHeader);
		System.out.println("Search Header :" + header);
		return header;

	}

	public LoginPage navigateToLoginPage() {
		page.click(myAccountLink);
		page.click(loginLink);
		return new LoginPage(page);
	}
}
