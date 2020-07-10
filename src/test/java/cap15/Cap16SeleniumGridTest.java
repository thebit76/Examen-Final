package cap15;

import automation.practice.*;
import driver.Driver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import util.ConfigurationProperties;
import util.Excel;
import util.reporter.GetScreenShot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static util.reporter.MensajesReporte.cargarMensajeResultadoTestNOk;
import static util.reporter.MensajesReporte.cargarMensajeResultadoTestOk;
import static util.utilitariosIO.UtilitariosIO.comparaCadenas;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;

public class Cap16SeleniumGridTest {

    HashMap<String, String> preferencias = new HashMap<String, String>();
    String url_AUT="";
    String excelPath = System.getProperty("user.dir")+"\\report\\excelOutput\\";
    @Factory(dataProvider = "dpBrowsers")
    public Cap16SeleniumGridTest(String platformName,String platformVersion,String browserName,String browserVersion) {
        preferencias.put("platformName",platformName);
        preferencias.put("platformVersion",platformVersion);
        preferencias.put("browserName",browserName);
        preferencias.put("browserVersion",browserVersion);
    }
    public Cap16SeleniumGridTest(){
    }

    @DataProvider
    public static Object[][] dpBrowsers() {

        //return new Object[][] { {"Windows", "10", "Chrome","80.0"}};
        //return new Object[][] { {"OS X", "Catalina", "Safari","13.0"}};
        return new Object[][] { {"Windows", "XP", "Firefox","47.0"}};

    }

    @BeforeMethod
    public void setUp(Method method) throws Exception {
        System.out.println();
        Driver.getInstance().setDriver(preferencias.get("browserName"), preferencias,method.getName());
        url_AUT = "http://automationpractice.com/index.php";
        Driver.getInstance().getDriver().get(url_AUT);
        Driver.getInstance().getDriver().manage().deleteAllCookies();
    }

    @Test (dataProvider = "dp_cp001")
    public void cp001_loginExitoso(HashMap<Object,Object> inputData) throws IOException {
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();
        MainPage mainPage     = new MainPage(driver);
        SignInPage signInPage = new SignInPage(driver);
        MyAccountPage myAccountPage = new MyAccountPage(driver);

        mainPage.ingresarSignIn();
        signInPage.ingresarCredenciales(inputData);
        boolean cargo = myAccountPage.verifificarSiCargoPagina();
        List<String> mensajeReporte = new ArrayList<>();

        GetScreenShot.capturarPantallaBase64(driver);
        GetScreenShot.capturarPantallaFile(driver, testResult.getMethod().getMethodName()+".png");

        if(cargo){
            String mensajeOk = "Cargó correctamente My account page.";
            mensajeReporte.add(mensajeOk);
            cargarMensajeResultadoTestOk(testResult,mensajeOk);
            Excel.crearExcel(excelPath+testResult.getMethod().getMethodName()+".xlsx",mensajeReporte);

        }else{
            String mensajeNOk = "No cargó correctamente My account page.";
            cargarMensajeResultadoTestNOk(testResult,mensajeNOk);
            Excel.crearExcel(excelPath+testResult.getMethod().getMethodName()+".xlsx",mensajeReporte);
        }
    }

    @Test (dataProvider = "dp_cp002")
    public void cp002_loginNoExitoso(HashMap<Object,Object> inputData){
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();
        MainPage mainPage     = new MainPage(driver);
        SignInPage signInPage = new SignInPage(driver);
        mainPage.ingresarSignIn();
        signInPage.ingresarCredenciales(inputData);
        String mensajeErrorActual   = signInPage.devolverMensajeError();
        String mensajeErrorEsperado = inputData.get("mensajeError").toString();

        if(mensajeErrorActual.compareTo(mensajeErrorEsperado)==0){
            cargarMensajeResultadoTestOk(testResult,"Mensaje se muestra correctamente: "+mensajeErrorActual);
        }else{
            cargarMensajeResultadoTestNOk(testResult,
                    "Mensaje esperado : "+mensajeErrorEsperado+"<br>" +
                            "Mensaje actual  : "+mensajeErrorActual
            );
        }
    }

