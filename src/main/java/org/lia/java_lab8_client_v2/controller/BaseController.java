package org.lia.java_lab8_client_v2.controller;

import org.lia.java_lab8_client_v2.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BaseController {
    private App FXApp;

    public void setFXApp(App FXApp) {
        this.FXApp = FXApp;
    }
}