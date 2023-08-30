package com.luan.getlib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * @since v0.1.1
 * @author luanpozzobon
 */
public class DataFormatter {
    public static LocalDate formatDate(String date){
        try{
            date = normalizeDate(date);
            return LocalDate.parse(date);
        } catch (DateTimeParseException e){
            System.out.println("Não foi possível converter a string fornecida para data: " + e);
            System.out.println("Insira uma data válida!");
            return null;
        }   
    }
    
    public static String formatZipCode(String zipCode){
        return zipCode.replaceAll("[\\W]", "");
    }
    
    private static String normalizeDate(String inputDate) {
        String[] formats = {"yyyy-MM-dd", "yyyy-dd-MM", "dd-MM-yyyy", "MM-dd-yyyy", "yyyy/MM/dd", "yyyy/dd/MM", "dd/MM/yyyy", "MM/dd/yyyy"};
        
        for(String i : formats){
            try{
                SimpleDateFormat formatter = new SimpleDateFormat(i);
                formatter.setLenient(false);
                Date date = formatter.parse(inputDate);
                return new SimpleDateFormat("yyyy-MM-dd").format(date);
            } catch(ParseException e){
                continue;
            }
        }
        return "";
    }
}
