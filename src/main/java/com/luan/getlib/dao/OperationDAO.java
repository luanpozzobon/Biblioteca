package com.luan.getlib.dao;

import com.luan.getlib.models.Operation;
import com.luan.getlib.utils.Database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @since v0.2.1
 * @author luanpozzobon
 */
public class OperationDAO {
    public static boolean saveOperation(Operation op){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO operations VALUES(DEFAULT, ?, ?, ?, ?, ?)")){
            
            st.setString(1, String.valueOf(op.getType()));
            st.setInt(2, op.getBookId());
            st.setInt(3, op.getCustomerId());
            st.setDate(4, Date.valueOf(op.getOperationDate()));
            st.setDouble(5, op.getValue());
            
            return st.executeUpdate() == 1;
        } catch (SQLException e){
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return false;
        }
    }
    
    public static Operation findById(int operationId){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM operations WHERE operation_id = ?")){
            
            st.setInt(1, operationId);
            
            ResultSet rSet = st.executeQuery();
            
            return new Operation(rSet.getInt("operation_id"),
                                 rSet.getString("type").charAt(0),
                                 rSet.getInt("book_id"),
                                 rSet.getInt("customer_id"),
                                 rSet.getDate("op_date").toLocalDate(),
                                 rSet.getDouble("value"));
        } catch(SQLException e) {
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return null;
        }
    }
    
    public static Operation findByBookAndCustomer(int bookId, int customerId){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM operations WHERE book_id = ? and customer_id = ?")){
            
            st.setInt(1, bookId);
            st.setInt(2, customerId);
            
            ResultSet rSet = st.executeQuery();
            
            if(rSet.next()) return new Operation(rSet.getInt("operation_id"),
                                                 rSet.getString("type").charAt(0),
                                                 rSet.getInt("book_id"),
                                                 rSet.getInt("customer_id"),
                                                 rSet.getDate("op_date").toLocalDate(),
                                                 rSet.getDouble("value"));
            
            return null;
        } catch(SQLException e){
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return null;
        }
    }
    
    public static List<Operation> findRentByCustomer(int customerId){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM operations WHERE type = 'r' and customer_id = ?")){
            
            st.setInt(1, customerId);
            
            ResultSet rSet = st.executeQuery();
            
            List<Operation> operations = new ArrayList<>();
            
            while (rSet.next()) operations.add(new Operation(rSet.getInt("operation_id"),
                                                 rSet.getString("type").charAt(0),
                                                 rSet.getInt("book_id"),
                                                 rSet.getInt("customer_id"),
                                                 rSet.getDate("op_date").toLocalDate(),
                                                 rSet.getDouble("value")));
            
            return operations;
            
        } catch(SQLException e){
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return null;
        }
    }
    
    public static Map<String, Operation> findByCustomerId(int customerId){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT operations.*, books.title FROM operations LEFT JOIN books ON operations.book_id = books.book_id WHERE customer_id = ? AND (type = 'r' OR type = 'p')")){
            
            st.setInt(1, customerId);
            
            ResultSet rSet = st.executeQuery();
            
            Map<String, Operation> operations = new TreeMap<>();
            while(rSet.next()){
                operations.put(rSet.getString("title"), new Operation(rSet.getInt("operation_id"),
                                                                      rSet.getString("type").charAt(0),
                                                                      rSet.getInt("book_id"),
                                                                      rSet.getInt("customer_id"),
                                                                      rSet.getDate("op_date").toLocalDate(),
                                                                      rSet.getDouble("value")));
            }
            
            return operations;
        } catch(SQLException e){
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return null;
        }
    }
    
    public static boolean updateOperation(Operation operation){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE operations SET type = ?, op_date = ?, value = ? WHERE operation_id = ?")){
            
            st.setString(1, String.valueOf(operation.getType()));
            st.setDate(2, Date.valueOf(operation.getOperationDate()));
            st.setDouble(3, operation.getValue());
            st.setInt(4, operation.getId());
            
            return st.executeUpdate() == 1;
        } catch(SQLException e) {
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return false;
        }
    }
    
    public static boolean deleteOperation(int operationId){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("DELETE FROM operations WHERE operation_id = ?")){
            
            st.setInt(1, operationId);
            
            return st.executeUpdate() == 1;
        } catch(SQLException e) {
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return false;
        }
    }
}