package org.lia.java_lab8_client_v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.lia.java_lab8_client_v2.controller.BaseController;
import org.lia.java_lab8_client_v2.controller.LoginController;
import org.lia.java_lab8_client_v2.managers.CommandManager;

import java.io.IOException;

public class App extends Application {

    public static Stage primaryStage;
    public static CommandManager commandManager;
    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController controller = fxmlLoader.getController();
        controller.setFXApp(this);
        stage.setScene(scene);
        stage.setTitle("Collection App");
        stage.show();
    }

    public static void main(String[] args) {
        commandManager = new CommandManager();
        Thread thread = new Thread(commandManager);
        thread.start();
        launch();
    }

    public void startMainWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/mainWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            BaseController controller = fxmlLoader.getController();
            controller.setFXApp(this);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Collection App");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}