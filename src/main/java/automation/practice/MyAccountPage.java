package automation.practice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.PadrePage;

public class MyAccountPage extends PadrePage {

    @FindBy(xpath = "//h1[@class='page-heading' and text()='My account']")
    WebElement myAccountHeader;
    @FindBy(linkText = "My addresses")
    WebElement myAddressesBtn;
    @FindBy(xpath = "//a[@title='Information']//span")
    WebElement myPersonalInformationBtn;

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }
    public boolean verifificarSiCargoPagina(){
        if(cargoElemento(myAccountHeader)){
            return true;
        }else{
            return false;
        }
    }
    public void ingresarMyAddresses(){
        scrollIntoViewElementAndClick(myAddressesBtn);
    }
    public void ingresarMyPersonaInformation(){
        scrollIntoViewElementAndClick(myPersonalInformationBtn);
    }
}
