package DoProject.Pages;

import DoProject.CommonFunctions.Functions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class AdminEmploymentStatus {

    private WebDriver driver;
    private Functions functions;
    private String urlViewSystemUser ="/viewSystemUser";
    private String urlEmploymentStatus ="/employmentStatus";
    private String urlAddEmploymentStatus ="/saveEmploymentStatus";
    private By AdminBTN = By.xpath("//span[normalize-space()='Admin']");
    private By Job = By.xpath("//span[normalize-space()='Job']");
    private By EmploymentStatus = By.xpath("//a[@role='menuitem' and normalize-space()='Employment Status']");
    private By AddBTN = By.xpath("//button[normalize-space()='Add']");
    private By Name = By.xpath("//label[text()='Name']/parent::div/following-sibling::div//input");
    private By SaveBTN = By.xpath("//button[normalize-space()='Save']");

    public AdminEmploymentStatus(WebDriver driver)
    {
        this.driver = driver;
        functions = new Functions(driver);
    }

    public void checkOneValueInTableAndEdit(int ColumnNumber, String Value, String EditValueData) throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> ListRow = driver.findElements(By.xpath("//div[@class='oxd-table-card']"));
        int TotalRow = ListRow.size();
        boolean found = false;

        for (int i = 1; i <= TotalRow; i++) {
            WebElement SiteCheck = driver.findElement(By.xpath("//div[@class='oxd-table-card'][" + i + "]//div[@role='cell'][" + ColumnNumber + "]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", SiteCheck);
            if (SiteCheck.getText().equals(Value)) {
                driver.findElement(By.xpath("//div[@class='oxd-table-card'][" + i + "]//button[@type='button'][2]")).click();
                functions.waitUrlContains(urlAddEmploymentStatus);
                functions.Click(Name);
                functions.ClearAC();
                functions.sendKeys(Name, EditValueData);
                WebElement SaveBTN = driver.findElement(By.xpath("//button[normalize-space()='Save']"));
                js.executeScript("arguments[0].scrollIntoView(true);", SaveBTN);
                functions.SubmitWE(SaveBTN);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Row not exist in table");
        }
    }

    public void confirmAdminEmployeeStatus()
    {
        functions.Click(AdminBTN);
        functions.waitUrlContains(urlViewSystemUser);
        WebElement AdminTitle = driver.findElement(By.xpath("//h6[normalize-space()='User Management']"));
        Assert.assertEquals(AdminTitle.getText(), "User Management", "Expected title 'User Management' but found different");
        functions.Click(Job);
        functions.Click(EmploymentStatus);
        functions.waitUrlContains(urlEmploymentStatus);
        WebElement EmploymentStatusTitle = driver.findElement(By.xpath("//h6[normalize-space()='Employment Status']"));
        Assert.assertEquals(EmploymentStatusTitle.getText(), "Employment Status", "Expected title 'Employment Status' but found different");
    }

    public void doAdminEmploymentStatusAddEmploymentStatus(String NameData) throws InterruptedException {
        //Add employment status
        functions.Click(AddBTN);
        functions.waitUrlContains(urlAddEmploymentStatus);
        WebElement AddEmploymentStatusTitle = driver.findElement(By.xpath("//h6[normalize-space()='Add Employment Status']"));
        Assert.assertEquals(AddEmploymentStatusTitle.getText(), "Add Employment Status", "Expected title 'Add Employment Status' but found different");
        functions.sendKeys(Name, NameData);
        functions.Submit(SaveBTN);
        functions.waitUrlContains(urlEmploymentStatus);
        WebElement EmploymentStatusTitle = driver.findElement(By.xpath("//h6[normalize-space()='Employment Status']"));
        Assert.assertEquals(EmploymentStatusTitle.getText(), "Employment Status", "Expected title 'Employment Status' but found different");
        //Check value in table
        functions.checkOneValueInTable(2, NameData);
    }

    public void doAdminEmploymentStatusEditEmploymentStatus(String NameData, String NameEditData) throws InterruptedException
    {
        checkOneValueInTableAndEdit(2, NameData, NameEditData);
        functions.waitUrlContains(urlEmploymentStatus);
        WebElement EmploymentStatusTitle = driver.findElement(By.xpath("//h6[normalize-space()='Employment Status']"));
        Assert.assertEquals(EmploymentStatusTitle.getText(), "Employment Status", "Expected title 'Employment Status' but found different");
        functions.checkOneValueInTableAfterEdit(2, NameEditData);
    }

    public void doAdminEmploymentStatusDeleteEmploymentStatus(String NameEditData) throws InterruptedException
    {
        functions.checkOneValueInTableAndDelete(2, NameEditData);
        functions.checkOneValueInTableAfterDelete(2, NameEditData);
    }
}
