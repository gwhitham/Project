package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author geraint
 */
//FIXMe: Import to table on constructor
public class ProductMenu extends javax.swing.JFrame {
    Validation v = new Validation();
    String gfilename = "";
    Utils u1 = new Utils();
    String [] cats;
    int productIdCounter;
    
     public void getCategories() throws FileNotFoundException, IOException{
        FileReader fr = new FileReader("category.txt");
        BufferedReader br = new BufferedReader(fr);
        int countLines = u1.countLines("category.txt");
        cats = new String [countLines];
        for(int i=0; i<countLines;i++){
            cats[i]=br.readLine();
        }
        
    }
    
    
    public void setCategories(){
        
        txtCategory.removeAllItems();
        for(String cat:cats){
            txtCategory.addItem(cat);
        }
    }

    /**
     * Creates new form ProductMenu
     */
    public ProductMenu() throws IOException {
        initComponents();
        bttnConfirm.setVisible(false);
        getCategories();
        setCategories();
        productIdCounter = generateCustomerId();
        txtID.setText(String.valueOf(productIdCounter));
        txtImageConfirm.setVisible(false);
    }
    
    
    private boolean validateInputFields() {
        // Check if product ID is a positive integer
        try {
            int productId = Integer.parseInt(txtID.getText());
            if (productId <= 0) {
                JOptionPane.showMessageDialog(this, "Product ID must be a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Product ID must be a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //Use the presence method from the Validation class to check if name and description are empty
        if (v.presence(txtName.getText()) || v.presence(txtDesc.getText())) {
            JOptionPane.showMessageDialog(this, "Product name and description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if price and quantity are positive numbers
        try {
            double price = Double.parseDouble(txtCost.getText());
            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Price must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int quantity = Integer.parseInt(txtQty.getText());
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    
    public void copyFile(File sourceFile, String destinationDirectory) throws IOException {
        // Create the destination directory if it doesn't exist
        File directory = new File(destinationDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Create the destination file
        File destinationFile = new File(destinationDirectory + sourceFile.getName());

        // Copy the image using FileInputStream and FileOutputStream
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destinationFile)) {
            byte [] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }
    }
    
    private int generateCustomerId() {
        int customerId = 1; // Default starting ID

        try (Scanner scanner = new Scanner(new File("products.txt"))) {
            String lastLine = "";
            while (scanner.hasNextLine()) {
                lastLine = scanner.nextLine();
            }

            if (!lastLine.isEmpty()) {
                String[] parts = lastLine.split(",");
                if (parts.length > 0) {
                    customerId = Integer.parseInt(parts[0]) + 1;
                }
            }
        } catch (FileNotFoundException e) {
            return customerId;
            // File doesn't exist, start with ID 1
        }

        return customerId;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        bttnCalculate = new javax.swing.JButton();
        bttnSave = new javax.swing.JButton();
        bttnReset = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        bttnConfirm = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtID = new javax.swing.JLabel();
        txtImageConfirm = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        txtQty = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        txtCategory = new javax.swing.JComboBox<>();
        txtCost = new javax.swing.JTextField();
        txtSales = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblConfirm = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel1.setText("Products");

        jButton4.setBackground(new java.awt.Color(255, 204, 204));
        jButton4.setText("back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Edit");

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Product ID", "Name", "Category", "Image file", "Cost Price", "Sales Price", "Qty"
            }
        ));
        jScrollPane2.setViewportView(productTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                        .addGap(38, 38, 38)
                        .addComponent(jButton5)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton4))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jButton5)))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Product Overview", jPanel1);

        jLabel5.setText("Quantity");

        jLabel6.setText("Description");

        jLabel7.setText("Category");

        jLabel8.setText("Cost Price");

        jLabel9.setText("Sales Price");

