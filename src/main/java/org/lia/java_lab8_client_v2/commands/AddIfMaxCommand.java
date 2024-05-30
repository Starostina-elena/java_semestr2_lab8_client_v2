package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.models.Coordinates;
import org.lia.java_lab8_client_v2.models.Organization;
import org.lia.java_lab8_client_v2.models.Product;
import org.lia.java_lab8_client_v2.models.UnitOfMeasure;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AddIfMaxCommand implements Command {
    private static final long serialVersionUID = 1785464768755190753L;
    private String login;
    private String password;


    public Product product;

    public String description() {
        return "adds new element if this element is bigger than max element in collection. Pattern: " +
                "add_if_max (String)name (Integer)price(may be null, use empty string) (String)partNumber " +
                "(Integer)manufactureCost";
    }

    public void execute(String[] arguments, String login, String password) {
        this.login = login;
        this.password = password;
        try {
            Integer price;
            try {
                if (arguments[2].isBlank()) {
                    price = null;
                } else {
                    price = Integer.parseInt(arguments[2]);
                }
            } catch (NumberFormatException e) {
                System.out.println("price is not correct, try again" + arguments[2]);
                return;
            }
            try {
                Integer.parseInt(arguments[4]);
            } catch (NumberFormatException e) {
                System.out.println("manufactureCost is not correct, try again");
                return;
            }
            Scanner in = new Scanner(System.in);
            Coordinates coords;
            long x;
            double y;
            while (true) {
                try {
                    System.out.println("Enter coordinates (long)x");
                    System.out.print("> ");
                    x = in.nextLong();
                    System.out.println("Enter coordinates (double)y");
                    System.out.print("> ");
                    y = in.nextDouble();
                    coords = new Coordinates(x, y);
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Wrong coordinates, try again");
                    in.nextLine();
                }
            }
            ArrayList<String> unitOfMeasures = new ArrayList<>();
            System.out.println("Enter one of unit of measure:");
            for (UnitOfMeasure c : UnitOfMeasure.values()) {
                System.out.println(c);
                unitOfMeasures.add(c.name());
            }
            System.out.print("> ");
            in.nextLine();
            String unitOfMeasure = in.nextLine().toUpperCase();
            while (!unitOfMeasures.contains(unitOfMeasure) & !unitOfMeasure.isBlank()) {
                System.out.println("Wrong unit of measure, please try again:");
                System.out.print("> ");
                unitOfMeasure = in.nextLine().toUpperCase();
            }
            UnitOfMeasure resUnitOfMeasure;
            if (unitOfMeasure.isBlank()) {
                resUnitOfMeasure = null;
            } else {
                resUnitOfMeasure = UnitOfMeasure.valueOf(unitOfMeasure);
            }
            Organization org;
            while (true) {
                try {
                    System.out.println("Enter organization (String)name");
                    System.out.print("> ");
                    String name = in.nextLine();
                    System.out.println("Enter organization (String)fullName. Press enter to leave this field empty");
                    System.out.print("> ");
                    String fullName = in.nextLine();
                    if (fullName.isBlank()) {
                        fullName = null;
                    }
                    Integer employeesCount;
                    while (true) {
                        try {
                            System.out.println("Enter organization (Integer)employeesCount. Press enter to leave this field empty");
                            System.out.print("> ");
                            String inEmployeesCount = in.nextLine();
                            if (inEmployeesCount.isBlank()) {
                                employeesCount = null;
                            } else {
                                employeesCount = Integer.parseInt(inEmployeesCount);
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Wrong employeesCount. Please try again:");
                        }
                    }
                    org = new Organization(name, fullName, employeesCount);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e + ". Please try again");
                }
            }

            product = new Product(arguments[1], coords, price, arguments[3],
                    Integer.parseInt(arguments[4]), resUnitOfMeasure, org);
        } catch (IllegalArgumentException e) {
            System.out.println(e + ". Please try again");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect number of arguments for add_if_max command. Please try again");
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
