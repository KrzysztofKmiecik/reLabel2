package pl.kmiecik.controller;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmiecik.Utils.*;

import java.util.Optional;

public class AppScreenController {

    private final Logger logger = LoggerFactory.getLogger(AppScreenController.class);

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    private MainScreenController mainScreenController;

    private String ipAddress;
    private int ipPort;
    private String comPort;
    private int boundRate;

    @FXML
    public void initialize() {
        MyJsonParameters myJsonParameters =new MyJsonParameters();
        Optional<Parameters> parametersOptional = myJsonParameters.read("src/main/resources/parameters.json");
        if(parametersOptional.isPresent()){
        ipAddress=parametersOptional.get().getIpAddress();
        ipPort=parametersOptional.get().getIpPort();
        comPort=parametersOptional.get().getComPort();
        boundRate=parametersOptional.get().getBoundRate();
        }
    }

    @FXML
    private TextField input2DCode;

    @FXML
    private Label myStatusLabel;

    @FXML
    public void OnKeyTyped(KeyEvent event) {
        boolean passResult = false;
        String zpl = "^XA\n" +
                "^LH25,15^MMP\n" +
                "^BY144,144^FT120,230^BXN,12,200,0,0,1\n" +
                "^FH\\^FD<BARCODE>^FS\n" +
                "^FT20,300^A0N,45,33^FH\\^FD<DPN>^FS\n" +
                "^XZ";

      /*  Zebra zebra = new Zebra(zpl, new RS232("", 9600));
        Fis fis = new Fis("10.235.241.235", 24401);*/

        Zebra zebra = new Zebra(zpl, new RS232(comPort, boundRate));
        Fis fis = new Fis(ipAddress, ipPort);
        if (event.getCode().equals(KeyCode.ENTER) || event.getCharacter().getBytes()[0] == '\n' || event.getCharacter().getBytes()[0] == '\r') {

            String old2DCode = input2DCode.getText();

            FormatToPrintString formatToPrintString = new FormatToPrintString();
            String new2DCode = formatToPrintString.prepareStringToPrint(input2DCode.getText());
            String sendMessageToFisResult = fis.sendMessageToFis(old2DCode, new2DCode, formatToPrintString.getNewAPN());

            if (sendMessageToFisResult.contains("status=PASS")) passResult = true;

            if (passResult) zebra.print(new2DCode);

            logger.info("Result= " + passResult + ";Input= " + old2DCode + ";Output= " + new2DCode + ";FISresult= " + sendMessageToFisResult);
            System.out.println("Input= " + old2DCode + "Output= " + new2DCode);
            myStatusLabel.setText("Result= " + passResult + "\nInput     = " + old2DCode
                    + "\nOutput  = " + new2DCode
            + "\nFISresult= " + sendMessageToFisResult);
            input2DCode.clear();
        }
    }

    @FXML
    public void backMenu() {
        mainScreenController.loadMenuScreen();
    }
}
