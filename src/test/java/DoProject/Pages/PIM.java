package DoProject.Pages;

import DoProject.CommonFunctions.Functions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class PIM {

    private WebDriver driver;
    private Functions functions;
    private String urlPIM = "/viewEmployeeList";
    private String urlAddEmployee = "/addEmployee";
    private String urlPersonalDetails = "/viewPersonalDetails";
    private String urlLogin = "/login";
    private String urlDashboard = "/dashboard";
    private By PIM = By.xpath("//span[normalize-space()='PIM']");
    private By AddEmployee = By.xpath("//a[normalize-space()='Add Employee']");
    private By FirstName = By.xpath("//input[@name='firstName']");
    private By LastName = By.xpath("//input[@name='lastName']");
    private By EmployeeId = By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div//input");
    private By CreateLoginDetails = By.xpath("//span[@class='oxd-switch-input oxd-switch-input--active --label-right']");
    private By Username = By.xpath("//label[text()='Username']/parent::div/following-sibling::div//input");
    private By Password = By.xpath("//label[text()='Password']/parent::div/following-sibling::div//input");
    private By ConfirmPassword  = By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div//input");
    private By EmployeeList  = By.xpath("//a[normalize-space()='Employee List']");
    private By EmployeeIdSearch = By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div//input");
    private By SearchBTN = By.xpath("//button[normalize-space()='Search']");
    private By Account = By.xpath("//p[@class='oxd-userdropdown-name']");
    private By Logout = By.xpath("//a[@role='menuitem' and normalize-space()='Logout']");
    private By UsernameLogin = By.xpath("//input[@name='username']");
    private By PasswordLogin = By.xpath("//input[@name='password']");
    private By LoginBTN = By.xpath("//button[@type='submit']");


    public PIM(WebDriver driver)
    {
        this.driver = driver;
        functions= new Functions(driver);
    }

    public void confirmPIM()
    {
        functions.Click(PIM);
        functions.waitUrlContains(urlPIM);
        WebElement EmployeeListTitle = driver.findElement(By.xpath("//h5[normalize-space()='Employee Information']"));
        Assert.assertTrue(EmployeeListTitle.getText().equals("Employee Information"), "Expected title 'Employee Information' but found different");
        functions.Click(AddEmployee);
        functions.waitUrlContains(urlAddEmployee);
        WebElement AddEmployeeTitle = driver.findElement(By.xpath("//h6[normalize-space()='Add Employee']"));
        Assert.assertTrue(AddEmployeeTitle.getText().equals("Add Employee"), "Expected title 'Add Employee' but found different");
    }

    public void doPIMAddEmployee(String FirtNameData, String LastNameData, String EmployeeIdData, String UsernameData, String PasswordData) throws InterruptedException {
        //Add employee
        functions.sendKeys(FirstName, FirtNameData);
        functions.sendKeys(LastName, LastNameData);
        functions.Click(EmployeeId);
        functions.ClearAC();
        functions.sendKeys(EmployeeId, EmployeeIdData);
        functions.Click(CreateLoginDetails);
        functions.sendKeys(Username, UsernameData);
        WebElement Status = driver.findElement(By.xpath("//label[normalize-space()='Enabled']"));
        if(!Status.isSelected())
        {
            Status.click();
        }
        functions.sendKeys(Password, PasswordData);
        functions.sendKeys(ConfirmPassword, PasswordData);
        WebElement SaveBTN = driver.findElement(By.xpath("//button[normalize-space()='Save']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", SaveBTN);
        Thread.sleep(2000);
        functions.SubmitWE(SaveBTN);
        functions.waitUrlContains(urlPersonalDetails);
        WebElement PersonalDetailsTitle = driver.findElement(By.xpath("//h6[normalize-space()='Personal Details']"));
        Assert.assertTrue(PersonalDetailsTitle.getText().equals("Personal Details"), "Expected title 'Personal Details' but found different");
        functions.Click(EmployeeList);
        //Check employee after add
        //Choose value search
        functions.waitUrlContains(urlPIM);
        WebElement EmployeeListTitle = driver.findElement(By.xpath("//h5[normalize-space()='Employee Information']"));
        Assert.assertTrue(EmployeeListTitle.getText().equals("Employee Information"), "Expected title 'Employee Information' but found different");
        functions.sendKeys(EmployeeIdSearch, EmployeeIdData);
        functions.Submit(SearchBTN);
        //Check value in table
        functions.checkAllValueInTable(2, EmployeeIdData);
    }

    public void doPIMLoginEmployee(String UsernameLoginData, String PasswordLoginData) throws InterruptedException {
        functions.Click(Account);
        functions.Click(Logout);
        functions.waitUrlContains(urlLogin);
        //In valid username
        functions.sendKeys(PasswordLogin, PasswordLoginData);
        functions.Submit(LoginBTN);
        Thread.sleep(2000);
        Assert.assertFalse(driver.getCurrentUrl().contains(urlDashboard), "Current URL contain '/Dashboard' - Login Passed - Test Failed");
        //In valid username
        functions.Click(PasswordLogin);
        functions.ClearAC();
        functions.sendKeys(UsernameLogin, UsernameLoginData);
        functions.Submit(LoginBTN);
        Thread.sleep(2000);
        Assert.assertFalse(driver.getCurrentUrl().contains(urlDashboard), "Current URL contain '/Dashboard' - Login Passed - Test Failed");
        //Login success
        functions.sendKeys(PasswordLogin, PasswordLoginData);
        functions.Submit(LoginBTN);
        functions.waitUrlContains(urlDashboard);
    }
}
