package pl.kmiecik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmiecik.Utils.FormatToPrintString;
import pl.kmiecik.Utils.IpClient;
import pl.kmiecik.Utils.RS232;
import pl.kmiecik.Utils.Zebra;

import java.util.Arrays;
import java.util.List;

public class AppScreenController {

//FD20111602641T28738159
    private final Logger logger = LoggerFactory.getLogger(StackPaneControllerCopy.class);

    List<String> list = Arrays.asList("124", "125");

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    private MainScreenController mainScreenController;

    @FXML
    private TextField input2DCode;


    @FXML
    private Label myStatusLabel;


    /**
     * FIS server  "10.235.241.235", 24401
     * <p>
     * Check if newid wasn't used
     * request   BREQ|id=125|process=REFLASH|station=BMW_RM_REFLASH_1|newid=124
     * response  BCNF|id=125|status=PASS|
     * <p>
     * Send
     * request   BCMP|id=125|newid=124|station=BMW_RM_REFLASH_1|process=REFLASH|status=PASS|endmodel=11111111|errorcode=
     * response  BACK|id=125|status=PASS
     */

    @FXML
    public void sendToFis(ActionEvent actionEvent) {
        System.out.println("SEND TO FIS " + actionEvent.getClass().getCanonicalName());
        String oldNumber = list.get(0);
        String newNumber = list.get(1);
        sendMessageToFis(oldNumber, newNumber);

        swap();

    }

    private void sendMessageToFis(String oldNumber, String newNumber) {
        try (IpClient ipClient = new IpClient()) {
            //  final String actual = ipClient.sendAndReceiveIPMessage("10.235.241.235", 24364, "HandleGETSTATIONLIST|");


            String message = "BREQ|id=" + oldNumber + "|process=REFLASH|station=BMW_RM_REFLASH_1|newid=" + newNumber;
            String actual = ipClient.sendAndReceiveIPMessage("10.235.241.235", 24401, message);
            System.out.println("FIS Response : " + actual);
            myStatusLabel.setText("FIS Response : " + actual);
            logger.info("Sent: " + message + "Resp: " + actual);

            if (actual.contains("status=PASS")) {
                message = "BCMP|id=" + oldNumber + "|newid=" + newNumber + "|station=BMW_RM_REFLASH_1|process=REFLASH|status=PASS|endmodel=11111111|errorcode=";
                actual = ipClient.sendAndReceiveIPMessage("10.235.241.235", 24401, message);
                System.out.println("FIS Response : " + actual);
                myStatusLabel.setText("FIS Response : " + actual);
                logger.info("Sent: " + message + "Resp: " + actual);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void swap() {
        String temp = list.get(0);
        list.set(0, list.get(1));
        list.set(1, temp);
    }

    public void OnKeyTyped(KeyEvent event) {

        if (event.getCode().equals(KeyCode.ENTER) || event.getCharacter().getBytes()[0] == '\n' || event.getCharacter().getBytes()[0] == '\r') {
            logger.info("dane wprowadzone= " + input2DCode.getText());
            System.out.println("dane wprowadzone= " + input2DCode.getText());
            myStatusLabel.setText("input2DCode.getText()");

            //   String input="2811223320113010001";
            FormatToPrintString formatToPrintString = new FormatToPrintString();
            String prepareStringToPrint = ReplaceAPN(formatToPrintString);

            logger.info("PrepareStringToPrint " + prepareStringToPrint);
            System.out.println(prepareStringToPrint);
            myStatusLabel.setText(prepareStringToPrint);

            //print(prepareStringToPrint);


            //sendMessageToFis(formatToPrintString.getCurrentAPN(), formatToPrintString.getNewAPN());


            input2DCode.clear();

        }

    }

    private String ReplaceAPN(FormatToPrintString formatToPrintString) {
        String prepareStringToPrint = formatToPrintString.prepareStringToPrint(input2DCode.getText());
        return prepareStringToPrint;
    }

    private void print(String prepareStringToPrint) {
        String zpl = "^XA\n" +
                "^LH25,15^MMP\n" +
                "^BY144,144^FT120,230^BXN,12,200,0,0,1\n" +
                "^FH\\^FD<BARCODE>^FS\n" +
                "^FT20,300^A0N,45,33^FH\\^FD<DPN>^FS\n" +
                "^XZ";
        RS232 rs232 = new RS232("COM3", 9600);
        Zebra zebra = new Zebra(zpl, rs232);
        zebra.open();
        String acknoladge = zebra.sendAndReceiveData(prepareStringToPrint);
      /*
        String printerIsReady = zebra.sendAndReceivedCommand("~HS");
        if (printerIsReady.equals("")) {
            myStatusLabel.setText("printer is ready");
            String acknoladge = zebra.sendAndReceiveData(prepareStringToPrint);
            myStatusLabel.setText("label was printed");
        }*/
        zebra.close();
        logger.info("Print " + prepareStringToPrint);
    }


    public void backMenu(ActionEvent actionEvent) {

        mainScreenController.loadMenuScreen();
    }
}
