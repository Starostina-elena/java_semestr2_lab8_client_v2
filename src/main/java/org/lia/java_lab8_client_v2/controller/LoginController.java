package org.lia.java_lab8_client_v2.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import org.lia.java_lab8_client_v2.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.lia.java_lab8_client_v2.commands.LoginCommand;
import org.lia.java_lab8_client_v2.commands.SignUpCommand;
import org.lia.java_lab8_client_v2.tools.Response;

import java.util.ResourceBundle;

public class LoginController {
    private App FXApp;
    @FXML
    private TextField login_login;
    @FXML
    private TextField password_login;
    @FXML
    private TextField login_signup;
    @FXML
    private TextField password_signup;
    @FXML
    private Label signup_message_label;
    @FXML
    private Label login_message_label;
    @FXML
    private Tab login_tab;
    @FXML
    private Tab signup_tab;
    @FXML
    private Button login_button;
    @FXML
    private Button signup_button;

    public void setFXApp(App FXApp) {
        this.FXApp = FXApp;
    }

    @FXML
    void okLogin() {
        if (login_login.getText().isBlank() || password_login.getText().isBlank()) {
            login_message_label.setText(FXApp.local_bundle.getString("Login_or_password_are_empty"));
        } else {
            LoginCommand command = new LoginCommand();
            command.execute(new String[] {"login", login_login.getText(), password_login.getText()}, "", "");
            Response response = App.commandManager.executeCommandFromObject(command);
            if (response.getSuccess()) {
                App.commandManager.login = command.getLogin();
                App.commandManager.password = command.getPassword();
                FXApp.startMainWindow();
            } else {
                login_message_label.setText(FXApp.local_bundle.getString("Wrong_login_or_password"));
            }
        }
    }

    @FXML
    void okSignUp() {
        if (login_signup.getText().isBlank() || password_signup.getText().isBlank()) {
            signup_message_label.setText(FXApp.local_bundle.getString("Login_or_password_are_empty"));
        } else {
            SignUpCommand command = new SignUpCommand();
            command.execute(new String[] {"sign_up", login_signup.getText(), password_signup.getText()}, "", "");
            Response response = App.commandManager.executeCommandFromObject(command);
            if (response.getSuccess()) {
                App.commandManager.login = command.getLogin();
                App.commandManager.password = command.getPassword();
                FXApp.startMainWindow();
            } else {
                signup_message_label.setText(response.getAnswer().get(0));
            }
        }
    }

    public void setLanguage() {
        ResourceBundle bundle = this.FXApp.local_bundle;
        login_tab.setText(bundle.getString("Login"));
        signup_tab.setText(bundle.getString("Sign_Up"));
        login_login.setPromptText(bundle.getString("login"));
        password_login.setPromptText(bundle.getString("password"));
        login_button.setText(bundle.getString("Login"));
        login_signup.setPromptText(bundle.getString("login"));
        password_signup.setPromptText(bundle.getString("password"));
        signup_button.setText(bundle.getString("Sign_Up"));
    }

}