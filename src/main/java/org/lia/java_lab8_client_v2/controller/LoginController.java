package org.lia.java_lab8_client_v2.controller;

import javafx.scene.control.TextField;
import org.lia.java_lab8_client_v2.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.lia.java_lab8_client_v2.commands.LoginCommand;
import org.lia.java_lab8_client_v2.commands.SignUpCommand;
import org.lia.java_lab8_client_v2.tools.Response;

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

    public void setFXApp(App FXApp) {
        this.FXApp = FXApp;
    }

    @FXML
    void okLogin() {
        if (login_login.getText().isBlank() || password_login.getText().isBlank()) {
            login_message_label.setText("Login or password are empty");
        } else {
            LoginCommand command = new LoginCommand();
            command.execute(new String[] {"login", login_login.getText(), password_login.getText()}, "", "");
            Response response = App.commandManager.executeCommandFromObject(command);
            if (response.getSuccess()) {
                App.commandManager.login = command.getLogin();
                App.commandManager.password = command.getPassword();
                FXApp.startMainWindow();

            } else {
                login_message_label.setText("Wrong login or password. Please try again");
            }
        }
    }

    @FXML
    void okSignUp() {
        if (login_signup.getText().isBlank() || password_signup.getText().isBlank()) {
            signup_message_label.setText("Login or password are empty");
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
}