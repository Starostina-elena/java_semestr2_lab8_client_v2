package org.lia.java_lab8_client_v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.lia.java_lab8_client_v2.controller.BaseController;
import org.lia.java_lab8_client_v2.managers.CommandManager;

import java.io.IOException;

public class App extends Application {

    private Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/mainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        BaseController controller = fxmlLoader.getController();
        controller.setFXApp(this);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        CommandManager commandManager = new CommandManager();
        launch();
    }
}