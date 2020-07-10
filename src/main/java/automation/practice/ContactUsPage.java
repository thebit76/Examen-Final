package automation.practice;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import util.PadrePage;

import java.util.HashMap;

public class ContactUsPage extends PadrePage {

    @FindBy(id = "id_contact")
    WebElement selectSubjectHeading;

    @FindBy(id ="email")
    WebElement txtEmail;

    @FindBy(id ="id_order")
    WebElement txtOrderReference;

    @FindBy(id ="fileUpload")
    WebElement inputFileUpload;

    @FindBy(id = "message")
    WebElement textAreaMessage;

    @FindBy(id = "submitMessage")
    WebElement btnSend;

    @FindBy(xpath = "//p[@class='alert alert-success']")
    WebElement mensajeConfirmacion;

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    public void llenarYEnviarFormulario(HashMap<Object, Object> inputData) {
        seleccionarPaisXTextoVisible(selectSubjectHeading,inputData.get("subjectHeading").toString());
        //Select selectSujectHeading = new Select(selectSubjectHeading);
        //selectSujectHeading.selectByVisibleText(inputData.get("subjectHeading").toString());
        scrollIntoViewElementAndSendKeys(txtEmail,inputData.get("email").toString());
        scrollIntoViewElementAndSendKeys(txtOrderReference,inputData.get("orderReference").toString());
        subirArchivo(inputFileUpload,System.getProperty("user.dir")+"\\"+inputData.get("filePath").toString());
        JSONArray mensaje = (JSONArray) inputData.get("mensajeConfirmacion");
        scrollIntoViewElementAndSendKeys(textAreaMessage,((JSONObject)mensaje.get(0)).get("message").toString());
        scrollIntoViewElementAndClick(btnSend);
    }

    public HashMap<Object, Object> devolverDatosMensaje() {
        HashMap<Object, Object> mensajeActual = new HashMap<>();
        scrollIntoViewElement(mensajeConfirmacion);
        mensajeActual.put("message",mensajeConfirmacion.getText());
        mensajeActual.put("background-color",mensajeConfirmacion.getCssValue("background-color"));
        mensajeActual.put("border-color",mensajeConfirmacion.getCssValue("border-color"));
        mensajeActual.put("color",mensajeConfirmacion.getCssValue("color"));

        return mensajeActual;
    }
}
