package DoPrepare.Help;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Reporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureHelp {

    // Project root path
    static String projectPath = System.getProperty("user.dir") + "/";

    // Date format for screenshot file names
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public static void captureScreenshot(WebDriver driver, String screenName) {
        try {
            // Log driver info
            Reporter.log("Driver for Screenshot: " + driver);

            // Cast driver to TakesScreenshot
            TakesScreenshot ts = (TakesScreenshot) driver;

            // Capture screenshot as File
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Get export path from properties file
            PropertiesHelp.setPropertiesFile();
            String exportPath = projectPath + PropertiesHelp.getPropValue("exportCapturePath");

            // Create directory if it doesn't exist
            File theDir = new File(exportPath);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }

            // Create full file path with name: screenName_timestamp.png
            String fullFilePath = exportPath + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png";

            // Copy screenshot to destination
            FileHandler.copy(source, new File(fullFilePath));

            System.out.println("Screenshot taken: " + fullFilePath);
            Reporter.log("Screenshot taken current URL: " + driver.getCurrentUrl(), true);

        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }
}