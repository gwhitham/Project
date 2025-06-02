/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author gera0276
 */
public class ComboItem {
    String name;
    int id;
    public ComboItem(String name, int id){
        this.name = name;
        this.id = id;
    }
    
    public int getID(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
}
