package database;

import automationPractice.MainPO;
import automationPractice.MyAccountPO;
import automationPractice.SignInPO;
import consultasDB.PoolData_LoginTest;
import framework.Driver;
import opensourcecms.LoginPage;
import opensourcecms.PrincipalPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utils.ConfigurationProperties;
import utils.JSONHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * Esta clase probará el enfoque data-driven leyendo el input desde un archivo properties
 */
public class LoginDataBaseTest {
    WebDriver driver;

    @BeforeClass
    public void setProperties(){
        ConfigurationProperties.getConfigurationProperties();
    }

    @BeforeMethod
    public void crearDriver() throws Exception {
        Driver.getInstance().setDriver(ConfigurationProperties.navegador);
        driver = Driver.getInstance().getDriver();
        driver.get(ConfigurationProperties.webURL);
    }

    @DataProvider
    public Object[][]  dp_signIn(Method testMethod) throws Exception {

        String email   = "";
        String password = "";
        List<HashMap<String, Object>> consultaBD = PoolData_LoginTest.devolverUsuarioLogin();
        if(consultaBD.size()>0) {
            email    = consultaBD.get(0).get("email").toString();
            password = consultaBD.get(0).get("password").toString();
        }


        Object[][] devolver = new Object[1][2];
        devolver[0][0] = email;
        devolver[0][1] = password;

        return devolver;
    }
    @Test(dataProvider = "dp_signIn")
    public void signIn(String userEmail, String userPassword) throws InterruptedException {
        MainPO mainPO           = new MainPO(driver);
        SignInPO signInPO       = new SignInPO(driver);
        MyAccountPO myAccountPO = new MyAccountPO(driver);

        mainPO.seleccionarBtnSignIn();
        signInPO.ingresarCredenciales(userEmail,userPassword);
        myAccountPO.verificarCargoPaginaCorrectamente();
        Thread.sleep(5000);
    }

    @AfterMethod
    public void cerrarDriver(){
        Driver.getInstance().closeDriver();
    }

}
