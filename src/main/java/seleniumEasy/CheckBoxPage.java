package seleniumEasy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.PadrePage;

public class CheckBoxPage  extends PadrePage {

    @FindBy(id= "isAgeSelected")
    WebElement checkBox;

    @FindBy(id ="txtAge")
    WebElement txtDebajoCheckBox;

    public CheckBoxPage(WebDriver driver) {
        super(driver);
    }

    public void seleccionarChecBox(){
        scrollIntoViewElementAndClick(checkBox);
    }

    public boolean checkBoxIsSelected(){
        scrollIntoViewElement(checkBox);
        if(checkBox.isSelected()){
            return true;
        }else{
            return false;
        }
    }

}