    @Test (dataProvider = "dp_cp003")
    public void cp003_verificarDireccionRegistrada(HashMap<Object,Object> inputData){
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();
        MainPage mainPage               = new MainPage(driver);
        SignInPage signInPage           = new SignInPage(driver);
        MyAccountPage myAccountPage     = new MyAccountPage(driver);
        MyAddressesPage myAddressesPage = new MyAddressesPage(driver);
        YourAddressPage yourAddressPage = new YourAddressPage(driver);

        mainPage.ingresarSignIn();
        signInPage.ingresarCredenciales(inputData);
        myAccountPage.ingresarMyAddresses();
        myAddressesPage.irASeccionActualizarDireccion();
        HashMap<String,String> mapaDireccionActual;
        mapaDireccionActual = yourAddressPage.devolverDatosDireccion();

        String mensajeDiferencia = "";
        mensajeDiferencia = mensajeDiferencia + comparaCadenas(mapaDireccionActual.get("address"),inputData.get("address").toString());
        mensajeDiferencia = mensajeDiferencia + comparaCadenas(mapaDireccionActual.get("city"),inputData.get("city").toString());
        mensajeDiferencia = mensajeDiferencia + comparaCadenas(mapaDireccionActual.get("state"),inputData.get("state").toString());
        mensajeDiferencia = mensajeDiferencia + comparaCadenas(mapaDireccionActual.get("zipCode"),inputData.get("zipCode").toString());
        mensajeDiferencia = mensajeDiferencia + comparaCadenas(mapaDireccionActual.get("country"),inputData.get("country").toString());

        if(mensajeDiferencia.length()!=0){
            cargarMensajeResultadoTestNOk(testResult,mensajeDiferencia);
        }else{
            cargarMensajeResultadoTestOk(testResult,"");
        }

    }
    @Test (dataProvider = "dp_cp004")
    public void cp004_verificarMiInformacionRegistrada(HashMap<Object,Object> inputData){
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();
        MainPage mainPage               = new MainPage(driver);
        SignInPage signInPage           = new SignInPage(driver);
        MyAccountPage myAccountPage     = new MyAccountPage(driver);
        YourPersonalInformationPage personalInformationPage = new YourPersonalInformationPage(driver);

        mainPage.ingresarSignIn();
        signInPage.ingresarCredenciales(inputData);
        myAccountPage.ingresarMyPersonaInformation();

        HashMap<String,String> datosInformacionActual;
        datosInformacionActual = personalInformationPage.devolverDatosInformacion();

        HashMap informacionCompletaEsperada  =  inputData;

        String mensajeDiferencia = "";
        mensajeDiferencia = mensajeDiferencia + comparaCadenas(datosInformacionActual.get("firstName"),informacionCompletaEsperada.get("firstName").toString());
        mensajeDiferencia = mensajeDiferencia + comparaCadenas(datosInformacionActual.get("lastName"),informacionCompletaEsperada.get("lastName").toString());
        mensajeDiferencia = mensajeDiferencia + comparaCadenas(datosInformacionActual.get("email"),informacionCompletaEsperada.get("email").toString());
        mensajeDiferencia = mensajeDiferencia + comparaCadenas(datosInformacionActual.get("birthDay"),informacionCompletaEsperada.get("birthDay").toString());
        mensajeDiferencia = mensajeDiferencia + comparaCadenas(datosInformacionActual.get("birthMonth"),informacionCompletaEsperada.get("birthMonth").toString());
        mensajeDiferencia = mensajeDiferencia + comparaCadenas(datosInformacionActual.get("birthYear"),informacionCompletaEsperada.get("birthYear").toString());

        if(mensajeDiferencia.length()!=0){
            cargarMensajeResultadoTestNOk(testResult,mensajeDiferencia);
        }else{
            cargarMensajeResultadoTestOk(testResult,"");
        }

    }

    @DataProvider(parallel = true)
    public Object[][] dp_cp001(Method testMethod) throws Exception {
        Object[][] devolver = new Object[1][1];
        String excelPath = System.getProperty("user.dir")+"\\src\\main\\resources\\excel\\cp001.xlsx";
        HashMap<Object,Object> mapa = Excel.devolverMapaExcel(excelPath);
        devolver[0][0] = mapa;
        return devolver;
    }

    @DataProvider(parallel = true)
    public Object[][] dp_cp002(Method testMethod) throws Exception {
        Object[][] devolver = new Object[1][1];
        String excelPath = System.getProperty("user.dir")+"\\src\\main\\resources\\excel\\cp002.xlsx";
        HashMap<Object,Object> mapa = Excel.devolverMapaExcel(excelPath);
        devolver[0][0] = mapa;
        return devolver;
    }

    @DataProvider(parallel = true)
    public Object[][] dp_cp003(Method testMethod) throws Exception {
        Object[][] devolver = new Object[1][1];
        String excelPath = System.getProperty("user.dir")+"\\src\\main\\resources\\excel\\cp003.xlsx";
        HashMap<Object,Object> mapa = Excel.devolverMapaExcel(excelPath);
        devolver[0][0] = mapa;
        return devolver;
    }
    @DataProvider(parallel = true)
    public Object[][] dp_cp004(Method testMethod) throws Exception {
        Object[][] devolver = new Object[1][1];
        String excelPath = System.getProperty("user.dir")+"\\src\\main\\resources\\excel\\cp004.xlsx";
        HashMap<Object,Object> mapa = Excel.devolverMapaExcel(excelPath);
        devolver[0][0] = mapa;
        return devolver;
    }


    @AfterMethod
    public void tearDown(ITestResult testResult) throws IOException, URISyntaxException {
        if(Boolean.parseBoolean(ConfigurationProperties.sauceLabsMode)){
            if (testResult.isSuccess()) {
                mark("passed");
            } else {
                mark("failed");
            }
        }
        if (Driver.getInstance().getDriver() != null) {
            Driver.getInstance().getDriver().quit();
        }
    }
    public static void mark(String status) throws URISyntaxException, UnsupportedEncodingException, IOException {
        WebDriver  driver = Driver.getInstance().getDriver();
        SessionId sessionid = ((RemoteWebDriver) driver).getSessionId();
        URI uri = new URI("https://"+ConfigurationProperties.userNameSauce+":"+
                ConfigurationProperties.accesKeySauce+"@api.browserstack.com/automate/sessions/"+sessionid.toString()+".json");
        HttpPut putRequest = new HttpPut(uri);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add((new BasicNameValuePair("status", status)));
        nameValuePairs.add((new BasicNameValuePair("reason", "")));
        putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpClientBuilder.create().build().execute(putRequest);
    }

}
