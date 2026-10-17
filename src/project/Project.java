/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;

/**
 *
 * @author gerai
 */
public class Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        MainMenu df = new MainMenu();
        MainMenu menu2 = new MainMenu();
        MainMenu menu3 = new MainMenu();
        Login l1 = new Login();
        //df.setVisible(true);
        //l1.setVisible(true);
        menu2.setVisible(true);
        DataStructures d1 = new DataStructures();
    }
    
}
