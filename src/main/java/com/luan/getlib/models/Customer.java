package com.luan.getlib.models;

/**
 * @since v0.1.0
 * @author luanpozzobon
 */
import java.time.LocalDate;

public class Customer {
    private int id;             // Id de identificação do usuário
    private String fullName;
    private LocalDate birthDate;// Validar classificação indicativa do livro
    private Address address;    // Conversão de moeda (Endereço de cobrança)
    private String email;
    private String phone;
    private double credits;     // Valor na moeda do usuário (Cashback, descontos, etc)
    private String currency;    // Moeda local do usuário, p/ cálculo de conversão de valores
    private String username;    // Criado pelo usuário 
    private String salt;        // Salting da senha
    private String password;    // Senha encriptada

    public Customer() {
        this.id = 0;
        this.fullName = null;
        this.birthDate = null;
        this.address = null;
        this.email = null;
        this.phone = null;
        this.credits = 0;
        this.username = null;
        this.salt = null;
        this.password = null;
    }
    
    public Customer(int id, String username){
        this.id = id;
        this.username = username;
    }
    
    public Customer(String fullName, LocalDate birthDate, Address address, String email, String phone, double credits, String currency, String username, String salt, String password) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.credits = credits;
        this.currency = currency;
        this.username = username;
        this.salt = salt;
        this.password = password;
    }
    
    public Customer(int id, String fullName, LocalDate birthDate, Address address, String email, String phone, double credits, String currency, String username, String salt, String password) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
    
    
}
