package com.luan.getlib.utils;

import java.io.Console;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @since v0.1.0
 * @author luanpozzobon
 */
public class InputReader {
    private final Scanner sc;
    private final Console console;
    
    public InputReader(){
        console = System.console();
        sc = new Scanner(System.in);
    }
    
    public int getNextInt(){
        String input;
        if(console != null){
            input = console.readLine();
        } else {
            input = sc.nextLine();
        }
        
        try{
            return Integer.parseInt(input);
        } catch(NumberFormatException | NoSuchElementException e){
            System.out.println("O scanner não encontrou um int: " + e);
            return -1;
        }
    }
    
    public String getNextLine(){
        if(console != null){
            return console.readLine();
        } else {
            return sc.nextLine();
        }
    }
    
    public double getNextDouble(){
        String input;
        
        if(console != null) {
            input = console.readLine();
        } else {
            input = sc.nextLine();
        }
        
        try{
            return Double.parseDouble(input);
        } catch(NumberFormatException | NullPointerException e){
            System.out.println("O scanner não encontrou um double: " + e);
            return -1;
        }
    }
    
    public char getNext(){
        String input;
        
        if(console != null) {
            input = console.readLine();
        } else {
            input = sc.nextLine();
        }
        
        try{
            return input.charAt(0);
        } catch(IndexOutOfBoundsException e){
            System.out.println("O scanner não encontrou nenhum caractere: " + e);
            return ' ';
        }
    }
    
    public char[] getPassword(){
        if(console != null) {
            return console.readPassword();
        } else {
            return sc.nextLine().toCharArray();
        }
    }
    
    public void closeScanner(){
        sc.close();
    }
}
