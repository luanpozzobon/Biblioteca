package com.luan.getlib.utils;

import com.luan.getlib.models.Result;

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
    private static final String INVALID_DATE_FORMAT = "Formato de data inválido!";
    private static final String DATE_FORMATTED = "Data formatada com sucesso";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    public static Result<LocalDate> formatDate(String date){
        LocalDate formattedDate = null;
        try{
            date = normalizeDate(date);
            formattedDate = LocalDate.parse(date);
            return new Result<>(true, DATE_FORMATTED, formattedDate);
        } catch (DateTimeParseException e) {
            return new Result<>(false, INVALID_DATE_FORMAT, formattedDate);
        }
    }

    private static String normalizeDate(String inputDate) {
        String[] formats = {"yyyy-MM-dd", "yyyy-dd-MM", "dd-MM-yyyy", "MM-dd-yyyy", "yyyy/MM/dd", "yyyy/dd/MM", "dd/MM/yyyy", "MM/dd/yyyy"};

        for(String i : formats){
            try{
                SimpleDateFormat formatter = new SimpleDateFormat(i);
                formatter.setLenient(false);
                Date date = formatter.parse(inputDate);
                return new SimpleDateFormat(DATE_PATTERN).format(date);
            } catch(ParseException e){
                continue;
            }
        }
        return "";
    }
    
    public static String removeNonNumbers(String zipCode){
        return zipCode.replaceAll("[\\W]", "");
    }
}
