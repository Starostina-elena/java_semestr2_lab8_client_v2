package org.lia.java_lab8_client_v2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.lia.java_lab8_client_v2.App;
import org.lia.java_lab8_client_v2.commands.*;
import org.lia.java_lab8_client_v2.tools.Response;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ProductInfoController {
    private App FXApp;
    public long productId = 0;

    @FXML
    private Label idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField coordsXField;
    @FXML
    private TextField coordsYField;
    @FXML
    private Label creationDateField;
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
    private Label creatorIdField;
    @FXML
    private Label messageLabel;
    @FXML
    private Button saveProductButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label coordXLabel;
    @FXML
    private Label creationDateLabel;
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
    private Label creatorIdLabel;
    @FXML
    private Label coordsYLabel;

    public void setFXApp(App FXApp) {
        this.FXApp = FXApp;
    }

    public void loadInfo() {
        GetByIdCommand command = new GetByIdCommand();
        command.execute(new String[] {"get_by_id", String.valueOf(productId)}, App.commandManager.login, App.commandManager.password);
        Response response = App.commandManager.executeCommandFromObject(command);
        idField.setText("" + response.getProduct().getId());
        nameField.setText(response.getProduct().getName());
        coordsXField.setText(response.getProduct().getCoordinates().getX() + "");
        coordsYField.setText(response.getProduct().getCoordinates().getY() + "");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(FXApp.local_bundle.getLocale());
        creationDateField.setText(response.getProduct().getCreationDate().toLocalDate().format(formatter));
        priceField.setText(response.getProduct().getPrice() + "");
        partNumberField.setText(response.getProduct().getPartNumber());
        manufactureCostField.setText(response.getProduct().getManufactureCost() + "");
        manufacturerField.setText(response.getProduct().getManufacturer().getName());
        manufacturerFullnameField.setText(response.getProduct().getManufacturer().getFullName());
        if (response.getProduct().getManufacturer().getEmployeesCount() == null) {
            manufacturerEmployeesField.setText("0");
        } else {
            manufacturerEmployeesField.setText(response.getProduct().getManufacturer().getEmployeesCount() + "");
        }
        creatorIdField.setText(response.getProduct().getUserId() + "");
    }

    @FXML
    public void saveProduct() {
        UpdateCommand command = new UpdateCommand();
        String employees = "0";
        if (!manufacturerEmployeesField.getText().isBlank()) {
            employees = manufacturerEmployeesField.getText();
        }
        try {
            command.execute(new String[] {
                    "update",
                    String.valueOf(productId),
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
            messageLabel.setText(FXApp.local_bundle.getString(response.getAnswer().get(0)));
        } catch (MissingResourceException e) {
            messageLabel.setText(response.getAnswer().get(0));
        }
        loadInfo();
    }

    @FXML
    public void deleteProduct() {
        RemoveByIdCommand command = new RemoveByIdCommand();
        command.execute(
                new String[] {
                        "remove_by_id",
                        idField.getText()
                }, App.commandManager.login, App.commandManager.password
        );
        Response response = App.commandManager.executeCommandFromObject(command);
        try {
            messageLabel.setText(FXApp.local_bundle.getString(response.getAnswer().get(0)));
        } catch (MissingResourceException e) {
            messageLabel.setText(response.getAnswer().get(0));
        }
        if (response.getSuccess()) {
            saveProductButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }

    public void setLanguage() {
        ResourceBundle bundle = this.FXApp.local_bundle;
        idLabel.setText(bundle.getString("Id"));
        nameLabel.setText(bundle.getString("Name"));
        coordXLabel.setText(bundle.getString("Coordinates_(x)"));
        creationDateLabel.setText(bundle.getString("Creation_date"));
        priceLabel.setText(bundle.getString("Price"));
        partNumberLabel.setText(bundle.getString("Part_Number"));
        manufactureCostLabel.setText(bundle.getString("Manufacture_Cost"));
        manufacturerNameLabel.setText(bundle.getString("Manufacturer"));
        manufacturerFullNameLabel.setText(bundle.getString("Manufacturer_Full_Name"));
        manufacturerEmployeesLabel.setText(bundle.getString("Manufacturer_Employees"));
        creatorIdLabel.setText(bundle.getString("Creator_Id"));
        saveProductButton.setText(bundle.getString("Save"));
        deleteButton.setText(bundle.getString("Delete"));
        coordsYLabel.setText(bundle.getString("Coordinates_(y)"));
    }

}