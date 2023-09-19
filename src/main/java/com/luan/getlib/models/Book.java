package com.luan.getlib.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

/**
 * @since v0.2.0
 * @author luanpozzobon
 */

@Entity
@Table(name = "books")
public class Book implements Serializable {
    
    @Id
    @Column(name = "book_id")
    private int id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "genre")
    private String genre;
    
    @Column(name = "amount")
    private int amount;
    
    @Column(name = "value")
    private double value;
    
    @Column(name = "parentalRating")
    private int parentalRating;
    
    public Book(){ }
    
    public Book(String title, String genre, int amount, double value, int parentalRating) {
        this.title = title;
        this.genre = genre;
        this.amount = amount;
        this.value = value;
        this.parentalRating = parentalRating;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getAmount() {
        return amount;
    }

    public double getValue() {
        return value;
    }

    public int getParentalRating() {
        return parentalRating;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return title == null;
    }

    public String getBasicInfo() {
        return "Id: " + id +
               "\nTítulo: " + title;
    }

    public String getFullInfo() {
        return "Id: " + id +
               "\nTítulo: " + title +
               "\nGênero: " + genre +
               "\nClassificação Indicativa: " + parentalRating +
               "\nQuantidade: " + amount;
    }
}
