/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

import java.io.IOException;

/**
 *
 * @author geraint
 */
public class DataStructures {
    
    static Customer [] CustomerList;
    private static AdminUser [] adminList;
    private static Order [] orderList;

    public DataStructures() throws IOException {
        Utils u1 = new Utils();
        CustomerList = new Customer [u1.countLines("customer.txt")]; 
        adminList = new AdminUser [u1.countLines("admin.txt")]; 
        String [][] cList = u1.getFileToArray("customer.txt");
        String [][] aList = u1.getFileToArray("admin.txt");
        for(int i =0; i<cList.length;i++){
            CustomerList[i] = new Customer(cList[i][0],cList[i][1],cList[i][2],cList[i][3],cList[i][4]);
        }
        for(int i =0; i<aList.length;i++){
            adminList[i] = new AdminUser(aList[i][0],aList[i][1],aList[i][2]);
        }
        loadOrders();
    
    }

    public static Order[] getOrderList() {
        return orderList;
    }

    public static AdminUser[] getAdminList() {
        return adminList;
    }
    
    public static void updateUsers() throws IOException{
        Utils u1 = new Utils();
        adminList = new AdminUser [u1.countLines("admin.txt")]; 
        String [][] aList = u1.getFileToArray("admin.txt");
        for(int i =0; i<aList.length;i++){
            adminList[i] = new AdminUser(aList[i][0],aList[i][1],aList[i][2]);
        }
    }
    
    public void loadOrders() throws IOException{
        Utils u1 = new Utils();
        orderList = new Order [u1.countLines("orders.txt")]; 
        String [][] oList = u1.getFileToArray("orders.txt");
        String [][] poList = u1.getFileToArray("productOrder.txt");
        String prod = "";
        String price = "";
        String qty = "";
        for(int i = 0; i<oList.length;i++){
            String orderid = oList[i][0];
            for(int j=0; j<poList.length;j++){
                if(orderid.equals(poList[j][1])){
                    prod=prod+poList[j][2]+",";
                    qty=qty+poList[j][3]+",";
                    price=price+poList[j][4]+",";
                }
            }
            
            String [] matchingProducts = prod.split(",");
            String [] matchingQty = qty.split(",");
            String [] matchingPrice = price.split(",");
            String [][] matchingOrders= new String[matchingProducts.length][3];
            for(int k = 0; k<matchingProducts.length;k++){
                matchingOrders[k][0] = matchingProducts[k];
                matchingOrders[k][1] = matchingQty[k];
                matchingOrders[k][2] = matchingPrice[k]; 
            }
            orderList[i] = new Order(oList[i][0],oList[i][1], matchingOrders, Double.valueOf(oList[i][2]));
            prod ="";
            price ="";
            qty="";
        }
        
    }
    
    
    
}
