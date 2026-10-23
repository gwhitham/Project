/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author geraint
 */
public class Order {
    private String customer;
    private String orderid;
    private String [][] products;
    private double totalCost;

    public Order(String customer, String orderid, String[][] products, double totalCost) {
        this.customer = customer;
        this.orderid = orderid;
        this.products = products;
        this.totalCost = totalCost;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String[][] getProducts() {
        return products;
    }

    public void setProducts(String[][] products) {
        this.products = products;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    
    
}
