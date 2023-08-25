package com.luan.getlib.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * @since v0.1.1
 * @author luanpozzobon
 */
public class DataFormatter {
    public static LocalDate formatDate(String date){
        try{
            return LocalDate.parse(date);
        } catch (DateTimeParseException e){
            System.out.println("Não foi possível converter a string fornecida para data: " + e);
            return null;
        }   
    }
}
