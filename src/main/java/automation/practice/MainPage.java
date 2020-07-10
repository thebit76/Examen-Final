package automation.practice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.PadrePage;

public class MainPage extends PadrePage {
    @FindBy(linkText = "Contact us")
    WebElement contactUsBtn;
    @FindBy(linkText = "Contact u")
    WebElement contactUs2;
    @FindBy(partialLinkText = "Sign in")
    WebElement signInBtn;
    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void ingresarAContactUs(){
        scrollIntoViewElementAndClick(contactUsBtn);
    }
    public void ingresarSignIn(){
        scrollIntoViewElementAndClick(signInBtn);
    }

    public void ingresarAContactUsMalaSintaxis() throws InterruptedException {
        clickJSMalaSintaxis(contactUsBtn);
    }
    public void ingresarAContactUsScriptTimeOut() throws InterruptedException {
        clickJSScriptTimeOut(contactUsBtn);
    }


    public void ingresarAContactUsNoSuchElement() {
        contactUs2.click();
    }


    public void ingresarAContactUsTimeOut() {
        scrollIntoViewElementAndClick(contactUs2);
    }
}
