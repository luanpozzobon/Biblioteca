package com.luan.getlib.utils;

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
        return Integer.parseInt(sc.nextLine());
    }
    
    public String getNextLine(){
        return sc.nextLine();
    }
    
    public double getNextDouble(){
        return Double.parseDouble(sc.nextLine());
    }
    
    public char getNext(){
        return sc.nextLine().charAt(0);
    }
}
