
package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomListeners implements ITestListener, ISuiteListener, IExecutionListener, IInvokedMethodListener {

    private static final Logger logger = LogManager.getLogger(CustomListeners.class);

    @Override
    public void onStart(ISuite suite) {
        logger.info("Starting test suite: " + suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.info("Finishing test suite: " + suite.getName());
    }

    @Override
    public void onExecutionStart() {
        logger.info("Test execution started.");
    }

    @Override
    public void onExecutionFinish() {
        logger.info("Test execution finished.");
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: " + result.getName());
        String fileName = result.getName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".html";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)))) {
            writer.write(result.getThrowable().toString());
        } catch (IOException e) {
            logger.error("Error saving HTML to file: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: " + result.getName() + ". Reason: " + result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("Test failed but within success percentage: " + result.getName());
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        logger.info("After invocation of method: " + method.getTestMethod().getMethodName());
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        logger.info("Before invocation of method: " + method.getTestMethod().getMethodName());
    }
}
