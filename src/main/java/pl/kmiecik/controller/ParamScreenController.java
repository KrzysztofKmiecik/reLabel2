package pl.kmiecik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pl.kmiecik.Utils.MyJsonParameters;
import pl.kmiecik.Utils.Parameters;

import java.util.Optional;

public class ParamScreenController {


    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    private MainScreenController mainScreenController;

    public String getIpAddress() {
        return ipAddress.getText();
    }

    public int getIpPort() {
        return Integer.parseInt(ipPort.getText());
    }

    public String getComPort() {
        return comPort.getText();
    }

    public int getBoundRate() {
        return Integer.parseInt(boundRate.getText());
    }

    @FXML
    public void initialize() {

        MyJsonParameters myJsonParameters =new MyJsonParameters();
        Optional<Parameters> parametersOptional = myJsonParameters.read("src/main/resources/parameters.json");
        if(parametersOptional.isPresent()){
            ipAddress.setText(parametersOptional.get().getIpAddress());
            ipPort.setText( String.valueOf(parametersOptional.get().getIpPort()));
            comPort.setText(parametersOptional.get().getComPort());
            boundRate.setText(String.valueOf(parametersOptional.get().getBoundRate()));
        }
    }

    @FXML
    private TextField ipAddress;
    @FXML
    private TextField ipPort;
    @FXML
    private TextField comPort;
    @FXML
    private TextField boundRate;

    @FXML
    public void backToMenu() {
        mainScreenController.loadMenuScreen();
    }

    @FXML
    public void pressSave(ActionEvent actionEvent) {
        Parameters parameters=new Parameters(ipAddress.getText(),Integer.parseInt(ipPort.getText()),comPort.getText(),Integer.parseInt(boundRate.getText()));
        MyJsonParameters myJsonParameters=new MyJsonParameters();
        myJsonParameters.save("src/main/resources/parameters.json",parameters);

    }
}
