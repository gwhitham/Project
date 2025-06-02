/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package project;

 import javax.swing.JSpinner;
 import javax.swing.SpinnerNumberModel;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JButton;
 import javax.swing.JOptionPane;
 import javax.swing.table.DefaultTableModel;
 import javax.swing.table.DefaultTableCellRenderer; // For aligning TOTAL row
 import javax.swing.ImageIcon;
 import java.awt.Image;
 import java.awt.GridLayout;
 import java.awt.FlowLayout;
 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.Component; // For renderer
 import java.awt.Font; // For TOTAL row font
 import java.io.File;
 import java.io.IOException;
 import java.io.BufferedReader;
import java.io.BufferedWriter;
 import java.io.FileReader;
import java.io.FileWriter;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import java.text.NumberFormat; // For currency formatting
import java.time.LocalDate;
import java.time.LocalDateTime;
 import java.util.Locale;       // For currency formatting


 public class TillSystem extends javax.swing.JFrame {
     Utils u1 = new Utils();
     private DefaultTableModel basketModel;
     private static final String IMAGE_FOLDER = "temp_images/";
     // Create a currency formatter
     private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.UK);
     private static final String TOTAL_ROW_IDENTIFIER = "TOTAL:"; // To identify the total row
     private static int totalRowIndex = -1;
     ComboItem [] c1;

     public TillSystem() throws IOException {
         initComponents();
         txtUser.setText(MainMenu.user);
         setButtons();
         fillCustomers();
         initializeBasketTable();
         // Ensure the image folder exists (optional robustness check)
         File imgDir = new File(IMAGE_FOLDER);
         if (!imgDir.exists()) {
             System.out.println("Warning: Image directory '" + IMAGE_FOLDER + "' not found.");
         }
     }
     private void fillCustomers() throws IOException{
         String [][] customers = u1.getFileToArray("customer.txt");
         String [] dropContent =null;
         int lines = u1.countLines("customer.txt");
         
         dropContent = new String [lines];
         
         int count = 0;
         c1 = new ComboItem[lines];
         jComboBoxCustomer.removeAllItems();
         for(String [] ind:customers){
            c1[count] = new ComboItem(ind[1]+" "+ind[2],Integer.parseInt(ind[0]));
            jComboBoxCustomer.addItem(ind[0]+" "+ind[1]);
            dropContent[count] = ind[0]+" "+ind[1];
            count++;
        }
     }
     private void initializeBasketTable() {
         basketModel = new DefaultTableModel(
                 // Updated column names
                 new String[]{"Item", "product ID", "Qty", "Unit Price", "Subtotal"}, 0
         ) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 // Prevent editing except perhaps for the TOTAL row identifier if needed
                 return getValueAt(row, 2) != null && getValueAt(row, 2).equals(TOTAL_ROW_IDENTIFIER) && column == 2;
                 // return false; // Simpler: make all non-editable
             }

             @Override
             public Class<?> getColumnClass(int columnIndex) {
                 // Define column types for proper sorting and rendering
                 switch (columnIndex) {
                     case 0: return String.class;   // Item Name
                     case 1: return Integer.class;  // ID
                     case 2: return Integer.class;  // Qty
                     case 3: return Object.class;   // Unit Price (or TOTAL label) - Use Object for flexibility
                     case 4: return Double.class;   // Subtotal / Total Value
                     default: return Object.class;
                 }
             }
         };
         tblTotal.setModel(basketModel);

         // --- Formatting and Rendering ---

         // Center align Qty column
         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
         centerRenderer.setHorizontalAlignment(JLabel.CENTER);
         tblTotal.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Right align currency columns (Unit Price and Subtotal)
         DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer() {
             @Override
             public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                 if (value instanceof Number) {
                     // Format numbers as currency
                     value = currencyFormatter.format(value);
                 } else if (value != null && value.equals(TOTAL_ROW_IDENTIFIER)) {
                    // Style the TOTAL label
                    setHorizontalAlignment(JLabel.RIGHT);
                    setFont(getFont().deriveFont(Font.BOLD));
                    setForeground(Color.BLUE); // Example styling
                    setText(value.toString());
                    return this; // Return early for the label
                 }
                 // Reset default styling for other cells
                 setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                 setFont(table.getFont()); // Reset font
                 setHorizontalAlignment(JLabel.RIGHT); // Right-align text/currency
                 return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             }
         };
         tblTotal.getColumnModel().getColumn(3).setCellRenderer(rightRenderer); // Unit Price
         tblTotal.getColumnModel().getColumn(4).setCellRenderer(rightRenderer); // Subtotal/Total

         // Set preferred column widths
         tblTotal.getColumnModel().getColumn(0).setPreferredWidth(130); // Item
         tblTotal.getColumnModel().getColumn(1).setPreferredWidth(40); // ID
         tblTotal.getColumnModel().getColumn(2).setPreferredWidth(40);  // Qty
         tblTotal.getColumnModel().getColumn(3).setPreferredWidth(70);  // Unit Price
         tblTotal.getColumnModel().getColumn(4).setPreferredWidth(80);  // Subtotal

         // Add initial total row
         updateTotalRow();
     }

     public void addToBasket(String ID, int quantity) {
         String[][] products = u1.getFileToArray("products.txt");
         for (String[] product : products) {
             if (product.length > 4 && product[0] != null && product[0].equals(ID)) { // Ensure product[4] exists
                 String productID = product[0];
                 String itemName = product[1];
                 String unitPriceStr = product[4]; // Get unit price string (index 4)
                 try {
                     double unitPrice = Double.parseDouble(unitPriceStr);
                     double subtotal = unitPrice * quantity;

                     // Check if item already in basket
                     boolean itemUpdated = false;
                     for (int i = 0; i < basketModel.getRowCount(); i++) {
                         // Skip the TOTAL row if it exists
                         if(basketModel.getValueAt(i, 3) != null && basketModel.getValueAt(i, 3).equals(TOTAL_ROW_IDENTIFIER)) {
                             continue;
                         }

                         if (basketModel.getValueAt(i, 0) != null && basketModel.getValueAt(i, 0).equals(itemName)) {
                             int existingQty = (int) basketModel.getValueAt(i, 2);
                             int newQty = existingQty + quantity;
                             double newSubtotal = unitPrice * newQty; // Recalculate subtotal based on new qty

                             basketModel.setValueAt(newQty, i, 2);        // Update Qty (col 1)
                             // Unit price (col 2) remains the same
                             basketModel.setValueAt(newSubtotal, i, 4);   // Update Subtotal (col 3)
                             itemUpdated = true;
                             break; // Exit loop once updated
                         }
                     }

                     // If item not found in basket, add as a new row
                     if (!itemUpdated) {
                         // Add item row BEFORE the total row
                         int insertRow = basketModel.getRowCount() > 0 ? basketModel.getRowCount() -1 : 0; // Default to 0 if empty, else before last row
                         if (basketModel.getRowCount() > 0 && basketModel.getValueAt(insertRow, 3) != null && basketModel.getValueAt(insertRow, 3).equals(TOTAL_ROW_IDENTIFIER)) {
                           // Correct index found
                         } else {
                            insertRow = basketModel.getRowCount(); // If no total row yet, append
                         }
                         basketModel.insertRow(insertRow, new Object[]{itemName,productID, quantity, unitPrice, subtotal});
                     }

                     // Update the total row after any change
                     updateTotalRow();

                 } catch (NumberFormatException e) {
                     JOptionPane.showMessageDialog(this, "Error parsing price for product ID '" + ID + "'.");
                 } catch (ArrayIndexOutOfBoundsException e) {
                     JOptionPane.showMessageDialog(this, "Error accessing product data (price) for ID '" + ID + "'.");
                 }
                 return; // Exit after processing the item
             }
         }
         JOptionPane.showMessageDialog(this, "Product with ID '" + ID + "' not found.");
     }

    private void updateTotalRow() {
        double total = 0.0;
        

        // Calculate total and find existing total row
        for (int i = 0; i < basketModel.getRowCount(); i++) {
            Object cellValue = basketModel.getValueAt(i, 3); // Check the "Unit Price" column for the identifier
            if (cellValue != null && cellValue.equals(TOTAL_ROW_IDENTIFIER)) {
                totalRowIndex = i;
            } else {
                // Make sure value is a number before adding
                Object subtotalValue = basketModel.getValueAt(i, 4);
                 if (subtotalValue instanceof Number) {
                    total += ((Number) subtotalValue).doubleValue();
                 }
            }
        }

        // Remove existing total row if found
        if (totalRowIndex != -1) {
            basketModel.removeRow(totalRowIndex);
        }

        // Add the new total row at the end
        // Ensure correct types are added (String, null/Integer, String, Double)
        basketModel.addRow(new Object[]{null, null, null, TOTAL_ROW_IDENTIFIER, total});

        // Optional: Scroll to make the total row visible if the table has a scroll pane
        // if (jScrollPane1 != null) {
        //     tblTotal.scrollRectToVisible(tblTotal.getCellRect(basketModel.getRowCount() - 1, 0, true));
        // }
    }


     // --- showProducts Method (no changes needed from previous version for this request) ---
    public void showProducts(String category) {
        bttnBack.setVisible(true);
        String[][] products = u1.getFileToArray("products.txt");

        txtCategory.setText(category);
        mainPanel.removeAll();
        mainPanel.setBackground(Color.lightGray);
        mainPanel.setLayout(new GridLayout(0, 1, 5, 5)); // Added gaps

        for (String[] product : products) {
             // Ensure product array has enough elements before accessing them
            if (product.length > 7 && product[6] != null && product[6].equals(category)) {
                String id = product[0];
                String name = product[1];
                String price = product[4]; // Unit Price
                String imageFileName = product[7];
                int stock;
                try {
                    stock = Integer.parseInt(product[5]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                    stock = 99;
                    System.err.println("Warning: Could not parse stock for product ID " + id + ", defaulting to " + stock);
                }

                // Panel for this Product
                JPanel productPanel = new JPanel(new BorderLayout(5, 5));
                productPanel.setBackground(Color.white);
                productPanel.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));

                // Image Label (Left)
                JLabel imageLabel = new JLabel();
                imageLabel.setPreferredSize(new Dimension(60, 60));
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
                imageLabel.setVerticalAlignment(JLabel.CENTER);
                imageLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                if (imageFileName != null && !imageFileName.trim().isEmpty()) {
                    File imageFile = new File(IMAGE_FOLDER + imageFileName.trim());
                    if (imageFile.exists()) {
                        try {
                            ImageIcon originalIcon = new ImageIcon(imageFile.getAbsolutePath());
                            Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                            imageLabel.setIcon(new ImageIcon(scaledImage));
                        } catch (Exception ex) {
                            System.err.println("Error loading/scaling image: " + imageFile.getPath() + " - " + ex.getMessage());
                            imageLabel.setText("No img");
                        }
                    } else {
                        System.err.println("Image file not found: " + imageFile.getAbsolutePath());
                        imageLabel.setText("No img");
                    }
                } else {
                     imageLabel.setText("No img");
                }
                productPanel.add(imageLabel, BorderLayout.WEST);

                // Details Panel (Center)
                JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                detailsPanel.setBackground(Color.white);

                JLabel productLabel = new JLabel("<html>" + name + "<br>" + currencyFormatter.format(Double.parseDouble(price)) + "</html>"); // Format unit price

                SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, Math.max(1, stock), 1);
                JSpinner quantitySpinner = new JSpinner(spinnerModel);
                 quantitySpinner.setPreferredSize(new Dimension(50, 25));

                JButton addButton = new JButton("Add");

                detailsPanel.add(productLabel);
                detailsPanel.add(new JLabel(" Qty:"));
                detailsPanel.add(quantitySpinner);
                detailsPanel.add(addButton);

                productPanel.add(detailsPanel, BorderLayout.CENTER);

                 addButton.addActionListener(e -> {
                     int selectedQuantity = (int) quantitySpinner.getValue();
                     addToBasket(id, selectedQuantity);
                 });

                mainPanel.add(productPanel);

            } else if (product.length > 6 && product[6] != null && product[6].equals(category)) { // Check length > 6 for category check
                 System.err.println("Warning: Product data for ID " + (product.length > 0 ? product[0] : "UNKNOWN") + " might be incomplete (e.g., missing image filename).");
            }
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

     // --- Other methods (setButtons, readButtonLabelsFromFile, main, etc.) remain largely the same ---
     // ...(ensure imports and other methods from previous steps are included)...
     public void setButtons() throws IOException{
         String [] arr = readButtonLabelsFromFile("category.txt");
         mainPanel.removeAll();
         mainPanel.setLayout(new GridLayout(0, 1, 5, 10)); // Vertical layout with gaps
         bttnBack.setVisible(false); // Hide the specific product back button initially


         for (String label : arr) {
             if (label != null && !label.trim().isEmpty()) { // Check for null or empty lines
                 JButton button = new JButton(label.trim());
                 button.setPreferredSize(new Dimension(150, 40)); // Give buttons a standard size
                 mainPanel.add(button);

                 // Add action listeners or other functionality to the buttons here
                 button.addActionListener(e -> {
                     showProducts(label.trim()); // Pass the category label
                 });
             }
         }
         mainPanel.revalidate();
         mainPanel.repaint();
     }

     public String[] readButtonLabelsFromFile(String filename) throws IOException {
         java.util.ArrayList<String> labels = new java.util.ArrayList<>();
         try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
             String line;
             while ((line = reader.readLine()) != null) {
                 if (!line.trim().isEmpty()) {
                    labels.add(line.trim());
                 }
             }
         } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "Error reading category file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             return new String[0];
         }
         return labels.toArray(new String[0]);
     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTotal = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtUser = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        txtCategory = new javax.swing.JLabel();
        bttnBack = new javax.swing.JButton();
        jPanelCompleteOrder = new javax.swing.JPanel();
        jButtonCompleteOrder = new javax.swing.JButton();
        jComboBoxCustomer = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/project/GJWLogoSmall.jpg"))); // NOI18N

        tblTotal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Qty", "Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTotal);

        btnBack.setBackground(new java.awt.Color(204, 204, 204));
        btnBack.setText("back to Main Menu");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel2.setText("user: ");

        txtUser.setText("current user");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtUser)
                .addContainerGap(150, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUser))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        mainPanel.setBackground(new java.awt.Color(204, 204, 255));
        mainPanel.setMinimumSize(new java.awt.Dimension(300, 0));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
        );

        bttnBack.setBackground(new java.awt.Color(255, 204, 204));
        bttnBack.setText("Back");
        bttnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(txtCategory)
                        .addGap(68, 68, 68)
                        .addComponent(bttnBack))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnBack)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBack))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtCategory)
                                        .addGap(10, 10, 10))
                                    .addComponent(bttnBack, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 36, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanelCompleteOrder.setBackground(new java.awt.Color(204, 255, 204));

        jButtonCompleteOrder.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonCompleteOrder.setText("Complete");
        jButtonCompleteOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompleteOrderActionPerformed(evt);
            }
        });

        jComboBoxCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Select Customer");

        javax.swing.GroupLayout jPanelCompleteOrderLayout = new javax.swing.GroupLayout(jPanelCompleteOrder);
        jPanelCompleteOrder.setLayout(jPanelCompleteOrderLayout);
        jPanelCompleteOrderLayout.setHorizontalGroup(
            jPanelCompleteOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCompleteOrderLayout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(52, 52, 52)
                .addGroup(jPanelCompleteOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCompleteOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        jPanelCompleteOrderLayout.setVerticalGroup(
            jPanelCompleteOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCompleteOrderLayout.createSequentialGroup()
                .addGroup(jPanelCompleteOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jButtonCompleteOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jMenu1.setText("Section");

        jMenuItem1.setText("Hardware");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenu3.setText("test");
        jMenu1.add(jMenu3);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setText("Admin Login");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Search");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelCompleteOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelCompleteOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        MainMenu m1 = new MainMenu();
        m1.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Login l1 = new Login();
        l1.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void bttnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnBackActionPerformed
        try {
            setButtons();
        } catch (IOException ex) {
            Logger.getLogger(TillSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bttnBackActionPerformed

    private void jButtonCompleteOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompleteOrderActionPerformed
       String custID = jComboBoxCustomer.getSelectedItem().toString();
        if(totalRowIndex==-1){
            JOptionPane.showMessageDialog(rootPane, "No items have been added to the basket");
        }
        else{
            FileWriter fw = null;
           try {
               String [] details = custID.split(" ");
               int id = Integer.parseInt(details[0]);
               System.out.println(id);
               int orderID = u1.getID("orders.txt");
               fw = new FileWriter("orders.txt",true);
               BufferedWriter bw = new BufferedWriter(fw);
               String date = LocalDateTime.now().toString();
               bw.write(orderID+","+id+","+basketModel.getValueAt(basketModel.getRowCount()-1, 4)+","+date);
               bw.newLine();
               bw.close();
               int productOrderID = u1.getID("productOrder.txt");
               
               Object[] columnData = new Object[basketModel.getRowCount()];  // One entry for each row
               Object[] rowData = new Object [basketModel.getRowCount()];
                
                for (int i = 0; i < basketModel.getRowCount()-1; i++) {  // Loop through the rows
                 // Record the 5th column value (index 4)
                    int productID = Integer.parseInt(basketModel.getValueAt(i, 1).toString());
                    int qty = Integer.parseInt(basketModel.getValueAt(i, 2).toString());
                    double price = Double.parseDouble((basketModel.getValueAt(i, 3).toString()));
                    System.out.println(qty+" "+price);
                    FileWriter fw1 = new FileWriter("productOrder.txt",true);
                    BufferedWriter bw1  = new BufferedWriter(fw1);
                    bw1.write(productOrderID+","+orderID+","+productID+","+qty+","+price);
                    bw1.newLine();
                    bw1.close();
                    productOrderID++;
                }
                
               
               
           } catch (IOException ex) {
               Logger.getLogger(TillSystem.class.getName()).log(Level.SEVERE, null, ex);
           } finally {
               try {
                   fw.close();
               } catch (IOException ex) {
                   Logger.getLogger(TillSystem.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
            
            
        }
    }//GEN-LAST:event_jButtonCompleteOrderActionPerformed

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
            java.util.logging.Logger.getLogger(TillSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TillSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TillSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TillSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TillSystem().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TillSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton bttnBack;
    private javax.swing.JButton jButtonCompleteOrder;
    private javax.swing.JComboBox<String> jComboBoxCustomer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelCompleteOrder;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable tblTotal;
    private javax.swing.JLabel txtCategory;
    private javax.swing.JLabel txtUser;
    // End of variables declaration//GEN-END:variables
}
