package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.models.Product;

public class RemoveLowerCommand implements Command {
    public Product product;
    private static final long serialVersionUID = 1785464768755190753L;
    private long id;
    private String login;
    private String password;
    public String localDescription = "RemoveLowerDescr";
    public String getLocalDescription() {
        return localDescription;
    }

    public String description() {
        return "delete from collection all elements lower that selected. Pattern: remove_lower (long)id";
    }

    public void execute(String[] arguments, String login, String password) {
        this.login = login;
        this.password = password;
        id = Integer.parseInt(arguments[1]);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