        bttnCalculate.setText("Calculate Sales Price");
        bttnCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnCalculateActionPerformed(evt);
            }
        });

        bttnSave.setText("Save");
        bttnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnSaveActionPerformed(evt);
            }
        });

        bttnReset.setText("Reset");
        bttnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnResetActionPerformed(evt);
            }
        });

        jButton1.setText("Multi File Import");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        bttnConfirm.setText("Confirm");
        bttnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnConfirmActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("New Product");

        jLabel3.setText("Image:");

        jLabel4.setText("ID");

        jButton2.setText("choose image");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtID.setText("current ID");

        txtImageConfirm.setForeground(new java.awt.Color(255, 51, 102));
        txtImageConfirm.setText("img");

        jButton3.setBackground(new java.awt.Color(255, 204, 204));
        jButton3.setText("back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtDesc.setColumns(20);
        txtDesc.setRows(5);
        jScrollPane1.setViewportView(txtDesc);

        txtCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoryActionPerformed(evt);
            }
        });

        jLabel10.setText("Name");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addContainerGap(588, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(290, 290, 290)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addGap(29, 29, 29)
                            .addComponent(bttnConfirm))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(bttnSave)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(bttnReset)))
                    .addGap(239, 239, 239))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(106, 106, 106)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(47, 47, 47)
                                    .addComponent(jLabel4)
                                    .addGap(36, 36, 36)
                                    .addComponent(txtID))
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel3))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap(156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtSales, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                    .addComponent(bttnCalculate))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jButton2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtImageConfirm))))))
                    .addGap(177, 177, 177)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblConfirm)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 321, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(jLabel2))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(37, 37, 37)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtID))))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(jLabel6)))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7)
                        .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addGap(28, 28, 28))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton2)
                                .addComponent(txtImageConfirm))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel9)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bttnCalculate))
                    .addGap(28, 28, 28)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bttnSave)
                        .addComponent(bttnReset))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(bttnConfirm))
                    .addGap(1, 1, 1)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblConfirm)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("New Product", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bttnCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnCalculateActionPerformed
        String content = new Utils().readFile("pricing.txt");
        if(!content.equals("")){
            float profit = Float.valueOf(content);
            String cost = txtCost.getText();
            Validation v = new Validation();
            if(!v.isDecimal(cost)){
                JOptionPane.showMessageDialog(rootPane, "Must be in valid price structure __.__");
            }
            else{
                float fCost = Float.valueOf(cost);
                float sales = ((profit/100)+1)*fCost;
                String rounded = String.format("%.2f", sales);
                txtSales.setText(String.valueOf(rounded));
            }
        }
    }//GEN-LAST:event_bttnCalculateActionPerformed

    private void bttnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnSaveActionPerformed
        // Validate input fields
        if (!validateInputFields()) {
            return;
        }

        // Get product information
        String productId = txtID.getText(); // Use txtID instead of txtProductID
        String productName = txtName.getText(); // Use txtName instead of txtProductName
        String productDescription = txtDesc.getText(); // Use txtDesc instead of txtProductDescription
        String price = txtSales.getText(); // Use txtCost instead of txtPrice
        String costPrice = txtCost.getText();
        String quantity = txtQty.getText(); // Use txtQty instead of txtQuantity
        String category = (String) txtCategory.getSelectedItem(); // Get selected category from txtCategory

        // Save product information to file
        try (FileWriter writer = new FileWriter("products.txt", true)) {
            String productData = productId + "," + productName + "," + productDescription + "," + price + "," +costPrice+ "," + quantity + "," + category + ","+gfilename+"\n";
            writer.write(productData);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving product information to file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Reset input fields
        txtName.setText("");
        txtDesc.setText("");
        txtSales.setText("");
        txtCost.setText("");
        txtCategory.setSelectedIndex(-1);
        productIdCounter++;
        txtID.setText(String.valueOf(productIdCounter));

        JOptionPane.showMessageDialog(this, "Product saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_bttnSaveActionPerformed

    private void bttnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnResetActionPerformed

        txtName.setText("");
        txtDesc.setText("");
        txtSales.setText("");
        txtCost.setText("");
        txtCategory.setSelectedIndex(-1);
    }//GEN-LAST:event_bttnResetActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        final String EXTENSION = ".csv";
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            gfilename = fileName;
            if (fileName.endsWith(EXTENSION)) {
                try {
                    copyFile(selectedFile, "C:\\Users\\gera0276\\Documents\\NetBeansProjects\\Project\\");
                    } catch (IOException ex) {
                        Logger.getLogger(ProductsForm.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    bttnConfirm.setVisible(true);
                    lblConfirm.setText("Confirm file is "+fileName);
                }
                else{
                    JOptionPane.showMessageDialog(this, "Incorrect file type", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bttnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnConfirmActionPerformed
        FileReader fr = null;
        try {
            fr = new FileReader(gfilename);
            BufferedReader br = new BufferedReader(fr);
            String line;
            if((line=br.readLine())==null){
                JOptionPane.showMessageDialog(this, "Incorrect file format, import unsuccesful.", "Error", JOptionPane.INFORMATION_MESSAGE);
                lblConfirm.setText("");
                bttnConfirm.setVisible(false);

                return;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProductsForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProductsForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductsForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_bttnConfirmActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        final String EXTENSION = ".jpg";
        final String EXTENSION2 = ".png";
        final String EXTENSION3 = ".jpeg";
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            gfilename = fileName;
            if (fileName.endsWith(EXTENSION)||fileName.endsWith(EXTENSION2)||fileName.endsWith(EXTENSION3)) {
                try {
                    /*String oldname = selectedFile.getName();
                    String [] suffix = oldname.split("\\.");
                    String id = String.valueOf(productIdCounter);
                    File newfile = new File(id+"."+suffix[1]);//Renaming of file to id + whatever is the current file suffix
                    selectedFile.renameTo(newfile);
                    */
                    copyFile(selectedFile, "temp_images\\");

                    } catch (IOException ex) {
                        Logger.getLogger(ProductsForm.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    txtImageConfirm.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(this, "Incorrect file type", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.setVisible(false);
        MainMenu m1 = new MainMenu();
        m1.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoryActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.setVisible(false);
        MainMenu m1 = new MainMenu();
        m1.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProductMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ProductMenu().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ProductMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttnCalculate;
    private javax.swing.JButton bttnConfirm;
    private javax.swing.JButton bttnReset;
    private javax.swing.JButton bttnSave;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblConfirm;
    private javax.swing.JTable productTable;
    private javax.swing.JComboBox<String> txtCategory;
    private javax.swing.JTextField txtCost;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JLabel txtID;
    private javax.swing.JLabel txtImageConfirm;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSales;
    // End of variables declaration//GEN-END:variables
}
