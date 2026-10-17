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

    public DataStructures() throws IOException {
        Utils u1 = new Utils();
        CustomerList = new Customer [u1.countLines("customer.txt")]; 
        String [][] cList = u1.getFileToArray("customer.txt");
        for(int i =0; i<cList.length;i++){
            CustomerList[i] = new Customer(cList[i][0],cList[i][1],cList[i][2],cList[i][3],cList[i][4]);
        }
    
    }
    
    
    
}
