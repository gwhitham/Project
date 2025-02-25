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
