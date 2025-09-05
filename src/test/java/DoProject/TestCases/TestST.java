package DoProject.TestCases;

import DoPrepare.Common.Listener;
import DoPrepare.Common.SetUp;
import DoPrepare.Help.ExcelHelp;
import DoProject.Pages.AdminUserManagement;
import DoProject.Pages.Login;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

public class TestST {

    private WebDriver driver;
    private Login login;
    private AdminUserManagement adminusermanagement;
    private ExcelHelp excelhelp;


    @Test
    public void testST() throws Exception
    {
        excelhelp = new ExcelHelp();
        excelhelp.setExcelFile("src/main/resources/DataFile.xlsx", "AdminJobTitleData");
        System.out.println(excelhelp.getCellData("EDIT VALUE", 1)); // Automation Tester
        System.out.println(excelhelp.getCellData("EDIT VALUE", 2)); // Senior Developer
        System.out.println(excelhelp.getCellData("EDIT VALUE", 3)); // Lead Manager

    }


}
