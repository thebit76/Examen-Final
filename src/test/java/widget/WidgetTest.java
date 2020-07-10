package widget;

import demoqa.DatePage;
import demoqa.UploadDownloadPage;
import driver.Driver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import seleniumEasy.CheckBoxPage;
import util.reporter.GetScreenShot;
import util.reporter.MensajesReporte;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class WidgetTest {

    HashMap<String, String> preferencias = new HashMap<String, String>();
    String url_AUT="";
    @Factory(dataProvider = "dpBrowsers")
    public WidgetTest(String browserName) {
        preferencias.put("browserName", browserName);
    }
    public WidgetTest(){
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
        if(method.getName().compareTo("probarCheckBox")==0){
            url_AUT = "https://www.seleniumeasy.com/test/basic-checkbox-demo.html";
        }else{
            if(method.getName().compareTo("probarDate")==0)
            {
                url_AUT = "http://demoqa.com/date-picker";
            }else{
                if(method.getName().compareTo("upLoadFileFalse")==0||method.getName().compareTo("upLoadFile")==0||method.getName().compareTo("downloadFile")==0)
                {
                    url_AUT = "http://demoqa.com/upload-download";
                }
            }
        }
        Driver.getInstance().getDriver().get(url_AUT);
        Driver.getInstance().getDriver().manage().deleteAllCookies();
    }
    @Test
    public void probarCheckBox(Method method) throws IOException {
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();
        CheckBoxPage checkBoxPage = new CheckBoxPage(driver);
        checkBoxPage.seleccionarChecBox();
        boolean resultado = checkBoxPage.checkBoxIsSelected();
        String nombreArchivo = testResult.getMethod().getMethodName()+".png";
        GetScreenShot.capturarPantallaBase64(driver);
        if(resultado){
            MensajesReporte.cargarMensajeResultadoTestOk(testResult,
                    "CheckBox fue seleccionado.");
        }else{
            MensajesReporte.cargarMensajeResultadoTestNOk(testResult,
                    "CheckBox NO fue seleccionado.");
        }
    }
    @Test
    public void probarDate(){
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();
        DatePage datePage = new DatePage(driver);
        String fecha = "05/06/2020";
        datePage.seleccionarDate(fecha);
        boolean resultado = datePage.validarFechaMostrada(fecha);
        if(resultado){
            MensajesReporte.cargarMensajeResultadoTestOk(testResult,
                    "");
        }else{
            MensajesReporte.cargarMensajeResultadoTestNOk(testResult,
                    "");
        }
    }

    @Test
    public void upLoadFileFalse(Method method) throws InterruptedException {
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();
        UploadDownloadPage uploadDownloadPage = new UploadDownloadPage(driver);
        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\fotos\\figuras.png";
        uploadDownloadPage.uploadFile(filePath);

        GetScreenShot.capturarPantallaFile(driver,method.getName()+".png");
        MensajesReporte.cargarMensajeResultadoTestNOk(testResult,
                "");

    }

    @Test
    public void upLoadFile() throws InterruptedException {
        WebDriver driver = Driver.getInstance().getDriver();
        UploadDownloadPage uploadDownloadPage = new UploadDownloadPage(driver);
        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\fotos\\figuras.png";
        uploadDownloadPage.uploadFile(filePath);
        Thread.sleep(5000);

    }
    @Test
    public void downloadFile() throws IOException {
        WebDriver driver = Driver.getInstance().getDriver();
        UploadDownloadPage uploadDownloadPage = new UploadDownloadPage(driver);

        String fileName = "sampleFile.jpeg";
        String originalFilePath     = System.getProperty("user.home")+"\\Downloads\\"+fileName;
        String destionationFilePath = System.getProperty("user.dir")+"\\src\\main\\resources\\downloaded\\"+fileName;
        File file = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\downloaded");
        File origenFile  = new File(originalFilePath);
        uploadDownloadPage.downloadFile(origenFile);
        File destinoFile = new File(destionationFilePath);

        FileUtils.cleanDirectory(file);
        FileUtils.copyFile(origenFile,destinoFile);
    }

    @AfterMethod
    public void tearDown(){
        if (Driver.getInstance().getDriver() != null) {
            Driver.getInstance().getDriver().quit();
        }
    }

}
