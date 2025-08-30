package DoProject.TestCases;

import DoPrepare.Common.Listener;
import DoPrepare.Common.SetUp;
import DoPrepare.Help.CaptureHelp;
import DoPrepare.Help.ExcelHelp;
import DoProject.Pages.AdminJobCategories;
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
@Feature("Job Categories Management")
@Listeners(Listener.class)
public class AdminJobCategoriesTest extends SetUp {

    private WebDriver driver;
    private Login login;
    private AdminJobCategories adminjobcategories;
    private ExcelHelp excelhelp;

    @BeforeMethod
    public void AdminJobCategoriesTest() {
        driver = getDriver();
        login = new Login(driver);
        adminjobcategories = new AdminJobCategories(driver);
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
    public void loginTest() throws Exception {
        excelhelp.setExcelFile("src/main/resources/DataFile.xlsx", "LoginData");
        login.doLogin(excelhelp.getCellData("USERNAME", 1), excelhelp.getCellData("PASSWORD", 1));
    }

    @Step
    @Test(priority = 2)
    @Story("Job Categories Management")
    @Description("Test adding, editing, and deleting job categories using data from Excel")
    public void adminJobCategoriesTest() throws Exception {
        excelhelp.setExcelFile("src/main/resources/DataFile.xlsx", "AdminJobCategoriesData");
        adminjobcategories.conFirmAdminJobCategories();
        adminjobcategories.doAdminJobCategoriesAddJobCategories(excelhelp.getCellData("NAME", 3));
        adminjobcategories.doAdminJobCategoriesEditJobCategories(excelhelp.getCellData("NAME", 3),
                excelhelp.getCellData("EDIT VALUE", 3));
        adminjobcategories.doAdminJobCategoriesDeleteJobCategories(excelhelp.getCellData("EDIT VALUE", 3));
    }
}