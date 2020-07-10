package demoqa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.PadrePage;

import java.io.File;

public class UploadDownloadPage  extends PadrePage {

    @FindBy(id = "downloadButton")
    WebElement downloadBtn;
    @FindBy(id = "uploadFile")
    WebElement uploadBtn;
    @FindBy(id="uploadedFilePath")
    WebElement fakePath;

    public UploadDownloadPage(WebDriver driver) {
        super(driver);
    }

    public void downloadFile(File origenFile){
        scrollIntoViewElementAndClick(downloadBtn);
        esperarYDevolverTrueCuandoArchivoExista(origenFile);
    }
    public void uploadFile(String path){
        scrollIntoViewElementAndSendKeys(uploadBtn,path);
        scrollIntoViewElement(fakePath);
        System.out.println("Fake path: "+fakePath.getText());
    }
}
