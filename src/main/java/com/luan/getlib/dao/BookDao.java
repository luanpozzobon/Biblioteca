package com.luan.getlib.dao;

import com.luan.getlib.models.Book;
import com.luan.getlib.utils.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @since v0.2.0
 * @author luanpozzobon
 */
public class BookDao {
    public static boolean saveBook(Book book){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO books VALUES(DEFAULT, ?, ?, ?, ?, ?)")){
            
            st.setString(1, book.getTitle());
            st.setString(2, book.getGenre());
            st.setInt(3, book.getAmount());
            st.setDouble(4, book.getValue());
            st.setInt(5, book.getParentalRating());
            
            return st.executeUpdate() == 1;
        } catch(SQLException e){
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return false;
        }
    }
    
    public static Book findById(int id){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM books WHERE book_id = ?")){
            
            st.setInt(1, id);
            
            ResultSet rSet = st.executeQuery();
            if(rSet.next()) return new Book(rSet.getInt("book_id"),
                                            rSet.getString("title"),
                                            rSet.getString("genre"),
                                            rSet.getInt("amount"),
                                            rSet.getDouble("value"),
                                            rSet.getInt("parentalRating"));
            
            return null;
            
        } catch(SQLException e){
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return null;
        }
    }
    
    public static List<Book> findByTitle(String title){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT book_id, title FROM books WHERE title LIKE ? ORDER BY title ASC")){
            
            st.setString(1, "%" + title + "%");
            
            ResultSet rSet = st.executeQuery();
            
            List<Book> books = new ArrayList<>();
            
            while(rSet.next()){
                books.add(new Book(rSet.getInt("book_id"),
                                   rSet.getString("title")));
            }
            return books;
        } catch(SQLException e){
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return null;
        }
    }
    
    public static List<Book> getAllBooks(){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT book_id, title FROM books ORDER BY title ASC")){
            ResultSet rSet = st.executeQuery();
            
            List<Book> books = new ArrayList<>();
            
            while(rSet.next()){
                books.add(new Book(rSet.getInt("book_id"),
                                   rSet.getString("title")));
            }
            return books;
        } catch(SQLException e){
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return null;
        }
    }
    
    public static boolean updateBook(Book book){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE books SET amount = ?, value = ? WHERE book_id = ?")){
            
            st.setInt(1, book.getAmount());
            st.setDouble(2, book.getValue());
            st.setInt(3, book.getId());
            
            return st.executeUpdate() == 1;
        } catch(SQLException e){
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return false;
        }
    }
}
