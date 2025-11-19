package com.qa.opencart.Utils;

import java.nio.file.Paths;
import java.util.List;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

public class ElementsUtils {

	private Page page;

	public ElementsUtils(Page page) {
		this.page = page;
	}

	/* --------------------- WAIT UTILS ----------------------- */

	public void waitForVisibility(String locator) {
		page.waitForSelector(locator, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
	}

	public void waitForClickable(String locator) {
		page.waitForSelector(locator, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.ATTACHED));
	}

	public void waitForHidden(String locator) {
		page.waitForSelector(locator, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
	}

	public void waitForURLContains(String urlPart) {
		page.waitForURL("**" + urlPart + "**");
	}

	public void waitForLoadState() {
		page.waitForLoadState(LoadState.NETWORKIDLE);
	}

	/* --------------------- BASIC ACTIONS ----------------------- */

	public void click(String locator) {
		waitForVisibility(locator);
		page.click(locator);
	}

	public void type(String locator, String value) {
		waitForVisibility(locator);
		page.fill(locator, value);
	}

	public void clear(String locator) {
		page.fill(locator, "");
	}

	public String getText(String locator) {
		waitForVisibility(locator);
		return page.textContent(locator).trim();
	}

	public List<String> getTexts(String locator) {
		return page.locator(locator).allTextContents();
	}

	public boolean isVisible(String locator) {
		try {
			return page.isVisible(locator);
		} catch (Exception e) {
			return false;
		}
	}

	public int getCount(String locator) {
		return page.locator(locator).count();
	}

	/* --------------------- SELECT / DROPDOWN ----------------------- */

	public void selectByValue(String locator, String value) {
		page.selectOption(locator, value);
	}

	public void selectByIndex(String locator, int index) {
		page.selectOption(locator, new SelectOption().setIndex(index));
	}

	public void selectByText(String locator, String text) {
		page.selectOption(locator, new SelectOption().setLabel(text));
	}

	/* --------------------- KEYBOARD & MOUSE ----------------------- */

	public void pressEnter(String locator) {
		page.press(locator, "Enter");
	}

	public void pressTab(String locator) {
		page.press(locator, "Tab");
	}

	public void hover(String locator) {
		page.hover(locator);
	}

	public void doubleClick(String locator) {
		page.dblclick(locator);
	}

	public void rightClick(String locator) {
	    page.click(locator, new Page.ClickOptions().setButton(MouseButton.RIGHT));
	}

	public void dragAndDrop(String from, String to) {
		page.dragAndDrop(from, to);
	}

	/* --------------------- CHECKBOX / RADIO ----------------------- */

	public void check(String locator) {
		if (!page.isChecked(locator)) {
			page.check(locator);
		}
	}

	public void uncheck(String locator) {
		if (page.isChecked(locator)) {
			page.uncheck(locator);
		}
	}

	public void selectRadio(String locator) {
		page.check(locator);
	}

	/* --------------------- ALERTS ----------------------- */

	public String handleAlertAccept() {
		final String[] message = { "" };
		page.onceDialog(dialog -> {
			message[0] = dialog.message();
			dialog.accept();
		});
		return message[0];
	}

	public String handleAlertDismiss() {
		final String[] message = { "" };
		page.onceDialog(dialog -> {
			message[0] = dialog.message();
			dialog.dismiss();
		});
		return message[0];
	}

	/* --------------------- JS ACTIONS ----------------------- */

	public Object jsClick(String locator) {
		return page.evaluate("el => el.click()", page.querySelector(locator));
	}

	public Object jsSetValue(String locator, String value) {
		return page.evaluate("el => el.value = '" + value + "'", page.querySelector(locator));
	}

	public void scrollToBottom() {
		page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollIntoView(String locator) {
		page.locator(locator).scrollIntoViewIfNeeded();
	}

	/* --------------------- FILE UPLOAD ----------------------- */

	public void uploadFile(String locator, String filePath) {
		page.setInputFiles(locator, Paths.get(filePath));
	}

	/* --------------------- FRAMES ----------------------- */

	public Frame switchToFrame(String frameName) {
		return page.frame(frameName);
	}

	public void frameClick(String frameName, String locator) {
		Frame frame = switchToFrame(frameName);
		frame.click(locator);
	}

	/* --------------------- NAVIGATION ----------------------- */

	public void navigateTo(String url) {
		page.navigate(url);
	}

	public void back() {
		page.goBack();
	}

	public void forward() {
		page.goForward();
	}

	/* --------------------- ASSERTIONS ----------------------- */

	public boolean assertEquals(String actual, String expected) {
		return actual.equals(expected);
	}

	public boolean assertTrue(boolean condition) {
		return condition;
	}

	/* --------------------- SCREENSHOTS ----------------------- */

	public void takeScreenshot(String filePath) {
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));
	}

	public byte[] screenshotAsBytes() {
		return page.screenshot();
	}
}
