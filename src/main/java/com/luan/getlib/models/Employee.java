package com.luan.getlib.models;

import com.luan.getlib.dao.EmployeeDAO;
import java.util.Arrays;
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
    private char[] salt;        // Salting da senha
    private char[] password;    // Senha encriptada
    
    public Employee(){
        this.id = 0;
        this.fullName = null;
        this.email = null;
        this.phone = null;
        this.accessCode = null;
        Arrays.fill(salt, ' ');
        Arrays.fill(password, ' ');
    }
    
    public Employee(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public Employee(int id, String accessCode) {
        this.id = id;
        this.accessCode = accessCode;
    }

    public Employee(int id, String fullName, String email, String phone, String accessCode, char[] salt, char[] password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.accessCode = accessCode;
        this.salt = salt;
        this.password = password;
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

    public char[] getSalt() {
        return salt;
    }

    public void setSalt(char[] salt) {
        this.salt = salt;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
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
