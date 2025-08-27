package DoPrepare.Common;

import DoPrepare.Help.RecordHelp;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName()
                : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    // Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    // HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    // Screenshot attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        Log.info("Start testing " + iTestContext.getName());
        WebDriver webDriver = SetUp.getDriver(); // Lấy WebDriver từ SetUp (ThreadLocal)
        if (webDriver != null) {
            iTestContext.setAttribute("WebDriver", webDriver);
        } else {
            Log.warn("WebDriver is null in onStart");
        }

        try {
            RecordHelp.startRecord(iTestContext.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        Log.info("End testing " + iTestContext.getName());
        try {
            RecordHelp.stopRecord();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        Log.info(getTestName(iTestResult) + " test is starting...");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        Log.info(getTestName(iTestResult) + " test is passed.");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Log.error(getTestName(iTestResult) + " test is failed.");
        Log.error("Screenshot captured for test case: " + getTestName(iTestResult));
        WebDriver webDriver = SetUp.getDriver(); // Lấy WebDriver từ SetUp (ThreadLocal)
        if (webDriver != null) {
            saveScreenshotPNG(webDriver);
            saveTextLog(getTestName(iTestResult) + " failed and screenshot taken!");
        } else {
            Log.warn("WebDriver is null, skipping screenshot");
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        Log.warn(getTestName(iTestResult) + " test is skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        Log.error("Test failed but it is in defined success ratio " + getTestName(iTestResult));
    }
}