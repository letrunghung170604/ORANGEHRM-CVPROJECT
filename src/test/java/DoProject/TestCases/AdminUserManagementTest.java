package DoProject.TestCases;

import DoPrepare.Common.Listener;
import DoPrepare.Common.SetUp;
import DoPrepare.Help.CaptureHelp;
import DoPrepare.Help.ExcelHelp;
import DoProject.Pages.AdminUserManagement;
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
@Feature("User Management")
@Listeners(Listener.class)
public class AdminUserManagementTest extends SetUp {

    private WebDriver driver;
    private Login login;
    private AdminUserManagement adminusermanagement;
    private ExcelHelp excelhelp;

    @BeforeMethod
    public void setUpDriver() {
        driver = getDriver();
        login = new Login(driver);
        adminusermanagement = new AdminUserManagement(driver);
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
    @Story("User Account Management")
    @Description("Test adding, editing, and deleting users using data from Excel")
    public void adminUserManagementTest() throws Exception {
        excelhelp.setExcelFile("src/main/resources/DataFile.xlsx", "AdminUserManagementData");
        adminusermanagement.confirmAdminUserManagement();
        adminusermanagement.doAdminUserManagementAddUser(excelhelp.getCellData("EMPLOYEE NAME", 3),
                excelhelp.getCellData("USERNAME", 3),
                excelhelp.getCellData("PASSWORD", 3),
                excelhelp.getCellData("ROLE", 3),
                excelhelp.getCellData("STATUS", 3));
        adminusermanagement.doAdminUserManagementEditUser(excelhelp.getCellData("USERNAME EDIT", 3));
        adminusermanagement.doAdminUserManagementDeleteUser();
    }
}