package cap15;

import automation.practice.MainPage;
import automation.practice.MyAccountPage;
import automation.practice.SignInPage;
import driver.Driver;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import util.Excel;
import util.reporter.GetScreenShot;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import static util.reporter.MensajesReporte.cargarMensajeResultadoTestNOk;
import static util.reporter.MensajesReporte.cargarMensajeResultadoTestOk;

public class CookiesTest {
    HashMap<String, String> preferencias = new HashMap<String, String>();
    String url_AUT="";
    String excelPath = System.getProperty("user.dir")+"\\report\\excelOutput\\";
    @Factory(dataProvider = "dpBrowsers")
    public CookiesTest(String browserName) {
        preferencias.put("browserName", browserName);
    }
    public CookiesTest(){
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
        url_AUT = "http://automationpractice.com/index.php";
        Driver.getInstance().getDriver().get(url_AUT);
        Driver.getInstance().getDriver().manage().deleteAllCookies();
    }

    @AfterMethod
    public void tearDown(){
        if (Driver.getInstance().getDriver() != null) {
            Driver.getInstance().getDriver().quit();
        }
    }
    @Test(dataProvider = "dp_cp001")
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

        guardarCookies(driver);

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

    @Test
    public void cp002_loginCookies() throws InterruptedException {
        ITestResult testResult = Reporter.getCurrentTestResult();
        WebDriver driver = Driver.getInstance().getDriver();
        driver.manage().deleteAllCookies();
        cargarCookies(driver);

    }

    public void guardarCookies(WebDriver driver){
        File archivoCookies = new File("src\\main\\resources\\cookies\\cookiesAutomationPractice.data");
        try
        {
            // borramos el archivo y lo creamos vacío
            archivoCookies.delete();
            archivoCookies.createNewFile();
            FileWriter fileWriter = new FileWriter(archivoCookies);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // for each para recorrer las cookies y escribirlo en el buffer
            for(Cookie cookie : driver.manage().getCookies())
            {
                bufferedWriter.write((cookie.getName()+";"+cookie.getValue()+";"+cookie.getDomain()+";"+cookie.getPath()+";"+cookie.getExpiry()+";"+cookie.isSecure()));
                bufferedWriter.newLine();
            }
            //cerramos el buffer y el archivo
            bufferedWriter.close();
            fileWriter.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void cargarCookies(WebDriver driver) throws InterruptedException {
        try{
            File file = new File("src\\main\\resources\\cookies\\cookiesAutomationPractice.data");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String strline;
            while((strline=bufferedReader.readLine())!=null){
                StringTokenizer token = new StringTokenizer(strline,";");
                while(token.hasMoreTokens()){
                    String nombre = token.nextToken();
                    String valor = token.nextToken();
                    String dominio = token.nextToken();
                    String path = token.nextToken();
                    Date expiracion = null;

                    String val;
                    if(!(val=token.nextToken()).equals("null"))
                    {
                        //DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
                        expiracion = new Date(new Date().getTime()+60*60*1000*24);
                        //expiracion = df.parse(val);

                    }
                    Boolean esSeguro = new Boolean(token.nextToken()).booleanValue();
                    Cookie cookie = new Cookie(nombre,valor,dominio,path,expiracion,esSeguro);
                    System.out.println("Cookie utilizada: "+cookie);
                    //agregamos la cookie a la sesión
                    //driver.manage().deleteAllCookies();

                    driver.manage().addCookie(cookie);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        driver.navigate().refresh();
        Thread.sleep(15000);
    }





    @DataProvider(parallel = true)
    public Object[][] dp_cp001(Method testMethod) throws Exception {
        Object[][] devolver = new Object[1][1];
        String excelPath = System.getProperty("user.dir")+"\\src\\main\\resources\\excel\\cp001.xlsx";
        HashMap<Object,Object> mapa = Excel.devolverMapaExcel(excelPath);
        devolver[0][0] = mapa;
        return devolver;
    }
}
