package org.lia.java_lab8_client_v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.lia.java_lab8_client_v2.controller.BaseController;
import org.lia.java_lab8_client_v2.controller.LoginController;
import org.lia.java_lab8_client_v2.managers.CommandManager;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class App extends Application {

    public static Stage primaryStage;
    public static CommandManager commandManager;
    public ResourceBundle local_bundle;

    @Override
    public void start(Stage stage) throws IOException {
        local_bundle = ResourceBundle.getBundle("locales/gui", new Locale("en", "EN"));
        this.primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController controller = fxmlLoader.getController();
        controller.setFXApp(this);
        controller.setLanguage();
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
            controller.setLanguage();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Collection App");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}