package org.lia.java_lab8_client_v2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.lia.java_lab8_client_v2.App;
import org.lia.java_lab8_client_v2.commands.*;
import org.lia.java_lab8_client_v2.tools.Response;

public class ProductAddController {
    private App FXApp;
    public long productId = 0;

    @FXML
    private TextField nameField;
    @FXML
    private TextField coordsXField;
    @FXML
    private TextField coordsYField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField partNumberField;
    @FXML
    private TextField manufactureCostField;
    @FXML
    private TextField manufacturerField;
    @FXML
    private TextField manufacturerFullnameField;
    @FXML
    private TextField manufacturerEmployeesField;
    @FXML
    private Label messageLabel;
    @FXML
    private CheckBox addIfMaxCheckBox;


    public void setFXApp(App FXApp) {
        this.FXApp = FXApp;
    }

    @FXML
    public void saveProduct() {
        Command command;
        if (addIfMaxCheckBox.isSelected()) {
            command = new AddIfMaxCommand();
        } else {
            command = new AddCommand();
        }
        command.execute(new String[] {
                "add",
                nameField.getText(),
                priceField.getText(),
                partNumberField.getText(),
                manufactureCostField.getText(),
                coordsXField.getText(),
                coordsYField.getText(),
                " ",
                manufacturerField.getText(),
                manufacturerFullnameField.getText(),
                manufacturerEmployeesField.getText(),
        }, App.commandManager.login, App.commandManager.password);
        Response response = App.commandManager.executeCommandFromObject(command);
        messageLabel.setText(response.getAnswer().get(0));
    }

}