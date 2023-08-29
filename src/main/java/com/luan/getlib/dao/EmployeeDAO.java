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
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return false;
        }
    }
    
    public static Employee findById(int id){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM employees WHERE employee_id = ?")){
            
            st.setInt(1, id);
            
            ResultSet rSet = st.executeQuery();
            
            if(rSet.next()) return new Employee(rSet.getInt("employee_id"),
                                                rSet.getString("fullName"),
                                                rSet.getString("email"),
                                                rSet.getString("phone"),
                                                rSet.getString("accessCode"),
                                                rSet.getString("salt"),
                                                rSet.getString("password"));
            
            return null;
        } catch(SQLException e){
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return null;
        }       
    }
    
    public static Employee findByAccessCode(String accessCode){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM employees WHERE accessCode = ?")){
            st.setString(1, accessCode);
            ResultSet rSet = st.executeQuery();
            
            if(rSet.next()) return new Employee(rSet.getInt("employee_id"),
                                                rSet.getString("accessCode"));
            
            return null;
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return null;
        }
    }
    
    public static boolean updateEmployee(Employee employee){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE employees SET salt = ?, password = ? WHERE employee_id = ?")){
            
            st.setString(1, employee.getSalt());
            st.setString(2, employee.getPassword());
            st.setInt(3, employee.getId());
            
            return st.executeUpdate() == 1;
        } catch(SQLException e){
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return false;
        }
    }
    
    public static boolean accessCodeExists(String accessCode){
        return findByAccessCode(accessCode) != null;
    }
    
    public static boolean deleteEmployee(Employee employee){
        try(Connection conn = Database.getConnection();
            PreparedStatement st = conn.prepareStatement("DELETE FROM employees WHERE employee_id = ?")){
            
            st.setInt(1, employee.getId());
            
            return st.executeUpdate() == 1;
        } catch(SQLException e) {
            System.out.println("Ocorreu um erro durante o acesso ao banco de dados: " + e);
            return false;
        }
    }
}
