package com.luan.getlib.models;

import com.luan.getlib.repository.EmployeeRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Random;

/**
 * @since v0.1.0
 * @author luanpozzobon
 */
@Entity
@Table(name = "employees")
public class Employee {
    
    @Id
    @Column(name = "employee_id")
    private int id;             // Id de identificação
    
    @Column(name = "fullName")
    private String fullName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "accessCode")
    private String accessCode;  // Código de acesso (Gerado automaticamente / Único)
    
    @Column(name = "salt")
    private char[] salt;        // Salting da senha
    
    @Column(name = "password")
    private char[] password;    // Senha encriptada
    
    public Employee(){ }
    
    public Employee(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
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
        } while(EmployeeRepository.accessCodeExists(accessCode));
        
        return accessCode;
    }

    public boolean isEmpty() {
        return accessCode == null;
    }
}
