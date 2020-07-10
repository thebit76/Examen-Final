package automation.practice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.PadrePage;

import java.util.HashMap;

public class SignInPage extends PadrePage {

    @FindBy(id="email")
    WebElement emailTxt;
    @FindBy(id="passwd")
    WebElement passwordTxt;
    @FindBy(id="SubmitLogin")
    WebElement btnSignIn;
    @FindBy(className = "alert-danger")
    WebElement mensajeError;

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void ingresarCredenciales(HashMap<Object,Object> inputData){
        scrollIntoViewElementAndSendKeys(emailTxt   ,inputData.get("email").toString());
        scrollIntoViewElementAndSendKeys(passwordTxt,inputData.get("password").toString());
        scrollIntoViewElementAndClick(btnSignIn);
    }
    public String devolverMensajeError(){
        scrollIntoViewElement(mensajeError);
        return esperarTextoNoEsteVacioYDevolver(mensajeError);
    }



}
