package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.models.Product;

public class ShowCommand implements Command {
    public Product product;
    private static final long serialVersionUID = 1785464768755190753L;
    private String login;
    private String password;
    public String localDescription = "ShowDescr";
    public String getLocalDescription() {
        return localDescription;
    }

    public String description() {
        return "shows elements in collection";
    }

    public void execute(String[] arguments, String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
