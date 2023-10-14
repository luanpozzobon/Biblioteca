package com.luan.getlib.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;
    private String title;
    private List<String> genre;
    private int amount;
    private double price;
    @Column(name = "PR")
    private int parentalRating;

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
