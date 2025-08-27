package DoPrepare.Common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class SetUp {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private String url = "";
    private final int PageLoadTime = 30;
    private final int ImplicitlyTime = 20;

    // Get WebDriver instance for the current thread
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Get application URL
    public String getUrl() {
        return url;
    }

    // Initialize WebDriver based on browser type
    public WebDriver setupDriver(String browserType) {
        WebDriver webDriver;
        switch (browserType.trim().toLowerCase()) {
            case "chrome":
                webDriver = initChromeDriver();
                break;
            case "firefox":
                webDriver = initFirefoxDriver();
                break;
            case "edge":
                webDriver = initEdgeDriver();
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                webDriver = initChromeDriver();
        }
        driver.set(webDriver);
        return webDriver;
    }

    // Set up WebDriver with browser type and navigate to URL
    private void setDriver(String browserType, String appURL) {
        WebDriver webDriver;
        switch (browserType) {
            case "chrome":
                webDriver = initChromeDriver();
                webDriver.navigate().to(appURL);
                break;
            case "firefox":
                webDriver = initFirefoxDriver();
                webDriver.navigate().to(appURL);
                break;
            case "edge":
                webDriver = initEdgeDriver();
                webDriver.navigate().to(appURL);
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                webDriver = initChromeDriver();
                webDriver.navigate().to(appURL);
        }
        driver.set(webDriver);
    }

    // Initialize Chrome WebDriver
    private WebDriver initChromeDriver() {
        System.out.println("Launching Chrome browser...");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en");
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PageLoadTime));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ImplicitlyTime));
        return webDriver;
    }

    // Initialize Firefox WebDriver
    private WebDriver initFirefoxDriver() {
        System.out.println("Launching Firefox browser...");
        WebDriverManager.firefoxdriver().setup();
        WebDriver webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PageLoadTime));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ImplicitlyTime));
        return webDriver;
    }

    // Initialize Edge WebDriver
    private WebDriver initEdgeDriver() {
        System.out.println("Launching Edge browser...");
        WebDriverManager.edgedriver().setup();
        WebDriver webDriver = new EdgeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PageLoadTime));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ImplicitlyTime));
        return webDriver;
    }

    // Initialize test setup with browser type and URL before class execution
    @Parameters({ "browserType", "webURL" })
    @BeforeClass
    public void initializeTestBaseSetup(String browserType, String webURL) {
        try {
            // Set up driver with specified browser and URL
            setDriver(browserType, webURL);
        } catch (Exception e) {
            System.out.println("Error..." + e.getStackTrace());
        }
    }

    // Clean up after class execution
    @AfterClass
    public void tearDown() throws Exception {
        Thread.sleep(2000);
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
        }
    }
}