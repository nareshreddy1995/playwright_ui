package com.qa.opencart.Listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.microsoft.playwright.Page;
import com.qa.Factory.opencart.PlaywrightFactory;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class TestListener extends BaseTest implements ITestListener {

	protected Page page;

	public void onTestFailure(ITestResult result) {

		try {
			// Get page from factory (ThreadLocal safe)
			Page page = PlaywrightFactory.getPage();

			// Take screenshot
			byte[] screenshot = page
					.screenshot(new Page.ScreenshotOptions().setFullPage(AppConstants.SCREENSHOT_FULL_PAGE));

			// Create folder path if not exists
			Path folderPath = Paths.get(AppConstants.FAILED_SCREENSHOTS_PATH);
			Files.createDirectories(folderPath);

			// Create screenshot file name
			String fileName = result.getName() + "_" + System.currentTimeMillis() + ".png";

			// Full file path
			Path filePath = folderPath.resolve(fileName);

			// Write screenshot
			Files.write(filePath, screenshot);

			System.out.println("Screenshot saved at: " + filePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
