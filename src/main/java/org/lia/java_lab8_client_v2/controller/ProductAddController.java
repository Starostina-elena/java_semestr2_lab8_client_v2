package org.lia.java_lab8_client_v2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.lia.java_lab8_client_v2.App;
import org.lia.java_lab8_client_v2.commands.*;
import org.lia.java_lab8_client_v2.tools.Response;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

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
    @FXML
    private Label nameLabel;
    @FXML
    private Label coordXLabel;
    @FXML
    private Label coordsYLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label partNumberLabel;
    @FXML
    private Label manufactureCostLabel;
    @FXML
    private Label manufacturerNameLabel;
    @FXML
    private Label manufacturerFullNameLabel;
    @FXML
    private Label manufacturerEmployeesLabel;
    @FXML
    private Button saveProductButton;


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
        String employees = "0";
        if (!manufacturerEmployeesField.getText().isBlank()) {
            employees = manufacturerEmployeesField.getText();
        }
        try {
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
                    employees,
            }, App.commandManager.login, App.commandManager.password);
        } catch (IllegalArgumentException e) {
            messageLabel.setText(e.getMessage());
            return;
        }
        Response response = App.commandManager.executeCommandFromObject(command);
        try {
            messageLabel.setText(this.FXApp.local_bundle.getString(response.getAnswer().get(0)));
        } catch (MissingResourceException e) {
            messageLabel.setText(response.getAnswer().get(0));
        }

    }

    public void setLanguage() {
        ResourceBundle bundle = this.FXApp.local_bundle;
        nameLabel.setText(bundle.getString("Name"));
        coordXLabel.setText(bundle.getString("Coordinates_(x)"));
        coordsYLabel.setText(bundle.getString("Coordinates_(y)"));
        priceLabel.setText(bundle.getString("Price"));
        partNumberLabel.setText(bundle.getString("Part_Number"));
        manufactureCostLabel.setText(bundle.getString("Manufacture_Cost"));
        manufacturerNameLabel.setText(bundle.getString("Manufacturer"));
        manufacturerFullNameLabel.setText(bundle.getString("Manufacturer_Full_Name"));
        manufacturerEmployeesLabel.setText(bundle.getString("Manufacturer_Employees"));
        addIfMaxCheckBox.setText(bundle.getString("Add_if_max"));
        saveProductButton.setText(bundle.getString("Save"));
    }

}