package pl.kmiecik.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MenuScreenController {

    private MainScreenController mainScreenController;

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    @FXML
    public void openApplication() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/AppScreen.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AppScreenController appScreenController = loader.getController();
        appScreenController.setMainScreenController(mainScreenController);
        mainScreenController.setScreen(pane);
    }

    @FXML
    public void openParameters() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/ParamScreen.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ParamScreenController paramScreenController = loader.getController();
        paramScreenController.setMainScreenController(mainScreenController);
        mainScreenController.setScreen(pane);
    }

    @FXML
    public void exit() {
        Platform.exit();
    }
}
