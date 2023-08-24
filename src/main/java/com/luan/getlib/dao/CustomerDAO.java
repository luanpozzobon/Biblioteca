package com.luan.getlib.dao;

import com.luan.getlib.models.Address;
import com.luan.getlib.utils.Database;
import com.luan.getlib.models.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

/**
 * @since v0.1.1
 * @author luanpozzobon
 */
public class CustomerDAO {
    public static boolean saveCustomer(Customer cst){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO customers VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")){
            
            st.setString(1, cst.getFullName());
            st.setDate(2, Date.valueOf(cst.getBirthDate()));
            st.setString(3, cst.getAddress().toString());
            st.setString(4, cst.getCurrency());
            st.setString(5, cst.getEmail());
            st.setString(6, cst.getPhone());
            st.setString(7, cst.getUsername());
            st.setDouble(8, cst.getCredits());
            st.setString(9, cst.getSalt());
            st.setString(10, cst.getPassword());
            
            st.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static Customer findById(int id){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM customers WHERE customer_id = ?")){
            
            st.setInt(1, id);
            ResultSet rSet = st.executeQuery();
            
            if(rSet.next()) return new Customer(rSet.getInt("customer_id"),
                                                rSet.getString("fullName"),
                                                rSet.getDate("birthDate").toLocalDate(),
                                                new Address(rSet.getString("address")),
                                                rSet.getString("email"),
                                                rSet.getString("phone"),
                                                rSet.getDouble("credits"),
                                                rSet.getString("currency"),
                                                rSet.getString("username"),
                                                rSet.getString("salt"),
                                                rSet.getString("password"));
        } catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static Customer findByUsername(String username){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM customers WHERE username = ?")){
            st.setString(1, username);
            ResultSet rSet = st.executeQuery();
            
            if(rSet.next()) return new Customer(rSet.getInt("customer_id"),
                                                rSet.getString("username"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean usernameExists(String username){
        return findByUsername(username) != null;
    }
    
    public static boolean updateCustomer(Customer cst) {
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE customers SET address = ?, currency = ?, email = ?, phone = ?, username = ?, salt = ?, password = ? WHERE customer_id = ?")){
            
            st.setString(1, cst.getAddress().toString());
            st.setString(2, cst.getCurrency());
            st.setString(3, cst.getEmail());
            st.setString(4, cst.getPhone());
            st.setString(5, cst.getUsername());
            st.setString(6, cst.getSalt());
            st.setString(7, cst.getPassword());
            st.setInt(8, cst.getId());
            
            return st.executeUpdate() == 1;           
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}