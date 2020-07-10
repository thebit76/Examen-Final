package automation.practice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.PadrePage;

import java.util.HashMap;

public class YourPersonalInformationPage extends PadrePage {

    @FindBy(id ="firstname")
    WebElement firstNameTxt;
    @FindBy(id ="lastname")
    WebElement lastNameTxt;
    @FindBy(id = "email")
    WebElement email;

    @FindBy(id = "days")
    WebElement birthDaySelect;
    @FindBy(id = "months")
    WebElement birthMonthsSelect;
    @FindBy(id = "years")
    WebElement birthYearsSelect;

    public YourPersonalInformationPage(WebDriver driver) {
        super(driver);
    }

    public HashMap<String, String> devolverDatosInformacion() {
        HashMap<String,String> mapaInformacion = new HashMap<>();
        mapaInformacion.put("firstName",esperarValueInputNoEsteVacioYDevolver(firstNameTxt));
        mapaInformacion.put("lastName",esperarValueInputNoEsteVacioYDevolver(lastNameTxt));
        mapaInformacion.put("email",esperarValueInputNoEsteVacioYDevolver(email));
        mapaInformacion.put("birthDay",devolverTextSeleccionActualSelect(birthDaySelect));
        mapaInformacion.put("birthMonth",devolverTextSeleccionActualSelect(birthMonthsSelect));
        mapaInformacion.put("birthYear",devolverTextSeleccionActualSelect(birthYearsSelect));
        return mapaInformacion;
    }
}
