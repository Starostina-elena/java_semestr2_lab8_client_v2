package org.lia.java_lab8_client_v2.commands;

import javafx.scene.control.TextArea;
import org.lia.java_lab8_client_v2.managers.CommandManager;
import org.lia.java_lab8_client_v2.models.Product;
import org.lia.java_lab8_client_v2.tools.Response;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ResourceBundle;

public class ExecuteScriptFileNameCommand implements Command {

    private CommandManager commandManager;
    private ArrayDeque<String> scriptStack = new ArrayDeque<>();
    private String login;
    private String password;
    private TextArea outputField;
    private ResourceBundle bundle;

    public ExecuteScriptFileNameCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
        outputField = null;
    }

    public ExecuteScriptFileNameCommand(CommandManager commandManager, TextArea outputField, ResourceBundle bundle) {
        this.commandManager = commandManager;
        this.outputField = outputField;
        this.bundle = bundle;
    }

    public String description() {
        return "executes script from file";
    }

    public void execute(String[] arguments, String login, String password) {
        this.login = login;
        this.password = password;
        try {
            if (scriptStack.contains(arguments[1])) {
                if (outputField == null) {
                    System.out.println("Recursive call is forbidden");
                } else {
                    outputField.setText(outputField.getText() + "\n" + bundle.getString("Recursive_call_is_forbidden"));
                }
                return;
            }
            else {
                scriptStack.add(arguments[1]);
            }
            BufferedReader reader = new BufferedReader(new FileReader(arguments[1]));
            String line = reader.readLine();
            while (line != null) {
                Response response = commandManager.executeCommand(line);
                if (outputField == null) {
                    response.getAnswer().forEach(System.out::println);
                    if (response.getProductCollectionResponse() != null) {
                        for (Product c: response.getProductCollectionResponse()) {
                            System.out.println(c);
                        }
                    }
                } else {
                    for (String c: response.getAnswer()) {
                        outputField.setText(outputField.getText() + "\n" + c);
                    }
                    if (response.getProductCollectionResponse() != null) {
                        for (Product c: response.getProductCollectionResponse()) {
                            outputField.setText(outputField.getText() + "\n" + c);
                        }
                    }
                }
                line = reader.readLine();
            }
            reader.close();
            scriptStack.removeLast();
        } catch (FileNotFoundException exception) {
            if (outputField == null) {
                System.out.println("File doesn't exist");
            } else {
                outputField.setText(outputField.getText() + "\n" + bundle.getString("File_doesnt_exist"));
            }

        } catch (IOException exception) {
            if (outputField == null) {
                System.out.println("IOE exception");
            } else {
                outputField.setText(outputField.getText() + "\nIOE exception");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect number of arguments for execute_script_file_name command. Please try again");
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
