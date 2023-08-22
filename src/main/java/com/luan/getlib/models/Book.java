package com.luan.getlib.models;

/**
 * @since v0.2.0
 * @author luanpozzobon
 */
public class Book {
    private int id;
    private String title;
    private String genre;
    private int amount;
    private double value;
    private String parentalRating;
    
    public Book(String title, String genre, int amount, double value, String parentalRating) {
        this.title = title;
        this.genre = genre;
        this.amount = amount;
        this.value = value;
        this.parentalRating = parentalRating;
    }
    
    public Book(int id, String title, String genre, int amount, double value, String parentalRating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.amount = amount;
        this.value = value;
        this.parentalRating = parentalRating;
    }
    
    public Book(int id, String title){
        this.id = id;
        this.title = title;
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

    public String getParentalRating() {
        return parentalRating;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
