package com.luan.getlib.repository;

import com.luan.getlib.models.Book;

import java.util.HashMap;
import java.util.List;

/**
 * @since v1.2.0
 * @author luanpozzobon
 */
public class BookRepository {
    private static final DatabaseAccess<Book> bookDatabase = new DatabaseAccess<>();
    public static boolean saveBook(Book book){
        return bookDatabase.save(book);
    }

    public static Book findById(int id) {
        List<Book> books = bookDatabase.find(Book.class, new HashMap<>(){{
            put("id", id);
        }});
        if (books.isEmpty()) return null;

        return books.get(0);
    }
    
    public static List<Book> findByTitle(String title){
        return bookDatabase.find(Book.class, new HashMap<>(){{
            put("title", title);
        }});
    }
    
    public static List<Book> getAllBooks(){
        return bookDatabase.find(Book.class, new HashMap<>());
    }
    
    public static boolean updateBook(Book book){
        return bookDatabase.update(book);
    }
}
