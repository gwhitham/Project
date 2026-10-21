/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author gerai
 */
public class Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            FileReader fr = new FileReader("admin.txt");
            BufferedReader br = new BufferedReader(fr);
            DataStructures d1 = new DataStructures();
            MainMenu df = new MainMenu();
            MainMenu menu2 = new MainMenu();
            MainMenu menu3 = new MainMenu();
            Login l1 = new Login();
            //df.setVisible(true);
            //l1.setVisible(true);
            menu2.setVisible(true);
            
        }
        catch(IOException exception){
            AdminUserForm a1 = new AdminUserForm("a");
            a1.setVisible(true);
        }
    }
    
}
