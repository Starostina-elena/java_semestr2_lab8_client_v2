package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.models.Product;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class SignUpCommand implements Command {
    public Product product;
    private String login;
    private String passwordString;
    private byte[] passwordByte;
    private String password;

    private static final long serialVersionUID = 1785464768755190753L;

    public String description() {
        return "creates a new user";
    }

    public void execute(String[] arguments, String login, String password) {
        this.login = login;
        this.password = password;
        System.out.println("Enter your name:");
        System.out.print("> ");
        Scanner sc = new Scanner(System.in);
        this.login = sc.nextLine();
        System.out.println("Enter your password:");
        System.out.print("> ");
        passwordString = sc.nextLine();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] passwordByte = md.digest(passwordString.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : passwordByte) {
                hexString.append(String.format("%02x", b));
            }
            passwordString = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return passwordString;
    }

}
