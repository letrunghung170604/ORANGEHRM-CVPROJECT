package DoProject.TestCases;

import DoPrepare.Common.Listener;
import DoPrepare.Common.SetUp;
import DoPrepare.Help.CaptureHelp;
import DoPrepare.Help.ExcelHelp;
import DoProject.Pages.AdminEmploymentStatus;
import DoProject.Pages.Login;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("System Administration")
@Feature("Employment Status Management")
@Listeners(Listener.class)
public class AdminEmploymentStatusTest extends SetUp {

    private WebDriver driver;
    private Login login;
    private AdminEmploymentStatus adminemploymentstatus;
    private ExcelHelp excelhelp;

    @BeforeMethod
    public void setUpDriver() {
        driver = getDriver();
        login = new Login(driver);
        adminemploymentstatus = new AdminEmploymentStatus(driver);
        excelhelp = new ExcelHelp();
    }

    @AfterMethod
    public void takeScreenshot(ITestResult result) throws InterruptedException {
        Thread.sleep(1000);
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                CaptureHelp.captureScreenshot(driver, result.getName());
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot: " + e.getMessage());
            }
        }
    }

    @Step
    @Test(priority = 1)
    @Story("System Login")
    @Description("Test login functionality with admin credentials")
    public void loginTest() throws InterruptedException {
        login.doLogin("Admin", "admin123");
    }

    @Step
    @Test(priority = 2)
    @Story("Employment Status Management")
    @Description("Test adding, editing, and deleting employment status using data from Excel")
    public void adminEmploymentStatusTest() throws Exception {
        excelhelp.setExcelFile("src/main/resources/DataFile.xlsx", "AdminEmploymentStatusData");
        adminemploymentstatus.confirmAdminEmployeeStatus();
        adminemploymentstatus.doAdminEmploymentStatusAddEmploymentStatus(excelhelp.getCellData("NAME", 3));
        adminemploymentstatus.doAdminEmploymentStatusEditEmploymentStatus(excelhelp.getCellData("NAME", 3),
                excelhelp.getCellData("NAME EDIT", 3));
        adminemploymentstatus.doAdminEmploymentStatusDeleteEmploymentStatus(excelhelp.getCellData("NAME EDIT", 3));
    }
}