package com.qa.opencart.Listeners;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Page;
import com.qa.Factory.opencart.PlaywrightFactory;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.extentReports.ExtentManager;
import com.qa.opencart.extentReports.ExtentTestManager;

public class TestListener extends BaseTest implements ITestListener {

	private static ExtentReports extent;
	private static ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

	public static void setPage(Page page) {
		pageThreadLocal.set(page);
	}

	protected Page page;

	@Override
	public void onStart(ITestContext context) {
		extent = ExtentManager.createInstance("reports/extent-report.html");
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTestManager.setExtentTest(extent.createTest(result.getName()));
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTestManager.getTest().log(Status.PASS, AppConstants.TEST_PASSED);
	}

	@Override
	public void onTestFailure(ITestResult result) {

		ExtentTestManager.getTest().log(Status.FAIL, AppConstants.TEST_FAILED);
		ExtentTestManager.getTest().fail(result.getThrowable());

		try {
			Page page = pageThreadLocal.get();

			if (page != null) {
				String screenshotPath = "screenshots/" + result.getName() + ".png";

				page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath))
						.setFullPage(AppConstants.FULL_SCREEN_TRUE));

				ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public void onTestFailure1(ITestResult result) {
//
//		try {
//			// Get page from factory (ThreadLocal safe)
//			Page page = PlaywrightFactory.getPage();
//
//			// Take screenshot
//			byte[] screenshot = page
//					.screenshot(new Page.ScreenshotOptions().setFullPage(AppConstants.SCREENSHOT_FULL_PAGE));
//
//			// Create folder path if not exists
//			Path folderPath = Paths.get(AppConstants.FAILED_SCREENSHOTS_PATH);
//			Files.createDirectories(folderPath);
//
//			// Create screenshot file name
//			String fileName = result.getName() + "_" + System.currentTimeMillis() + ".png";
//
//			// Full file path
//			Path filePath = folderPath.resolve(fileName);
//
//			// Write screenshot
//			Files.write(filePath, screenshot);
//
//			System.out.println("Screenshot saved at: " + filePath);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

}
