package org.lia.java_lab8_client_v2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.lia.java_lab8_client_v2.App;
import org.lia.java_lab8_client_v2.commands.*;
import org.lia.java_lab8_client_v2.tools.Response;

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
        creationDateField.setText(response.getProduct().getCreationDate() + "");
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
                manufacturerEmployeesField.getText(),
        }, App.commandManager.login, App.commandManager.password);
        Response response = App.commandManager.executeCommandFromObject(command);
        messageLabel.setText(response.getAnswer().get(0));
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
        messageLabel.setText(response.getAnswer().get(0));
        if (response.getSuccess()) {
            saveProductButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }

}