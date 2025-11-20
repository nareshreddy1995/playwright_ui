package com.qa.opencart.extentReports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {

	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	public static void setExtentTest(ExtentTest test) {
		extentTest.set(test);

	}

	public static ExtentTest getTest() {
		return extentTest.get();

	}
}
