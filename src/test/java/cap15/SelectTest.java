package cap15;

import automation.practice.*;
import driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import util.Excel;
import util.reporter.GetScreenShot;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static util.reporter.MensajesReporte.cargarMensajeResultadoTestNOk;
import static util.reporter.MensajesReporte.cargarMensajeResultadoTestOk;
import static util.utilitariosIO.UtilitariosIO.comparaCadenas;

public class SelectTest {

    HashMap<String, String> preferencias = new HashMap<String, String>();
    String url_AUT="";
    String excelPath = System.getProperty("user.dir")+"\\report\\excelOutput\\";
    @Factory(dataProvider = "dpBrowsers")
    public SelectTest(String browserName) {
        preferencias.put("browserName", browserName);
    }
    public SelectTest(){
    }

    @DataProvider
    public Object[][] dpBrowsers() throws Exception {
        //return new Object[][] {{"chrome"},{"firefox"},{"IE"}};
        //return new Object[][] {{"IE"}};
        //return new Object[][] {{"firefox"}};
        return new Object[][]{{"chrome"}};
    }

    @BeforeMethod
    public void setUp(Method method) throws Exception {
        Driver.getInstance().setDriver(preferencias.get("browserName"), preferencias);
        url_AUT = "https://chercher.tech/practice/practice-dropdowns-selenium-webdriver";
        Driver.getInstance().getDriver().get(url_AUT);
        Driver.getInstance().getDriver().manage().deleteAllCookies();
    }

    @Test
    public void cp001_selectTodo()  {
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();

        FluentWait apareceForm = new FluentWait<WebDriver>(driver);
        apareceForm.pollingEvery(Duration.ofMillis(250));
        apareceForm.withTimeout(Duration.ofSeconds(30)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try{
                    Select selectQuery = new Select(driver.findElement(By.tagName("select")));
                    if(selectQuery.getOptions().size()>0){
                        return true;
                    }else{
                        return false;
                    }
                }catch (Exception e){
                    return false;
                }
            }
        });

        Select select = new Select(driver.findElement(By.tagName("select")));
        List<WebElement> elements = select.getOptions();
        System.out.println("Listado del Select: ");
        for(WebElement option : elements){
            System.out.println("Option-> "+option.getText());
        }
    }

    @Test
    public void cp002_selectOption(){
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();
        new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOf(driver.findElement(By.tagName("select"))));
        WebElement optionIphone = driver.findElement(By.xpath("//option[text()='Iphone']"));
        System.out.println("Option value-> "+optionIphone.getAttribute("value"));
        System.out.println("Option text -> "+optionIphone.getText());
    }




    @AfterMethod
    public void tearDown(){
        if (Driver.getInstance().getDriver() != null) {
            Driver.getInstance().getDriver().quit();
        }
    }

}
