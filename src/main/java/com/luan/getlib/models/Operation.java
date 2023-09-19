package com.luan.getlib.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

/**
 * @since v0.2.1
 * @author luanpozzobon
 */

@Entity
@Table(name = "operations")
public class Operation {
    
    @Id
    @Column(name = "operation_id")
    private int id;
    
    @Column(name = "type")
    private char type;
    
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;
    
    @Column(name = "op_date")
    private LocalDate operationDate;
    
    @Column(name = "value")
    private double value;

    public Operation(){ }

    public Operation(char type, Book book, Customer customer, LocalDate operationDate, double value) {
        this.type = type;
        this.book = book;
        this.customer = customer;
        this.operationDate = operationDate;
        this.value = value;
    }
    
    public int getId() {
        return id;
    }

    public char getType() {
        return type;
    }
    
    public String getTypeAsString(){
        return type == 'r' ? "Aluguel" : "Compra";
    }

    public Book getBook() {
        return book;
    }

    public Customer getCustomer() {
        return customer;
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

    public void setBook(Book book) {
        this.book = book;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
    
    public double calculateExchangeValue(){
        int period = Period.between(operationDate, LocalDate.now()).getMonths();
        return period < 25 ? value - (value * 0.0208 * period) : value - (value * 0.5);
    }

    public boolean isEmpty() {
        return book == null;
    }
}
