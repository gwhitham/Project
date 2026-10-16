/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

import java.io.IOException;

/**
 *
 * @author gera0276
 */
public class ModifiedTillSystem extends TillSystem {

    public ModifiedTillSystem() throws IOException {
        // super() is called automatically, which runs initComponents() in TillSystem
        
        // Now you can modify the protected UI elements or add new logic
        setupCustomFeatures();
    }

    private void setupCustomFeatures() {
        // Example: If you made jButtonCompleteOrder protected, you can change it here
        jButtonCompleteOrder.setText("Process Different Order");
    }
    
    // You can also override methods like showProducts if you need different logic
    @Override
    public void showProducts(String category) {
        super.showProducts(category); // Call the original, or write completely new logic
        // ... custom code ...
    }
}