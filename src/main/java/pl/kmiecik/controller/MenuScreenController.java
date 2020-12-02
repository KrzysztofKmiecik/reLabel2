package pl.kmiecik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MenuScreenController {



    private MainScreenController mainScreenController;

    @FXML
    private StackPane mainStackPane;

    public void openApplication(ActionEvent actionEvent) {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/AppScreen.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppScreenController appScreenController=loader.getController();
        appScreenController.setMainScreenController(mainScreenController);

        mainScreenController.setScreen(pane);

    }

    public void openParameters(ActionEvent actionEvent) {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/ParamScreen.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ParamScreenController paramScreenController=loader.getController();
        paramScreenController.setMainScreenController(mainScreenController);

        mainScreenController.setScreen(pane);

    }

    public void exit(ActionEvent actionEvent) {
        System.exit(1);
    }


    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }
}
