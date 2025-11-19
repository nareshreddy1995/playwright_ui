package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

	private Page page;

	// 1. String Locators - OR

	private String emailId = "//input[@id='input-email']";
	private String password = "//input[@id='input-password']";
	private String loginBtn = "//input[@type='submit']";
	private String forgotpwdLink = "(//a[text()='Forgotten Password'])[1]";
	private String logoutLink = "//a[@class='list-group-item' and normalize-space()='Logout']";

	// 2.page constructor

	public LoginPage(Page page) {
		this.page = page;
	}

	// 3. page actions/methods

	public String getLoginPageTitle() {
		return page.title();

	}

	public boolean isForgotpwdLinkExist() {
		return page.isVisible(forgotpwdLink);
	}

	public boolean doLogin(String appUserName, String apppassword) {

		page.fill(emailId, appUserName);
		page.fill(password, apppassword);
		page.click(loginBtn);
		if (page.isVisible(logoutLink)) {
			System.out.println("user is logged in successfully..");
			return true;

		}
		return false;
	}

}
