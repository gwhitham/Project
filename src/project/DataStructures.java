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
    
    
    
}
