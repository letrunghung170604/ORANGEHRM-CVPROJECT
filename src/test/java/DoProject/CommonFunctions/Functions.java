package DoProject.CommonFunctions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.locks.Condition;


public class Functions {

    private  WebDriver driver;

    public Functions(WebDriver driver)
    {
        this.driver = driver;
    }

    public void Clear(By Site)
    {
        driver.findElement(Site).clear();
    }

    public void ClearAC()
    {
        Actions action = new Actions(driver);
        action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).build().perform();
    }

    public void Click(By Site)
    {
        driver.findElement(Site).click();
    }

    public void ClickWE(WebElement Site)
    {
        Site.click();
    }

    public  void sendKeys(By Site, String Data)
    {
        driver.findElement(Site).sendKeys(Data);
    }

    public  void sendKeysWE(WebElement Site, String Data)
    {
        Site.sendKeys(Data);
    }

    public  void Submit(By Site)
    {
        driver.findElement(Site).submit();
    }

    public  void SubmitWE(WebElement Site)
    {
        Site.submit();
    }


    public void waitElementToBeClickable(By Site)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(Site));
    }

    public void waitVisibilityOfElementLocated(By Site)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(Site));
    }

    public void waitVisibilityOfAllElementLocatedBy(By Site)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Site));
    }

    public void waitUrlContains(String String)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.urlContains(String));
    }

    public void assertTrueC(Boolean Condition)
    {
        Assert.assertTrue(Condition);
    }

    public void assertTrueCaM(Boolean Condition, String Message)
    {
        Assert.assertTrue(Condition,Message);
    }

    public void assertEquals(String Actual, String Expected)
    {
        Assert.assertEquals(Actual, Expected);
    }

    public void assertEquals(String Actual, String Expected, String Message)
    {
        Assert.assertEquals(Actual, Expected, Message);
    }

    public void checkAllValueInTable(int ColumnNumber, String Value) throws InterruptedException {

        Thread.sleep(2000);
        List<WebElement> ListRow = driver.findElements(By.xpath("//div[@class='oxd-table-card']"));
        int TotalRow = ListRow.size();
        for(int i = 1; i <= TotalRow; i++)
        {
            WebElement SiteCheck = driver.findElement(By.xpath("//div[@class='oxd-table-card']["+i+"]//div[@role='cell']["+ColumnNumber+"]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", SiteCheck);
            Assert.assertTrue(SiteCheck.getText().contains(Value),"Site row "+i+" column "+ColumnNumber+" don't find search value");
        }
    }

    public void checkOneValueInTable(int ColumnNumber, String Value) throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> ListRow = driver.findElements(By.xpath("//div[@class='oxd-table-card']"));
        int TotalRow = ListRow.size();
        boolean found = false;

        for (int i = 1; i <= TotalRow; i++) {
            WebElement SiteCheck = driver.findElement(By.xpath("//div[@class='oxd-table-card'][" + i + "]//div[@role='cell'][" + ColumnNumber + "]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", SiteCheck);
            if (SiteCheck.getText().equals(Value)) {
                System.out.println("Was found value check: " + Value);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Value '" + Value + "' was NOT found in column " + ColumnNumber);
        }
    }

    public void checkOneValueInTableAndDelete(int ColumnNumber, String Value) throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> ListRow = driver.findElements(By.xpath("//div[@class='oxd-table-card']"));
        int TotalRow = ListRow.size();
        boolean found = false;

        for (int i = 1; i <= TotalRow; i++) {
            WebElement SiteCheck = driver.findElement(By.xpath("//div[@class='oxd-table-card'][" + i + "]//div[@role='cell'][" + ColumnNumber + "]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(false);", SiteCheck);
            if (SiteCheck.getText().equals(Value)) {
                driver.findElement(By.xpath("//div[@class='oxd-table-card'][" + i + "]//button[@type='button'][1]")).click();
                driver.findElement(By.xpath("//button[normalize-space()='Yes, Delete']")).click();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Row not exist in table");
        }
    }

    public void checkAllValueInTableAfterDelete() throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> ListRow = driver.findElements(By.xpath("//div[@class='oxd-table-card']"));
        Assert.assertTrue(ListRow.isEmpty(), "Row still not delete");
    }

    public void checkOneValueInTableAfterEdit(int ColumnNumber, String EditValue) throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> ListRow = driver.findElements(By.xpath("//div[@class='oxd-table-card']"));
        int TotalRow = ListRow.size();
        boolean found = false;

        for (int i = 1; i <= TotalRow; i++) {
            WebElement SiteCheck = driver.findElement(By.xpath("//div[@class='oxd-table-card'][" + i + "]//div[@role='cell'][" + ColumnNumber + "]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(false);", SiteCheck);
            if (SiteCheck.getText().equals(EditValue)) {
                System.out.println("Row was being edit value: " + EditValue);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Row still not edit in table");
        }
    }

    public void checkOneValueInTableAfterDelete(int ColumnNumber, String Value) throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> ListRow = driver.findElements(By.xpath("//div[@class='oxd-table-card']"));
        int TotalRow = ListRow.size();

        for (int i = 1; i <= TotalRow; i++) {
            WebElement SiteCheck = driver.findElement(By.xpath("//div[@class='oxd-table-card'][" + i + "]//div[@role='cell'][" + ColumnNumber + "]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", SiteCheck);
            Assert.assertFalse(SiteCheck.getText().equals(Value),"Row still not delete");
        }
    }


    public void upLoadFile(String Path, By InputField) throws InterruptedException {

            String filePath = System.getProperty("user.dir") + Path;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement uploadElement = wait.until(ExpectedConditions.presenceOfElementLocated(InputField));
            uploadElement.sendKeys(filePath);
    }



}
