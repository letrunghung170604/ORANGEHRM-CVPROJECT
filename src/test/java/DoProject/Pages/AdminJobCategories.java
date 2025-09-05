package DoProject.Pages;

import DoProject.CommonFunctions.Functions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class AdminJobCategories {

    private WebDriver driver;
    private Functions functions;
    private String urlViewSystemUser ="/viewSystemUser";
    private String urlJobCategory ="/jobCategory";
    private String urlAddJobCategory ="/saveJobCategory";
    private By AdminBTN = By.xpath("//span[normalize-space()='Admin']");
    private By Job = By.xpath("//span[normalize-space()='Job']");
    private By JobCategories = By.xpath("//a[@role='menuitem' and normalize-space()='Job Categories']");
    private By AddBTN = By.xpath("//button[normalize-space()='Add']");
    private By Name = By.xpath("//label[text()='Name']/parent::div/following-sibling::div//input");
    private By SaveBTN = By.xpath("//button[normalize-space()='Save']");
    //button[normalize-space()='Yes, Delete']


    public AdminJobCategories(WebDriver driver)
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
                functions.waitUrlContains(urlAddJobCategory);
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

    public void conFirmAdminJobCategories()
    {
        functions.Click(AdminBTN);
        functions.waitUrlContains(urlViewSystemUser);
        WebElement AdminTitle = driver.findElement(By.xpath("//h6[normalize-space()='User Management']"));
        Assert.assertEquals(AdminTitle.getText(), "User Management", "Expected title 'User Management' but found different");
        functions.Click(Job);
        functions.Click(JobCategories);
        functions.waitUrlContains(urlJobCategory);
        WebElement JobCategoriesTitle = driver.findElement(By.xpath("//h6[normalize-space()='Job Categories']"));
        Assert.assertEquals(JobCategoriesTitle.getText(), "Job Categories", "Expected title 'Job Categories' but found different");
    }

    public void doAdminJobCategoriesAddJobCategories(String NameData) throws InterruptedException {

        //Add job categories
        functions.Click(AddBTN);
        functions.waitUrlContains(urlAddJobCategory);
        WebElement AddJobCategoryTitle = driver.findElement(By.xpath("//h6[normalize-space()='Add Job Category']"));
        Assert.assertEquals(AddJobCategoryTitle.getText(), "Add Job Category", "Expected title 'Add Job Category' but found different");
        functions.sendKeys(Name, NameData);
        functions.Submit(SaveBTN);
        functions.waitUrlContains(urlJobCategory);
        WebElement JobCategoriesTitle = driver.findElement(By.xpath("//h6[normalize-space()='Job Categories']"));
        Assert.assertEquals(JobCategoriesTitle.getText(), "Job Categories", "Expected title 'Job Categories' but found different");
        //Check value in table
        functions.checkOneValueInTable(2, NameData);
    }

    public void doAdminJobCategoriesEditJobCategories(String NameData, String EditValueData) throws InterruptedException
    {
        checkOneValueInTableAndEdit(2, NameData, EditValueData);
        functions.waitUrlContains(urlJobCategory);
        functions.checkOneValueInTableAfterEdit(2, EditValueData);
    }

    public void doAdminJobCategoriesDeleteJobCategories(String EditValueData) throws InterruptedException
    {
        functions.checkOneValueInTableAndDelete(2, EditValueData);
        functions.checkOneValueInTableAfterDelete(2, EditValueData);
    }

}
