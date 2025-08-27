package DoProject.Pages;

import DoProject.CommonFunctions.Functions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class AdminJobTitle {

    private WebDriver driver;
    private Functions functions;
    private String urlViewSystemUser ="/viewSystemUser";
    private String urlJobTitle = "/viewJobTitleList";
    private String urlAddJobTitle = "/saveJobTitle";
    private By AdminBTN = By.xpath("//span[normalize-space()='Admin']");
    private By Job = By.xpath("//span[normalize-space()='Job']");
    private By JobTitles = By.xpath("//a[@role='menuitem' and normalize-space()='Job Titles']");
    private By AddBTN = By.xpath("//button[normalize-space()='Add']");
    private By JobTitle = By.xpath("//label[text()='Job Title']/parent::div/following-sibling::div//input");
    private By JobDescription = By.xpath("(//textarea[@class='oxd-textarea oxd-textarea--active oxd-textarea--resize-vertical'])[1]");
    private By Note = By.xpath("//textarea[@placeholder='Add note']");
    private By JobSpecificationField = By.xpath("//input[@type='file' and contains(@class,'oxd-file-input')]");


    public AdminJobTitle(WebDriver driver)
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
                functions.waitUrlContains(urlAddJobTitle);
                functions.Click(JobTitle);
                functions.ClearAC();
                functions.sendKeys(JobTitle, EditValueData);
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

    public void confirmAdminJobTitle()
    {
        functions.Click(AdminBTN);
        functions.waitUrlContains(urlViewSystemUser);
        WebElement AdminTitle = driver.findElement(By.xpath("//h6[normalize-space()='User Management']"));
        Assert.assertEquals(AdminTitle.getText(), "User Management", "Expected title 'User Management' but found different");
        functions.Click(Job);
        functions.Click(JobTitles);
        functions.waitUrlContains(urlJobTitle);
        WebElement JobTitlesTitle = driver.findElement(By.xpath("//h6[normalize-space()='Job Titles']"));
        Assert.assertEquals(JobTitlesTitle.getText(), "Job Titles", "Expected title 'Job Titles' but found different");
    }

    public void doAminJobTitleAddJobTitle(String JobTitleData, String JobDescriptionData, String NoteData, String Path ) throws InterruptedException {

        //Add job title
        functions.Click(AddBTN);
        functions.waitUrlContains(urlAddJobTitle);
        WebElement AddJobTitleTitle = driver.findElement(By.xpath("//h6[@class='oxd-text oxd-text--h6 orangehrm-main-title']"));
        Assert.assertEquals(AddJobTitleTitle.getText(), "Add Job Title", "Expected title 'Add Job Title' but found different");
        functions.sendKeys(JobTitle, JobTitleData);
        functions.sendKeys(JobDescription, JobDescriptionData);
        functions.upLoadFile(Path, JobSpecificationField);
        functions.sendKeys(Note, NoteData);
        WebElement SaveBTN = driver.findElement(By.xpath("//button[normalize-space()='Save']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", SaveBTN);
        functions.SubmitWE(SaveBTN);
        functions.waitUrlContains(urlJobTitle);
        //Check value in table
        functions.checkOneValueInTable(2, JobTitleData);
        functions.checkOneValueInTable(3, JobDescriptionData);
    }

    public void doAminJobTitleEditJobTitle(String JobTitleData, String EditValueData) throws InterruptedException
    {
        checkOneValueInTableAndEdit(2, JobTitleData, EditValueData);
        functions.waitUrlContains(urlJobTitle);
        functions.checkOneValueInTableAfterEdit(2, EditValueData);
    }

    public void doAminJobTitleDeleteJobTitle(String EditValueData) throws InterruptedException
    {
        functions.checkOneValueInTableAndDelete(2, EditValueData);
        functions.checkOneValueInTableAfterDelete(2, EditValueData);
    }

}
