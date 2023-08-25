package com.luan.getlib.utils;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @since v0.1.0
 * @author luanpozzobon
 */
public class InputReader {
    private final Scanner sc;
    
    public InputReader(){
        sc = new Scanner(System.in);
    }
    
    public int getNextInt(){
        try{
            String line = sc.nextLine();
            return Integer.parseInt(line);
        } catch(NumberFormatException | NoSuchElementException e){
            System.out.println("O scanner não encontrou um int: " + e);
            return -1;
        }
    }
    
    public String getNextLine(){
        return sc.nextLine();
    }
    
    public double getNextDouble(){
        try{
            return Double.parseDouble(sc.nextLine());
        } catch(NumberFormatException | NullPointerException e){
            System.out.println("O scanner não encontrou um double: " + e);
            return -1;
        }
    }
    
    public char getNext(){
        try{
            return sc.nextLine().charAt(0);
        } catch(IndexOutOfBoundsException e){
            System.out.println("O scanner não encontrou nenhum caractere: " + e);
            return ' ';
        }
    }
    
    public void closeScanner(){
        sc.close();
    }
}
