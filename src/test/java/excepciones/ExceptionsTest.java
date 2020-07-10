package excepciones;

import automation.practice.MainPage;
import driver.Driver;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.reporters.jq.Main;
import seleniumEasy.ModalDemo;
import util.JSONHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

public class ExceptionsTest {

    HashMap<String, String> preferencias = new HashMap<String, String>();
    String url_AUT="";
    @Factory(dataProvider = "dpBrowsers")
    public ExceptionsTest(String browserName) {
        preferencias.put("browserName", browserName);
    }
    public ExceptionsTest(){
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
        Driver.getInstance().setDriver(preferencias.get("browserName"), preferencias,null);
        Driver.getInstance().getDriver().manage().deleteAllCookies();
    }

    @Test
    public void javaScriptExceptionTest() throws InterruptedException {
        WebDriver driver =Driver.getInstance().getDriver();
        driver.get("http://automationpractice.com/index.php");
        MainPage mainPage = new MainPage(driver);
        mainPage.ingresarAContactUsMalaSintaxis();
    }

    @Test
    public void scripTimeOutExceptionTest() throws InterruptedException {
        WebDriver driver =Driver.getInstance().getDriver();
        driver.get("http://automationpractice.com/index.php");
        MainPage mainPage = new MainPage(driver);
        mainPage.ingresarAContactUsScriptTimeOut();
    }

    @Test
    public void noSuchElementExceptionTest(){
        WebDriver driver =Driver.getInstance().getDriver();
        driver.get("http://automationpractice.com/index.php");

        MainPage mainPage = new MainPage(driver);
        mainPage.ingresarAContactUsNoSuchElement();
    }

    @Test
    public void timeOutExceptionTest(){
        WebDriver driver =Driver.getInstance().getDriver();
        driver.get("http://automationpractice.com/index.php");
        MainPage mainPage = new MainPage(driver);
        mainPage.ingresarAContactUsTimeOut();
    }

    @Test
    public void elementClickInterceptedExceptionTest() throws InterruptedException {
        WebDriver driver =Driver.getInstance().getDriver();
        driver.get("https://www.seleniumeasy.com/test/bootstrap-modal-demo.html");

        ModalDemo modalDemo = new ModalDemo(driver);
        modalDemo.clickEnBtnLaunchModalConWaitAlert();
        modalDemo.clickEnBtnLaunchModalSinWaitAlert();
    }

    @Test
    public void noSuchFrameException() throws InterruptedException {
        WebDriver driver =Driver.getInstance().getDriver();
        driver.get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_iframe");

        Thread.sleep(4000);
        driver.switchTo().frame("olx");

    }

    @Test
    public void noSuchWindowException() throws InterruptedException {
        WebDriver driver =Driver.getInstance().getDriver();
        driver.get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_iframe");

        Thread.sleep(4000);
        /*Set<String> ventanas= driver.getWindowHandles();
        for(String ventana : ventanas){
            //ir cambiando de ventanas
        }*/
        System.out.println("Ventana actual: "+driver.getWindowHandle());
        driver.switchTo().window("ventanaPrueba");
    }

    @Test
    public void staleElementReferenceExceptionTest() {
        WebDriver driver = Driver.getInstance().getDriver();
        driver.get("http://automationpractice.com/index.php");
        new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.linkText("Contact us")));
        WebElement btnContactUs = driver.findElement(By.linkText("Contact us"));
        driver.navigate().refresh();
        new WebDriverWait(driver,30).until(ExpectedConditions.stalenessOf(btnContactUs));
        btnContactUs.click();
    }

    @Test
    public void invalidSelectorException() {
        WebDriver driver = Driver.getInstance().getDriver();
        driver.get("http://automationpractice.com/index.php");
        new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.linkText("Contact us")));
        driver.findElement(By.xpath("//div[@id='contact-link']]")).click();
    }
    @Test
    public void noAlertPresentException(){
        WebDriver driver = Driver.getInstance().getDriver();
        driver.get("http://automationpractice.com/index.php");
        driver.switchTo().alert();
    }
    @Test
    public void noSuchSessionException(){
        WebDriver driver = Driver.getInstance().getDriver();
        driver.get("http://automationpractice.com/index.php");
        driver.close();
        driver.findElement(By.xpath("//div[@id='contact-link']]")).click();
    }



    @AfterMethod
    public void tearDown(){
        if (Driver.getInstance().getDriver() != null) {
            Driver.getInstance().getDriver().quit();
        }
    }

}
