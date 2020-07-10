package automation.practice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.PadrePage;

public class MyAddressesPage  extends PadrePage {

    @FindBy(linkText = "Update")
    WebElement updateBtn;

    public MyAddressesPage(WebDriver driver) {
        super(driver);
    }
    public void irASeccionActualizarDireccion(){
        scrollIntoViewElementAndClick(updateBtn);
    }
}
