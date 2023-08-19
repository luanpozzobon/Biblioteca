package com.luan.getlib.dao;

import com.luan.getlib.models.Employee;
import com.luan.getlib.utils.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @since v0.1.2
 * @author luanpozzobon
 */
public class EmployeeDAO {
    public static boolean accessCodeExists(String accessCode){
        return findByAccessCode(accessCode) != null;
    }
    
    public static boolean saveEmployee(Employee emp){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO employees VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)")){
            st.setString(1, emp.getFullName());
            st.setString(2, emp.getEmail());
            st.setString(3, emp.getPhone());
            st.setString(4, emp.getAccessCode());
            st.setString(5, emp.getSalt());
            st.setString(6, emp.getPassword());
            
            st.executeUpdate();
            
            return true;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static Employee findByAccessCode(String accessCode){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM employees WHERE accessCode = ?")){
            st.setString(1, accessCode);
            ResultSet rSet = st.executeQuery();
            
            if(rSet.next()) return new Employee(rSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
