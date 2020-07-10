package seleniumEasy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.PadrePage;

public class ModalDemo extends PadrePage {
    @FindBy(xpath = "//*[@href='#myModal0']")
    WebElement btnLaunchModal;

    @FindBy(tagName = "h2")
    WebElement lblTitle;

    public ModalDemo(WebDriver driver) {
        super(driver);
    }

    public void clickEnBtnLaunchModalConWaitAlert(){
        wait.until(ExpectedConditions.elementToBeClickable(btnLaunchModal));
        btnLaunchModal.click();
    }
    public void clickEnBtnLaunchModalSinWaitAlert() throws InterruptedException {
        Thread.sleep(4000);
        btnLaunchModal.click();
    }


}
