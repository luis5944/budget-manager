/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budget.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import budget.model.Purchases;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class ControllerUI {

    private final Scanner sc = new Scanner(System.in);
    private double income = 0;

    public void text() {
        String text = "Choose your action:\n"
                + "1) Add income\n"
                + "2) Add purchase\n"
                + "3) Show list of purchases\n"
                + "4) Balance\n"
                + "5) Save\n"
                + "6) Load\n"
                + "7) Analyze (Sort)\n"
                + "0) Exit";
        System.out.println(text);
    }

    public void menu(int option) {
        switch (option) {
            case 1:
                System.out.println("Enter income:");
                income += Double.parseDouble(sc.nextLine());
                System.out.println("Income was added!\n");
                break;
            case 2:

                purchasesUI ui = new purchasesUI();

                while (option != 5) {
                    ui.text();
                    try {
                        option = Integer.parseInt(sc.nextLine());
                        ui.menu(option);
                    } catch (NumberFormatException ex) {

                    }
                }

                break;

            case 3:
                listUI lui = new listUI();

                while (option != 6) {
                    lui.text();
                    try {
                        option = Integer.parseInt(sc.nextLine());
                        lui.menu(option);
                    } catch (NumberFormatException ex) {

                    }
                }
                break;
            case 4:
                for (Purchases purchase : Purchases.getPurchases()) {
                    income -= purchase.getPrice();
                }
                System.out.println("Balance: $" + income + "\n");

                break;

            case 5:
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(new File("purchases.txt")));
                    for (Purchases purchase : Purchases.getPurchases()) {
                        bw.append(purchase.getName() + ";" + purchase.getPrice() + ";" + purchase.getType());
                        bw.newLine();

                    }
                    bw.append("Balance;" + income);
                } catch (IOException ex) {

                } finally {
                    try {
                        bw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ControllerUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                System.out.println("Purchases were saved!");
                break;
            case 6:

                BufferedReader br = null;
                try {
                    File f = new File("purchases.txt");
                    if (!f.exists()) {
                        return;
                    }
                    br = new BufferedReader(new FileReader(f));
                    Purchases.getPurchases().clear();
                    String linea;
                    while ((linea = br.readLine()) != null) {

                        String[] data = linea.split(";");
                        if (!data[0].equals("Balance")) {
                            Purchases.getPurchases().add(new Purchases(data[0], Double.parseDouble(data[1]), data[2]));
                        } else {
                            income = Double.parseDouble(data[1]);
                        }
                    }

                } catch (FileNotFoundException ex) {

                } catch (IOException ex) {

                }

                System.out.println("Purchases were loaded!\n");
                break;
            case 7:
                sortUI sui = new sortUI();
                while (option != 4) {
                    sui.text();
                    try {
                        option = Integer.parseInt(sc.nextLine());
                        sui.menu(option);
                    } catch (NumberFormatException ex) {

                    }
                }
                break;
            case 0:
                System.out.println("\nBye!");
                System.exit(0);
        }
    }

    class purchasesUI extends ControllerUI {

        @Override
        public void text() {
            String text = "Choose the type of purchase\n"
                    + "1) Food\n"
                    + "2) Clothes\n"
                    + "3) Entertainment\n"
                    + "4) Other\n"
                    + "5) Back";
            System.out.println(text);
        }

        public void enter(String type) {
            System.out.println("\nEnter purchase name:");
            String name = sc.nextLine();
            if (!name.isEmpty()) {
                System.out.println("Enter its price:");
                double price = Double.parseDouble(sc.nextLine());
                Purchases.getPurchases().add(new Purchases(name, price, type));
                System.out.println("Purchase was added!\n");
            }
        }

        @Override
        public void menu(int option) {
            switch (option) {
                case 1:
                    enter("Food");
                    break;
                case 2:
                    enter("Clothes");
                    break;

                case 3:
                    enter("Entertainment");
                    break;
                case 4:
                    enter("Other");
                    break;

                case 5:
                    System.out.println("");
                    break;

            }
        }
    }

    class listUI extends ControllerUI {

        @Override
        public void text() {
            String text = "Choose the type of purchases\n"
                    + "1) Food\n"
                    + "2) Clothes\n"
                    + "3) Entertainment\n"
                    + "4) Other\n"
                    + "5) All\n"
                    + "6) Back";
            System.out.println(text);
        }

        public void show(String type, boolean all) {
            double sum = 0;
            int count = 0;

            if (all) {
                if (Purchases.getPurchases().isEmpty()) {
                    System.out.println("\nAll:");
                    System.out.println("Purchase list is empty!\n");
                    return;
                }
                System.out.println("\nAll:");
                for (Purchases purchase : Purchases.getPurchases()) {

                    System.out.println(purchase.getName() + " $" + Purchases.formatText(purchase.getPrice()));
                    sum += purchase.getPrice();
                }
                System.out.println("Total sum: $" + Purchases.formatText(sum) + "\n");
            } else {
                System.out.println("\n" + type + ":");
                for (Purchases purchase : Purchases.getPurchases()) {
                    if (purchase.getType().equals(type)) {

                        System.out.println(purchase.getName() + " $" + Purchases.formatText(purchase.getPrice()));
                        sum += purchase.getPrice();
                        count++;
                    }

                }
                if (count > 0) {
                    System.out.println("Total sum: $" + Purchases.formatText(sum) + "\n");
                } else {
                    System.out.println("\n" + type + ":");
                    System.out.println("Purchase list is empty!\n");
                }
            }

        }

        @Override
        public void menu(int option) {
            switch (option) {
                case 1:
                    show("Food", false);
                    break;
                case 2:
                    show("Clothes", false);
                    break;

                case 3:
                    show("Entertainment", false);
                    break;
                case 4:
                    show("Other", false);
                    break;

                case 5:
                    show("", true);
                    break;
                case 6:
                    System.out.println("");
                    break;

            }
        }
    }

    class sortUI extends ControllerUI {

        Map<Double, String> sortType = new TreeMap<>(Collections.reverseOrder());

        @Override
        public void text() {
            String text = "How do you want to sort?\n"
                    + "1) Sort all purchases\n"
                    + "2) Sort by type\n"
                    + "3) Sort certain type\n"
                    + "4) Back";
            System.out.println(text);
        }

        @Override
        public void menu(int option) {
            switch (option) {
                case 1:
                    Collections.sort(Purchases.getPurchases());
                    if (Purchases.getPurchases().isEmpty()) {
                        System.out.println("\nPurchase list is empty!\n");
                        return;
                    }
                    System.out.println("\nAll:");
                    double totalP = 0;

                    for (Purchases purchase : Purchases.getPurchases()) {
                        System.out.println(purchase.getName() + " $" + Purchases.formatText(purchase.getPrice()));
                        totalP += purchase.getPrice();
                    }
                    System.out.println("Total: $" + Purchases.formatText(totalP) + "\n");

                    break;
                case 2:
                    double total = pricesAndTotal();
                    System.out.println("\nTypes:");
                    if (sortType.isEmpty()) {
                        System.out.println("Total sum: $" + Purchases.formatText(total) + "\n");
                        return;
                    }
                    sortType.forEach((price, type) -> {
                        System.out.println(type + " - $" + Purchases.formatText(price));
                    });

                    System.out.println("Total sum: $" + Purchases.formatText(total) + "\n");

                    break;

                case 3:
                    String text = "\nChoose the type of purchase\n"
                            + "1) Food\n"
                            + "2) Clothes\n"
                            + "3) Entertainment\n"
                            + "4) Other";
                    System.out.println(text);
                    option = Integer.parseInt(sc.nextLine());

                    switch (option) {
                        case 1:
                            sortByType("Food");
                            break;
                        case 2:
                            sortByType("Clothes");
                            break;
                        case 3:
                            sortByType("Entertainment");
                            break;
                        case 4:
                            sortByType("Other");
                            break;

                    }

                    break;
                case 4:
                    System.out.println();
                    return;

            }
        }

        private void sortByType(String type) {
            Collections.sort(Purchases.getPurchases());

            if (Purchases.getPurchases().isEmpty()) {
                System.out.println("\nPurchase list is empty!\n");
                return;
            }

            System.out.println("\ntype:");
            double totalPT = 0;
            for (Purchases purchase : Purchases.getPurchases()) {
                if (type.equals(purchase.getType())) {
                    System.out.println(purchase.getName() + " $" + Purchases.formatText(purchase.getPrice()));
                    totalPT += purchase.getPrice();
                }
            }
            System.out.println("Total sum: $" + Purchases.formatText(totalPT) + "\n");
        }

        private double pricesAndTotal() {
            Map<Double, String> map = new TreeMap<>();
            double sumFood = 0;
            double sumEntert = 0;
            double sumClot = 0;
            double sumOther = 0;
            double sumTotal = 0;

            for (Purchases p : Purchases.getPurchases()) {

                switch (p.getType()) {
                    case "Food":
                        sumFood += p.getPrice();
                        break;
                    case "Entertainment":
                        sumEntert += p.getPrice();
                        break;
                    case "Clothes":
                        sumClot += p.getPrice();
                        break;
                    case "Other":
                        sumOther += p.getPrice();
                        break;
                    default:
                        break;
                }
            }
            if (sumClot != 0) {
                map.put(sumClot, "Clothes");
            }
            if (sumFood != 0) {
                map.put(sumFood, "Food");
            }
            if (sumEntert != 0) {
                map.put(sumEntert, "Entertainment");
            }
            if (sumOther != 0) {
                map.put(sumOther, "Other");
            }

            sortType.putAll(map);
            sumTotal = sumFood + sumEntert + sumClot + sumTotal;
            return sumTotal;
        }

    }
}
