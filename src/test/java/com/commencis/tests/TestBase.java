package com.commencis.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class TestBase {
    protected static ExtentReports report;
    //this class is used to create HTML report file
    protected ExtentHtmlReporter htmlReporter;
    //this will  define a test, enables adding logs, authors, test steps
    protected static ExtentTest extentLogger;

    @BeforeMethod
    public void setUp() {

    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        if(testResult.getStatus() == ITestResult.FAILURE){
            extentLogger.fail(testResult.getName());
            extentLogger.fail(testResult.getThrowable());
        }

    }

    @BeforeTest
    public void setUpTest(){
        report = new ExtentReports();

        //create a report path
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "/test-output/report.html";

        //initialize the html reporter with the report path
        htmlReporter = new ExtentHtmlReporter(path);

        //attach the html report to report object
        report.attachReporter(htmlReporter);

        //title in report
        htmlReporter.config().setReportName("Commencis API Test");

        //set environment information
        report.setSystemInfo("Environment","Online");
        report.setSystemInfo("OS",System.getProperty("os.name"));
    }

    @AfterTest
    public void tearDownTest(){
        report.flush();
    }
}
