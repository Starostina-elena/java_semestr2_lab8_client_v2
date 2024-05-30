package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.models.Product;

import java.io.Serializable;

public interface Command extends Serializable {
    void execute(String[] arguments, String login, String password);
    String description();
    public Product product = null;
    String login = null;
    String password = null;
    public String getLogin();
    public String getPassword();
}
