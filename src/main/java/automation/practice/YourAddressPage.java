package automation.practice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.PadrePage;

import java.util.HashMap;

public class YourAddressPage  extends PadrePage {

    @FindBy(id ="address1")
    WebElement addressTxt;
    @FindBy(id = "city")
    WebElement cityTxt;
    @FindBy(id = "id_state")
    WebElement stateSelect;
    @FindBy(id = "postcode")
    WebElement zipCodeTxt;
    @FindBy(id = "id_country")
    WebElement countrySelect;

    public YourAddressPage(WebDriver driver) {
        super(driver);
    }

    public HashMap<String,String> devolverDatosDireccion(){
        HashMap<String,String> mapaDireccion = new HashMap<>();
        mapaDireccion.put("address",esperarValueInputNoEsteVacioYDevolver(addressTxt));
        mapaDireccion.put("city",esperarValueInputNoEsteVacioYDevolver(cityTxt));
        mapaDireccion.put("state",devolverTextSeleccionActualSelect(stateSelect));
        mapaDireccion.put("zipCode",esperarValueInputNoEsteVacioYDevolver(zipCodeTxt));
        mapaDireccion.put("country",devolverTextSeleccionActualSelect(countrySelect));
        return mapaDireccion;
    }

}
