/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author gerai
 */
public class Validation {
    
    
public boolean presence(String str){
    if(str.isEmpty()){
        return true;
    }
    return false;
}
//type check for decimal

public boolean isDecimal(String str) {
    if (str == null || str.trim().isEmpty()) {
        return false;
    }
    try {
        Double.parseDouble(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}

//format check
public boolean email(String str){
    int countAt = 0;
    int countDot = 0;
    
    for(int i =0; i<str.length();i++){
        if(str.substring(i,i+1).equals("@")){
            countAt++;
        }
        if(str.substring(i,i+1).equals(".")){
            countDot++;
        }
    }
    
    if(countAt!=1 || countDot==0){
        return true;
    }
    if(str.substring(0,1).equals(".") || str.substring(0,1).equals("@")){
        return true;
    }
    return false;
}
    
}
