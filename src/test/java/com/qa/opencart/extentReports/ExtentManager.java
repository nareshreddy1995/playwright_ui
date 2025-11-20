package com.qa.opencart.extentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	public static ExtentReports reports;
	public static ExtentSparkReporter sparkReporter;

	public static ExtentReports createInstance(String fileName) {
		sparkReporter = new ExtentSparkReporter(fileName);
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Playwright Automation Report");

		reports = new ExtentReports();
		reports.attachReporter(sparkReporter);

		return reports;

	}

}
