package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.managers.CommandManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;

public class ExecuteScriptFileNameCommand implements Command {

    private CommandManager commandManager;
    private ArrayDeque<String> scriptStack;
    private String login;
    private String password;

    public ExecuteScriptFileNameCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
        scriptStack = new ArrayDeque<>();
    }

    public String description() {
        return "executes script from file";
    }

    public void execute(String[] arguments, String login, String password) {
        this.login = login;
        this.password = password;
        try {
            if (scriptStack.contains(arguments[1])) {
                System.out.println("Recursive call is forbidden");
                return;
            }
            else {
                scriptStack.add(arguments[1]);
            }
            BufferedReader reader = new BufferedReader(new FileReader(arguments[1]));
            String line = reader.readLine();
            while (line != null) {
                commandManager.executeCommand(line);
                line = reader.readLine();
            }
            reader.close();
            scriptStack.removeLast();
        } catch (FileNotFoundException exception) {
            System.out.println("File doesn't exist");
        } catch (IOException exception) {
            System.out.println("IOE exception");
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
