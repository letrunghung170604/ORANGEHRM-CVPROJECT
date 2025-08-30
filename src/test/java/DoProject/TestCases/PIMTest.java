package DoProject.TestCases;

import DoPrepare.Common.Listener;
import DoPrepare.Common.SetUp;
import DoPrepare.Help.CaptureHelp;
import DoPrepare.Help.ExcelHelp;
import DoProject.Pages.Login;
import DoProject.Pages.PIM;
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

@Epic("Human Resource Management")
@Feature("Personal Information Management (PIM)")
@Listeners(Listener.class)
public class PIMTest extends SetUp {

    private WebDriver driver;
    private Login login;
    private PIM pim;
    private ExcelHelp excelhelp;

    @BeforeMethod
    public void PIMTest() {
        driver = getDriver();
        login = new Login(driver);
        pim = new PIM(driver);
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
    @Story("Employee Management in PIM")
    @Description("Test adding an employee and logging in with employee credentials from Excel")
    public void pimTest() throws Exception {
        excelhelp.setExcelFile("src/main/resources/DataFile.xlsx", "PIMData");
        pim.confirmPIM();
        pim.doPIMAddEmployee(excelhelp.getCellData("FIRST NAME", 3),
                excelhelp.getCellData("LAST NAME", 3),
                excelhelp.getCellData("EMPLOYEE ID", 3),
                excelhelp.getCellData("USERNAME", 3),
                excelhelp.getCellData("PASSWORD", 3));
        pim.doPIMLoginEmployee(excelhelp.getCellData("USERNAME", 3),
                excelhelp.getCellData("PASSWORD", 3));
    }
}