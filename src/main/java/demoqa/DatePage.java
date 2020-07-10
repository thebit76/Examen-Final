package demoqa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.PadrePage;

import java.util.Calendar;

public class DatePage extends  PadrePage{

    @FindBy(id="datePickerMonthYearInput")
    WebElement onlyDate;

    public DatePage(WebDriver driver) {
        super(driver);
    }

    public void seleccionarDate(String date){
        scrollIntoViewElementClearAndSendKeys(onlyDate,date);
    }
    public boolean validarFechaMostrada(String date){
        boolean resultado;

        wait.until(ExpectedConditions.attributeToBeNotEmpty(onlyDate,"value"));
        if(onlyDate.getAttribute("value").compareTo(date) == 0){
            resultado = true;
        }else{
            resultado = false;
        }
        return resultado;
    }
}
