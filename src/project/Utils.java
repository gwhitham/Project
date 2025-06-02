/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author gerai
 */
public class Utils {
    
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
    
}
