package cap15;

import automation.practice.MainPage;
import automation.practice.MyAccountPage;
import automation.practice.SignInPage;
import driver.Driver;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import util.Excel;

import javax.mail.*;
import javax.mail.search.SubjectTerm;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import static util.reporter.MensajesReporte.cargarMensajeResultadoTestNOk;
import static util.reporter.MensajesReporte.cargarMensajeResultadoTestOk;

public class APIMailTest {

    @Test
    public void leyendoCorreo() throws Exception {

        Store store;
        Folder folder;
        String email = "aprendiendoselenium@gmail.com";
        String password = "afp123$$";

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.ssl.trust", "*");

        Session session = Session.getDefaultInstance(props, null);
        store = session.getStore("imaps");
        store.connect("imap.gmail.com", email, password);
        Thread.sleep(5000);

        folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);


        Message[] messages = null;
        messages = folder.search(new SubjectTerm("Su cuenta Oracle:"),
                folder.getMessages());

        Message buscado = null;
        boolean fueEncontradoCorreo=false;
        for (Message mail : messages) {
            if (!mail.isSet(Flags.Flag.SEEN)) {
                buscado = mail;
                //System.out.println("Message Count is: " + taxActEmail.getMessageNumber());
                fueEncontradoCorreo = true;
                break;
            }
        }
        if(!fueEncontradoCorreo){
            throw new Exception("No fue encontrado el correo");
        }else{
            System.out.println("Mensaje encontrado");
            System.out.println("Asunto: "+buscado.getSubject());

            System.out.println("Body  : "+buscado.getContent().toString());
            int ini=0;
            int fin=0;
            String cadena= buscado.getContent().toString();
            String temporal = ";\">Verificar dirección de correo electrónico</strong></a></td>";
            String textoExtraido = cadena.substring(cadena.indexOf(temporal)+3,cadena.indexOf("</strong></a></td>"));;
            System.out.println("Texto extraído: "+textoExtraido);


        }


    }


}
