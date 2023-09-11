package com.luan.getlib.models;

/**
 * @since v0.1.0
 * @author luanpozzobon
 */
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private int id;             // Id de identificação do usuário
    
    @Column(name = "fullName")
    private String fullName;
    
    @Column(name = "birthDate")
    private LocalDate birthDate;// Validar classificação indicativa do livro
    
    @Column(name = "address")
    private String address;    // Conversão de moeda (Endereço de cobrança)
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "credits")
    private double credits;     // Valor na moeda do usuário (Cashback, descontos, etc)
    
    @Column(name = "currency")
    private String currency;    // Moeda local do usuário, p/ cálculo de conversão de valores
    
    @Column(name = "username")
    private String username;    // Criado pelo usuário 
    
    @Column(name = "salt")
    private char[] salt;        // Salting da senha
    
    @Column(name = "password")
    private char[] password;    // Senha encriptada

    public Customer() { }
    
    public Customer(String fullName, LocalDate birthDate, Address address, String email, String phone, double credits, String currency, String username, char[] salt, char[] password) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address.toString();
        this.email = email;
        this.phone = phone;
        this.credits = credits;
        this.currency = currency;
        this.username = username;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address.toString();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
