package org.lia.java_lab8_client_v2.controller;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.lia.java_lab8_client_v2.App;

import org.lia.java_lab8_client_v2.models.Product;

import javafx.fxml.FXML;
import org.lia.java_lab8_client_v2.commands.ShowCommand;
import org.lia.java_lab8_client_v2.tools.Response;

import java.io.IOException;


public class BaseController {
    private App FXApp;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Long> id_field_table;
    @FXML
    private TableColumn<Product, String> name_field_table;
    @FXML
    private TableColumn<Product, Long> coord_x_field_table;
    @FXML
    private TableColumn<Product, Double> coords_y_field_table;
    @FXML
    private TableColumn<Product, String> creation_date_field_table;
    @FXML
    private TableColumn<Product, Integer> price_field_table;
    @FXML
    private TableColumn<Product, String> partnumber_field_table;
    @FXML
    private TableColumn<Product, Integer> manufacture_cost_field_table;
    @FXML
    private TableColumn<Product, String> manufacturer_field_table;
    @FXML
    private TableColumn<Product, Long> creator_id_field_table;
    @FXML
    private AnchorPane visualPane;
    @FXML
    private Tab field_tab;

    public void setFXApp(App FXApp) {
        this.FXApp = FXApp;
    }

    @FXML
    public void initialize() {
        id_field_table.setCellValueFactory(product -> new SimpleLongProperty(product.getValue().getId()).asObject());
        name_field_table.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getName()));
        coord_x_field_table.setCellValueFactory(product -> new SimpleLongProperty(product.getValue().getCoordinates().getX()).asObject());
        coords_y_field_table.setCellValueFactory(product -> new SimpleDoubleProperty(product.getValue().getCoordinates().getY()).asObject());
        creation_date_field_table.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getCreationDate().toString()));
        price_field_table.setCellValueFactory(product -> new SimpleIntegerProperty(product.getValue().getPrice()).asObject());
        partnumber_field_table.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getPartNumber()));
        manufacture_cost_field_table.setCellValueFactory(product -> new SimpleIntegerProperty(product.getValue().getManufactureCost()).asObject());
        manufacturer_field_table.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getManufacturer().getName()));
        creator_id_field_table.setCellValueFactory(product -> new SimpleLongProperty(product.getValue().getUserId()).asObject());
        refresh();
    }

    public void refresh() {
        Thread refresher = new Thread(() -> {
            while (true) {
                Platform.runLater(this::loadCollection);
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread was interrupted, Failed to complete operation");
                }
            }
        });
        refresher.start();
    }

    private void loadCollection() {
        ShowCommand command = new ShowCommand();
        command.execute(new String[] {"show"}, App.commandManager.login, App.commandManager.password);
        Response response = App.commandManager.executeCommandFromObject(command);
        productTable.setItems(FXCollections.observableList(response.getProductCollectionResponse().stream().toList()));
        visualPane.getChildren().clear();
        for (Product c : response.getProductCollectionResponse()) {
            double r = c.getUserId() * 100 % 255 / 255.0;
            double g = (c.getUserId() + 100 * 100) % 255 / 255.0;
            double b = c.getUserId() * 200 % 255 / 255.0;
            double width = App.primaryStage.getWidth();
            double height = App.primaryStage.getHeight();
            double x = width / 2 + (double) c.getCoordinates().getX() * 10;
            double y = height / 2 - c.getCoordinates().getY() * 10;
            if (x < 0) {
                x = 0;
            } else if (x > width * 0.8) {
                x = width * 0.8;
            }
            if (y < 0) {
                y = 0;
            } else if (y > height * 0.8) {
                y = width * 0.8;
            }
            var circle = new Circle(10, Color.color(r, g, b));
            circle.setCenterX(x);
            circle.setCenterY(y);
            visualPane.getChildren().add(circle);
        }
    }

    @FXML
    public void editElement() {
        try {
            var editLoader = new FXMLLoader(App.class.getResource("view/productInfo.fxml"));
            //var editRoot = loadFxml(editLoader);
            var editScene = new Scene(editLoader.load());
            var editStage = new Stage();
            editStage.setScene(editScene);
            editStage.setResizable(false);
            editStage.setTitle("Product");
            ProductInfoController editController = editLoader.getController();
            editController.setFXApp(FXApp);
            try {
                editController.productId = productTable.getSelectionModel().getSelectedItem().getId();
            } catch (NullPointerException ignored) {}
            if (editController.productId > 0) {
                editStage.show();
                editController.loadInfo();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}