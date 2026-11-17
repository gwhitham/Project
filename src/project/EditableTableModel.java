/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

import java.util.Arrays;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class EditableTableModel extends DefaultTableModel {
    
    private boolean isEditable = false;
    private List<Integer> allowedColumns; // Stores which columns can be edited

    // Updated constructor that accepts the allowed column indices
    public EditableTableModel(Object[] columnNames, int rowCount, Integer... editableColumns) {
        super(columnNames, rowCount);
        this.allowedColumns = Arrays.asList(editableColumns);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Only allow editing if the table is unlocked AND the column is in the allowed list
        return isEditable && allowedColumns.contains(column);
    }

    public void setTableEditable(boolean state) {
        this.isEditable = state;
    }

    public boolean isTableEditable() {
        return this.isEditable;
    }

}
