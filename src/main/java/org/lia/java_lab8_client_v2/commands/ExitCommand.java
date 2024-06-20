package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.managers.CommandManager;
import org.lia.java_lab8_client_v2.models.Product;

public class ExitCommand implements Command {
    public Product product;
    CommandManager commandManager;
    private String login;
    private String password;
    public String localDescription = "ExitDescr";
    public String getLocalDescription() {
        return localDescription;
    }
    public ExitCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public String description() {
        return "quits from program";
    }

    public void execute(String[] arguments, String login, String password) {
        this.login = login;
        this.password = password;
        System.out.println("goodbye");
        System.exit(0);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
