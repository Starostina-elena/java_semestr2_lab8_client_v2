package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.managers.CommandManager;
import org.lia.java_lab8_client_v2.models.Product;

import java.util.Map;

public class HelpCommand implements Command {
    public Product product;
    CommandManager commandManager;
    private String login;
    private String password;
    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public String description() {
        return "provides info on commands";
    }

    public void execute(String[] arguments, String login, String password) {
        this.login = login;
        this.password = password;
        Map<String, Command> commandsLIst = commandManager.getCommandsList();
        try {
            boolean status = false;
            for (String c : commandsLIst.keySet()) {
                if (c.equals(arguments[1])) {
                    System.out.println(c + ": " + commandsLIst.get(c).description());
                    status = true;
                    break;
                }
            }
            if (!status) {
                System.out.println("no such command");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            for (String c : commandsLIst.keySet()) {
                System.out.println(c + ": " + commandsLIst.get(c).description());
            }
        }

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
