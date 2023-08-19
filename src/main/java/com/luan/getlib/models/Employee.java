package com.luan.getlib.models;

import com.luan.getlib.dao.EmployeeDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * @since v0.1.0
 * @author luanpozzobon
 */
public class Employee {
    private int id;             // Id de identificação
    private String fullName;
    private String email;
    private String phone;
    private String accessCode;  // Código de acesso (Gerado automaticamente / Único)
    private String salt;        // Salting da senha
    private String password;    // Senha encriptada

    public Employee(ResultSet rSet){
        try{
            id = rSet.getInt("id");
            fullName = rSet.getString("fullName");
            email = rSet.getString("email");
            phone = rSet.getString("phone");
            accessCode = rSet.getString("accessCode");
            salt = rSet.getString("salt");
            password = rSet.getString("password");
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public Employee(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }
    
    public Employee(){
        this.id = 0;
        this.fullName = "";
        this.email = "";
        this.phone = "";
        this.accessCode = "";
        this.salt = "";
        this.password = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String generateAccessCode(){
        Random rd = new Random(System.currentTimeMillis());
        int code;
        String accessCode;
        do{
            code = rd.nextInt(1000000);
            accessCode = String.format("%07d", code);
        } while(EmployeeDAO.accessCodeExists(accessCode));
        
        return accessCode;
    }
}
