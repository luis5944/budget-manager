/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budget.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis
 */
public class Purchases implements Comparable, Serializable {

    private String name;
    private double price;
    private String type;
    private static List<Purchases> purchases = new ArrayList();

    public Purchases(String name, double price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;

    }

    public static List<Purchases> getPurchases() {
        return purchases;
    }

    public static String formatText(double price) {
        return String.format("%.2f", price).replaceAll(",", ".");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(Object o) {
        double comparePrice = ((Purchases) o).getPrice();
        return Double.compare(comparePrice, price);
    }

}
