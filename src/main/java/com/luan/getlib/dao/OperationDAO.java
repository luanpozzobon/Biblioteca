package com.luan.getlib.dao;

import com.luan.getlib.models.Operation;
import com.luan.getlib.utils.Database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }
    
    public static Operation findRentByBookAndCustomer(int bookId, int customerId){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM operations WHERE type = 'r' and book_id = ? and customer_id = ?")){
            
            st.setInt(1, bookId);
            st.setInt(2, customerId);
            
            ResultSet rSet = st.executeQuery();
            
            if(rSet.next()) return new Operation(rSet.getInt("operation_id"),
                                                 rSet.getString("type").charAt(0),
                                                 rSet.getInt("book_id"),
                                                 rSet.getInt("customer_id"),
                                                 rSet.getDate("op_date").toLocalDate(),
                                                 rSet.getDouble("value"));
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean deleteOperation(int operationId){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("DELETE FROM operations WHERE operation_id = ?")){
            
            st.setInt(1, operationId);
            
            return st.executeUpdate() == 1;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}