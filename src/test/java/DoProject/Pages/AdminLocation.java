package DoProject.Pages;

import DoProject.CommonFunctions.Functions;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class AdminLocation {

    private WebDriver driver;
    private Functions functions;
    private String urlViewSystemUser ="/viewSystemUser";
    private String urlLocations ="/viewLocations";
    private String urlAddLocations ="/saveLocation";
    private By AdminBTN = By.xpath("//span[normalize-space()='Admin']");
    private By Organization = By.xpath("//span[normalize-space()='Organization']");
    private By Locations = By.xpath("//a[@role='menuitem' and normalize-space()='Locations']");
    private By AddBTN = By.xpath("//button[normalize-space()='Add']");
    private By Name = By.xpath("//label[text()='Name']/parent::div/following-sibling::div//input");
    private By City = By.xpath("//label[text()='City']/parent::div/following-sibling::div//input");
    private By Country = By.xpath("//div[@class='oxd-select-text-input']");
    private By SaveBTN = By.xpath("//button[normalize-space()='Save']");
    private By NameSearch = By.xpath("//label[text()='Name']/parent::div/following-sibling::div//input");
    private By CitySearch = By.xpath("//label[text()='City']/parent::div/following-sibling::div//input");
    private By CountrySearch = By.xpath("//div[@class='oxd-select-text-input']");
    private By SearchBTN = By.xpath("//button[normalize-space()='Search']");
    private By EditBTN = By.xpath("//i[@class='oxd-icon bi-pencil-fill']");
    private By DeleteBTN = By.xpath("//i[@class='oxd-icon bi-trash']");
    private By YesDeleteBTN = By.xpath("//button[normalize-space()='Yes, Delete']");


    public AdminLocation(WebDriver driver)
    {
        this.driver = driver;
        functions = new Functions(driver);
    }

    public void confirmAdminLocation()
    {
        functions.Click(AdminBTN);
        functions.waitUrlContains(urlViewSystemUser);
        WebElement AdminTitle = driver.findElement(By.xpath("//h6[normalize-space()='User Management']"));
        Assert.assertEquals(AdminTitle.getText(), "User Management", "Expected title 'User Management' but found different");
        functions.Click(Organization);
        functions.Click(Locations);
        functions.waitUrlContains(urlLocations);
        WebElement LocationsTitle = driver.findElement(By.xpath("//h5[normalize-space()='Locations']"));
        Assert.assertEquals(LocationsTitle.getText(), "Locations", "Expected title 'Locations' but found different");
    }

    public void doAdminLocationAddLocation(String NameData, String CityData, String CountryData) throws InterruptedException {
        //Add location
        functions.Click(AddBTN);
        functions.waitUrlContains(urlAddLocations);
        WebElement AddLocationsTitle = driver.findElement(By.xpath("//h6[normalize-space()='Add Location']"));
        Assert.assertEquals(AddLocationsTitle.getText(), "Add Location", "Expected title 'Add Location' but found different");
        functions.sendKeys(Name, NameData);
        functions.sendKeys(City, CityData);
        functions.Click(Country);
        WebElement ChooseVietnam = driver.findElement(By.xpath("//div[@role='listbox']//span[normalize-space()='Viet Nam']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", ChooseVietnam);
        functions.ClickWE(ChooseVietnam);
        Thread.sleep(1000);
        functions.Submit(SaveBTN);
        //Check location after add
        //Choose value search
        functions.waitUrlContains(urlLocations);
        WebElement LocationsTitle = driver.findElement(By.xpath("//h5[normalize-space()='Locations']"));
        Assert.assertEquals(LocationsTitle.getText(), "Locations", "Expected title 'Locations' but found different");
        functions.sendKeys(NameSearch, NameData);
        functions.sendKeys(CitySearch, CityData);
        functions.Click(CountrySearch);
        WebElement ChooseVietnamSearch = driver.findElement(By.xpath("//div[@role='listbox']//span[normalize-space()='Viet Nam']"));
        js.executeScript("arguments[0].scrollIntoView(true);", ChooseVietnamSearch);
        functions.ClickWE(ChooseVietnamSearch);
        functions.Submit(SearchBTN);
        //Check value in table
        functions.checkAllValueInTable(2, NameData);
        functions.checkAllValueInTable(3, CityData);
        functions.checkAllValueInTable(4, CountryData);
    }

    public void doAdminLocationEditLocation(String NameEditData) throws InterruptedException {
        functions.Click(EditBTN);
        functions.waitUrlContains(urlAddLocations);
        WebElement EditLocationTitle = driver.findElement(By.xpath("//h6[normalize-space()='Edit Location']"));
        Assert.assertEquals(EditLocationTitle.getText(), "Edit Location", "Expected title 'Edit Location' but found different");
        functions.Click(Name);
        functions.ClearAC();
        functions.sendKeys(Name, NameEditData);
        functions.Submit(SaveBTN);
        functions.waitUrlContains(urlLocations);
        WebElement LocationsTitle = driver.findElement(By.xpath("//h5[normalize-space()='Locations']"));
        Assert.assertEquals(LocationsTitle.getText(), "Locations", "Expected title 'Locations' but found different");
        functions.sendKeys(Name, NameEditData);
        functions.Submit(SearchBTN);
        functions.checkAllValueInTable(2, NameEditData);
    }

    public void doAdminLocationDeleteLocation() throws InterruptedException {
        functions.Click(DeleteBTN);
        functions.waitElementToBeClickable(YesDeleteBTN);
        Thread.sleep(1000);
        functions.Click(YesDeleteBTN);
        functions.checkAllValueInTableAfterDelete();
    }
}
