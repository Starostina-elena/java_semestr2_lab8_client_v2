package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.models.Product;

public class ClearCommand implements Command {
    public Product product;

    private static final long serialVersionUID = 1785464768755190753L;
    private String login;
    private String password;

    public String description() {
        return "clears collection";
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
