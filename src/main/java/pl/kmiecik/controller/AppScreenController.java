package pl.kmiecik.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
        MyJsonParameters myJsonParameters = new MyJsonParameters();
        Optional<Parameters> parametersOptional = myJsonParameters.read(MyPaths.PARAMETERS);
        if (parametersOptional.isPresent()) {
            ipAddress = parametersOptional.get().getIpAddress();
            ipPort = parametersOptional.get().getIpPort();
            comPort = parametersOptional.get().getComPort();
            boundRate = parametersOptional.get().getBoundRate();
        }
    }

    @FXML
    private TextField input2DCode;

    @FXML
    private Label myStatusLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button led;

    @FXML
    public void OnKeyTyped(KeyEvent event) {

        if (event.getCode().equals(KeyCode.ENTER)
                || event.getCharacter().getBytes()[0] == '\n'
                || event.getCharacter().getBytes()[0] == '\r') {

            toDoList();
        }
    }

    private void toDoList() {
        boolean passResult = false;
        progressBar.setProgress(0);
        final String old2DCode = input2DCode.getText();
        FormatToPrintString formatToPrintString = new FormatToPrintString();
        final String new2DCode = formatToPrintString.prepareStringToPrint(input2DCode.getText());
        progressBar.setProgress(30);

        final Fis fis = new Fis(ipAddress, ipPort);
        String sendMessageToFisResult
                = fis.sendMessageToFis(old2DCode, new2DCode, formatToPrintString.getNewAPN());
        if (fis.isCommunicationResultOK()) passResult = true;
        progressBar.setProgress(60);

        if (passResult) {
            print(new2DCode);
            setLed("-fx-background-color: #00ff00","OK");
        } else {
            setLed("-fx-background-color: #ff0000","NOK");
        }

        log(passResult, old2DCode, new2DCode, sendMessageToFisResult);
        input2DCode.clear();
        progressBar.setProgress(100);
    }

    private void setLed(String style, String text) {
        led.setStyle(style);
        led.setText(text);
    }

    private void log(boolean passResult, String old2DCode, String new2DCode, String sendMessageToFisResult) {
        String str = passResult ? "PASS" : "FAIL";
        logger.info("Result= " + str + ";Input= " + old2DCode + ";Output= " + new2DCode
                + ";FISresult= " + sendMessageToFisResult);
        myStatusLabel.setText("Result= " + str + "\n" + old2DCode
                + " --> " + new2DCode
                + "\nFIS= " + sendMessageToFisResult.replace(";", "\n"));
    }

    private void print(String new2DCode) {
       final  Zebra zebra = new Zebra(new RS232(comPort, boundRate));
        zebra.loadZplFromFile(MyPaths.LABEL_ZPL);
        zebra.print(new2DCode);
    }

    @FXML
    public void backMenu() {
        mainScreenController.loadMenuScreen();
    }



}
