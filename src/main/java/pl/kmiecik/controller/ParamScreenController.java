package pl.kmiecik.controller;

import javafx.event.ActionEvent;

public class ParamScreenController {

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    private MainScreenController mainScreenController;

    public void backToMenu(ActionEvent actionEvent) {
        mainScreenController.loadMenuScreen();
    }


}
