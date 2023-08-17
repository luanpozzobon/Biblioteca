package com.luan.getlib.utils;

import java.time.LocalDate;

/**
 * @since v0.1.1
 * @author luanpozzobon
 */
public class DataFormatter {
    public static LocalDate formatDate(String date){
        return LocalDate.parse(date);
    }
}
