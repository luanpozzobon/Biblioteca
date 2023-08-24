package com.luan.getlib.models;

import java.time.LocalDate;
import java.time.Period;

/**
 * @since v0.2.1
 * @author luanpozzobon
 */
public class Operation {
    private int id;
    private char type;
    private int bookId;
    private int customerId;
    private LocalDate operationDate;
    private double value;

    public Operation(char type, int bookId, int customerId, LocalDate operationDate, double value) {
        this.type = type;
        this.bookId = bookId;
        this.customerId = customerId;
        this.operationDate = operationDate;
        this.value = value;
    }
    
    public Operation(int id, char type, int bookId, int customerId, LocalDate operationDate, double value) {
        this.id = id;
        this.type = type;
        this.bookId = bookId;
        this.customerId = customerId;
        this.operationDate = operationDate;
        this.value = value;
    }
    
    public int getId() {
        return id;
    }

    public char getType() {
        return type;
    }

    public int getBookId() {
        return bookId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public double getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    
    
    public double calculateRentValue(){
        int period = Period.between(operationDate, LocalDate.now()).getDays();
        return period > 5 ? value * (period - 5) : 0;
    }
}
