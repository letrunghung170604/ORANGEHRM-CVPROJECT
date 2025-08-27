package DoProject.Pages;

import DoProject.CommonFunctions.Functions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;


public class AdminUserManagement {

    private WebDriver driver;
    private Functions functions;
    private String urlViewSystemUser ="/viewSystemUser";
    private String urlSaveSystemUser ="/saveSystemUser";
    private By AdminBTN = By.xpath("//span[normalize-space()='Admin']");
    private By AddBTN = By.xpath("//button[normalize-space()='Add']");
    private By UserRole = By.xpath("//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]");
    private By ChooseAdmin = By.xpath("//div[contains(@class,'oxd-select-dropdown')]//span[normalize-space()='Admin']");
    private By Status = By.xpath("//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]/div[1]");
    private By ChooseEnabled = By.xpath("//div[contains(@class,'oxd-select-dropdown')]//span[normalize-space()='Enabled']");
    private By EmployeeName = By.xpath("//input[@placeholder='Type for hints...']");
    private By Username = By.xpath("//label[text()='Username']/parent::div/following-sibling::div//input");
    private By Password = By.xpath("//label[text()='Password']/parent::div/following-sibling::div//input");
    private By ConfirmPassword = By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div//input");
    private By SaveBTN = By.xpath("//button[normalize-space()='Save']");
    private By UsernameSearch = By.xpath("//label[text()='Username']/parent::div/following-sibling::div//input");
    private By UserRoleSearch = By.xpath("//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]");
    private By ChooseAdminSearch = By.xpath("//div[contains(@class,'oxd-select-dropdown')]//span[normalize-space()='Admin']");
    private By EmployeeNameSearch = By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");
    private By StatusSearch = By.xpath("//body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[4]/div[1]/div[2]/div[1]/div[1]");
    private By ChooseEnableSearch = By.xpath("//div[contains(@class,'oxd-select-dropdown')]//span[normalize-space()='Enabled']");
    private By SearchBTN = By.xpath("//button[normalize-space()='Search']");
    private By EditBTN = By.xpath("//i[@class='oxd-icon bi-pencil-fill']");
    private By UsernameEdit = By.xpath("//label[text()='Username']/parent::div/following-sibling::div//input");
    private By SaveEditBTN = By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']");
    private By DeleteBTN = By.xpath("//i[@class='oxd-icon bi-trash']");
    private By YesDeleteBTN = By.xpath("//button[normalize-space()='Yes, Delete']");



    public AdminUserManagement(WebDriver driver)
    {
        this.driver = driver;
        functions = new Functions(driver);
    }

    public void confirmAdminUserManagement()
    {
        functions.Click(AdminBTN);
        functions.waitUrlContains(urlViewSystemUser);
        WebElement AdminTitle = driver.findElement(By.xpath("//h6[normalize-space()='User Management']"));
        Assert.assertEquals(AdminTitle.getText(), "User Management", "Expected title 'User Management' but found different");
    }

    public void doAdminUserManagementAddUser(String EmployeeNameData, String UsernameData, String PasswordData, String RoleData, String StatusData) throws InterruptedException {
        //Add user
        functions.Click(AddBTN);
        functions.waitUrlContains(urlSaveSystemUser);
        functions.Click(UserRole);
        functions.Click(ChooseAdmin);
        functions.Click(Status);
        functions.Click(ChooseEnabled);
        functions.sendKeys(EmployeeName, EmployeeNameData);
        Thread.sleep(3000);
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
        functions.sendKeys(Username, UsernameData);
        functions.sendKeys(Password, PasswordData);
        functions.sendKeys(ConfirmPassword, PasswordData);
        Thread.sleep(1000);
        functions.Submit(SaveBTN);
        //Check user after add
        //Choose value search
        functions.waitUrlContains(urlViewSystemUser);
        WebElement AdminTitle = driver.findElement(By.xpath("//h6[normalize-space()='User Management']"));
        Assert.assertEquals(AdminTitle.getText(), "User Management", "Expected title 'User Management' but found different");
        functions.sendKeys(UsernameSearch, UsernameData);
        functions.Click(UserRoleSearch);
        functions.Click(ChooseAdminSearch);
        functions.sendKeys(EmployeeNameSearch, EmployeeNameData);
        Thread.sleep(3000);
        action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
        functions.Click(StatusSearch);
        functions.Click(ChooseEnableSearch);
        functions.Submit(SearchBTN);
        //Check value in table
        functions.checkAllValueInTable(2, UsernameData);
        functions.checkAllValueInTable(3, RoleData);
        functions.checkAllValueInTable(4, EmployeeNameData);
        functions.checkAllValueInTable(5, StatusData);
    }

    public void doAdminUserManagementEditUser(String UsernameEditData) throws InterruptedException {

        functions.Click(EditBTN);
        functions.waitUrlContains(urlSaveSystemUser);
        functions.waitVisibilityOfElementLocated(UsernameEdit);
        functions.Click(UsernameEdit);
        functions.ClearAC();
        functions.sendKeys(UsernameEdit, UsernameEditData);
        functions.Submit(SaveEditBTN);
        functions.waitUrlContains(urlViewSystemUser);
        functions.sendKeys(UsernameSearch, UsernameEditData);
        functions.Click(SearchBTN);
        functions.checkAllValueInTable(2, UsernameEditData);
    }

    public void doAdminUserManagementDeleteUser() throws InterruptedException {

        functions.Click(DeleteBTN);
        functions.waitElementToBeClickable(YesDeleteBTN);
        Thread.sleep(1000);
        functions.Click(YesDeleteBTN);
        functions.checkAllValueInTableAfterDelete();
    }

}
