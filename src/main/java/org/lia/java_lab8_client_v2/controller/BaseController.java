package org.lia.java_lab8_client_v2.controller;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.lia.java_lab8_client_v2.App;

import org.lia.java_lab8_client_v2.commands.ClearCommand;
import org.lia.java_lab8_client_v2.models.Product;

import javafx.fxml.FXML;
import org.lia.java_lab8_client_v2.commands.ShowCommand;
import org.lia.java_lab8_client_v2.tools.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;


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
    @FXML
    private Tab table_tab;
    @FXML
    private Button new_product_button;
    @FXML
    private Button edit_product_button;
    @FXML
    private Button clearButton;
    @FXML
    private ComboBox languageComboBox;

    private final HashMap<String, Locale> localeMap = new HashMap<>() {{
        put("English", new Locale("en", "EN"));
        put("Русский", new Locale("ru", "RU"));
    }};

    public void setFXApp(App FXApp) {
        this.FXApp = FXApp;
    }

    @FXML
    public void initialize() {
        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
        languageComboBox.setValue("English");
        id_field_table.setCellValueFactory(product -> new SimpleLongProperty(product.getValue().getId()).asObject());
        name_field_table.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getName()));
        coord_x_field_table.setCellValueFactory(product -> new SimpleLongProperty(product.getValue().getCoordinates().getX()).asObject());
        coords_y_field_table.setCellValueFactory(product -> new SimpleDoubleProperty(product.getValue().getCoordinates().getY()).asObject());
        price_field_table.setCellValueFactory(product -> new SimpleIntegerProperty(product.getValue().getPrice()).asObject());
        partnumber_field_table.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getPartNumber()));
        manufacture_cost_field_table.setCellValueFactory(product -> new SimpleIntegerProperty(product.getValue().getManufactureCost()).asObject());
        manufacturer_field_table.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getManufacturer().getName()));
        creator_id_field_table.setCellValueFactory(product -> new SimpleLongProperty(product.getValue().getUserId()).asObject());
        refresh();
    }

    @FXML
    public void languageComboBoxChanged() {
        this.FXApp.local_bundle = ResourceBundle.getBundle("locales/gui", localeMap.get(languageComboBox.getValue()));
        setLanguage();
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
            circle.setId(String.valueOf(c.getId()));
            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    editElementWindow(Long.parseLong(event.getPickResult().getIntersectedNode().getId()));
                }
            });
            visualPane.getChildren().add(circle);
        }
    }

    public void editElementWindow(long id) {
        try {
            var editLoader = new FXMLLoader(App.class.getResource("view/productInfo.fxml"));
            var editScene = new Scene(editLoader.load());
            var editStage = new Stage();
            editStage.setScene(editScene);
            editStage.setResizable(false);
            editStage.setTitle(FXApp.local_bundle.getString("Product"));
            ProductInfoController editController = editLoader.getController();
            editController.setFXApp(FXApp);
            editController.setLanguage();
            editController.productId = id;
            editStage.show();
            editController.loadInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editElement() {
        try {
            long id = productTable.getSelectionModel().getSelectedItem().getId();
            if (id > 0) {
                editElementWindow(id);
            }
        } catch (NullPointerException ignored) {}
    }

    @FXML
    public void createElement() {
        try {
            var addLoader = new FXMLLoader(App.class.getResource("view/productAdd.fxml"));
            var editScene = new Scene(addLoader.load());
            var editStage = new Stage();
            editStage.setScene(editScene);
            editStage.setResizable(false);
            editStage.setTitle(FXApp.local_bundle.getString("Product"));
            ProductAddController addController = addLoader.getController();
            addController.setFXApp(FXApp);
            addController.setLanguage();
            editStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clearProducts() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting elements");
        alert.setHeaderText("Do you really want to delete all your elements?");
        alert.setContentText("Press OK to delete and Cancel not to delete");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            ClearCommand command = new ClearCommand();
            command.execute(new String[] {"clear"}, App.commandManager.login, App.commandManager.password);
            Response response = App.commandManager.executeCommandFromObject(command);
            alert.close();
        }
    }

    public void setLanguage() {
        ResourceBundle bundle = this.FXApp.local_bundle;
        table_tab.setText(bundle.getString("Table"));
        id_field_table.setText(bundle.getString("ID"));
        name_field_table.setText(bundle.getString("name"));
        coord_x_field_table.setText(bundle.getString("coords(x)"));
        coords_y_field_table.setText(bundle.getString("coords(y)"));
        creation_date_field_table.setText(bundle.getString("created_at"));
        price_field_table.setText(bundle.getString("price"));
        partnumber_field_table.setText(bundle.getString("part_number"));
        manufacture_cost_field_table.setText(bundle.getString("manufacture_cost"));
        manufacturer_field_table.setText(bundle.getString("manufacturer"));
        creator_id_field_table.setText(bundle.getString("creator_id"));
        new_product_button.setText(bundle.getString("New"));
        edit_product_button.setText(bundle.getString("Edit"));
        clearButton.setText(bundle.getString("Clear"));
        field_tab.setText(bundle.getString("Area"));
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(bundle.getLocale());
        creation_date_field_table.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getCreationDate().toLocalDate().format(formatter)));
    }

}