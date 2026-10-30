/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 *
 * @author gerai
 */
public class Utils {
    
    public Utils(){
        
    }
    
    public void writeToFile(String filename, String details){
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(details);
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int countLines(String fileName) throws FileNotFoundException, IOException{
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine()!= null) {
                lineCount++;
            }
        }
        return lineCount;
    }
    
    //abstraction of the get customer ID, now we can generate an id based on any file
    
    public int getID(String filename) {
        int Id = 1; // Default starting ID

        try (Scanner scanner = new Scanner(new File(filename))) {
            String lastLine = "";
            while (scanner.hasNextLine()) {
                lastLine = scanner.nextLine();
            }

            if (!lastLine.isEmpty()) {
                String[] parts = lastLine.split(",");
                if (parts.length > 0) {
                    Id = Integer.parseInt(parts[0]) + 1;
                }
            }
        } catch (FileNotFoundException e) {
            return Id;
            // File doesn't exist, start with ID 1
        }

        return Id;
    }
    
    public int countFields(String fileName) throws FileNotFoundException, IOException{
        int fieldCount = 0;
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line=reader.readLine())!= null) {
                String [] items = line.split(",");
                fieldCount = items.length;
                return fieldCount;
            }
        }
        return fieldCount;
    }
    
    public String[][] getFileToArray(String filename){
        String [][] arr = null;
        try {
            arr = new String [countLines(filename)][countFields(filename)];
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line=reader.readLine())!= null) {
                String [] items = line.split(",");
                for(int i=0; i<items.length;i++){
                    arr[count][i] = items[i];
                }
                count++;
            }
        }
            
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
    public void saveImageToFolder(File selectedFile, String folder) {
        String destinationFolder = folder; // Specify your desired folder
        File destinationDir = new File(destinationFolder);

        if (!destinationDir.exists()) {
            destinationDir.mkdirs(); // Create the directory if it doesn't exist
        }

        String fileName = selectedFile.getName();
        File destinationFile = new File(destinationDir, fileName);

        try {
            Path sourcePath = selectedFile.toPath();
            Path destinationPath = destinationFile.toPath();
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING); // Copy the file

            JOptionPane.showMessageDialog(null, "Image saved to " + destinationFolder);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Print the stack trace for debugging
        }
    }
    public void emptyFile(String filename) throws IOException{
        FileWriter fw = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("");
        bw.close();
        fw.close();
    }
    
    public void appendFile(String filename, String record) throws IOException{
        FileWriter fw = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(record);
        bw.close();
        fw.close();
    }
    
    
    public void deleteRecord(String filename, int id) throws IOException{
        String [][] arr = getFileToArray(filename);
        emptyFile(filename);
        for(String [] row:arr){
            if(!row[0].equals(id)){
                String record = "";
                boolean first = true;
                for(String item:row){
                    if(first){
                        record=record+item;
                        first=false;
                    }
                    else{
                        record=record+","+item;
                    }
                    
                }
                appendFile(filename, record);
            }
        }
        
        
    }
    
    public boolean checkArray(String searchTerm, int index, String [][] arr){
        for(int i = 0; i<arr.length;i++){
            if(arr[i][index].equals(searchTerm)){
                return true;
            }
        }
        return false;
    }
    
    public String readFile(String filename){
        FileReader fr = null;
        try {
            fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            //Shortcut to reading lines where lines read each lines and collect joins them all together by a carriage return \n
            return br.lines().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }
    
    public Customer getCustomerFromID(String ID){
        for(Customer cust:DataStructures.CustomerList){
            if(cust.getID().equals(ID)){
                return cust;
            }
        }
        return null;
    }
    
    public Product getProductFromID(String ID){
       for(Product prod:DataStructures.productList){
            if(prod.getId().equals(ID)){
                return prod;
            }
        }
        return null;
    }

    // Will calculate the range given and work out the scale of the graph. It will also return the number of y/m/d
    // 0 years
    // 1 months
    // 2 days
    public int[] calculateRange(LocalDate startDate, LocalDate endDate){
        int [] values = new int[2];
        Period range = Period.between(startDate, endDate);
        if(range.getYears()>1){
            values[0] = 0;
            values[1] = range.getYears();
            return values;
        }
        else if(range.getYears()>0||range.getMonths()>2){
            values[0] = 1;
            
            values[1] = range.getMonths()+range.getYears()*12;
            return values;
        }
        values[0] = 2;
        values[1] = range.getDays();
        return values;
        
    }
    
    public double[][] findRangeSales(int [] range,LocalDate startDate, LocalDate endDate){
        double [][] sales = new double[range[1]+1][2];
        if(range[0]==0){
            LocalDate current;
            for(int i=0; i<=range[1];i++){
                if(i==0){
                    current = startDate;
                }
                else{
                    current = LocalDate.of(startDate.getYear()+i, Month.JANUARY, 1);
                }
                 
                LocalDate currentEnd;
                if(current.getYear()==LocalDate.now().getYear()){
                    currentEnd = endDate;
                }
                else{
                    currentEnd = LocalDate.of(startDate.getYear()+i, Month.DECEMBER, 31);
                }
                 
                sales[i][0] = findTotalSales(current,currentEnd);
                sales[i][1] = Math.round(startDate.getYear()+i);
            }
        }
        else if(range[0]==1){
            LocalDate current = startDate;
            for(int i=0; i<=range[1];i++){
                current = startDate.plusMonths(i);
                if(i!=0){
                    current = current.withDayOfMonth(1);
                }
                LocalDate currentEnd = current.plusMonths(1);
                if(current.getYear()==LocalDate.now().getYear()&& current.getMonth()==LocalDate.now().getMonth()){
                    currentEnd = endDate;
                }
                sales[i][0] = findTotalSales(current,currentEnd);
                sales[i][1] = current.getYear() + ((startDate.getMonthValue()+i)/100);
            }
        }
        return sales;
    }
    
    public double findTotalSales(LocalDate startDate, LocalDate endDate){
        double total = 0;
        for(Order current:DataStructures.getOrderList()){
                String []  o = current.getDate().split("-");
                LocalDate oDate = LocalDate.of(Integer.valueOf(o[0]), Integer.valueOf(o[1]), Integer.valueOf(o[2]));
                if (!oDate.isAfter(endDate)&& !oDate.isBefore(startDate)){
                    total = total+current.getTotalCost();
                }
            }
        return total;
    }
    
    
}
