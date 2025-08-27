package DoProject.Pages;

import DoProject.CommonFunctions.Functions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Login {

    private WebDriver driver;
    private Functions functions;
    private String url = "/dashboard";
    private By Username = By.xpath("//input[@name='username']");
    private By Password = By.xpath("//input[@name='password']");
    private By LoginBTN = By.xpath("//button[@type='submit']");

    public Login(WebDriver driver)
    {
        this.driver = driver;
        functions = new Functions(driver);
    }

    public void doLogin(String UsernameData, String PasswordData) throws InterruptedException {

        functions.Clear(Username);
        functions.sendKeys(Username, UsernameData);
        functions.Clear(Password);
        functions.sendKeys(Password, PasswordData);
        functions.Submit(LoginBTN);
        Thread.sleep(4000);
        WebElement AdminTitle = driver.findElement(By.xpath("//h6[normalize-space()='Dashboard']"));
        Assert.assertEquals(AdminTitle.getText(), "Dashboard", "Expected title 'Dashboard' but found different - Login Failed");
        Assert.assertTrue(driver.getCurrentUrl().contains(url), "Current URL does not contain '/Dashboard' - Login Failed");

    }
}
