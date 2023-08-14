package com.luan.getlib.models;

/**
 * @since 0.1
 * @author luanp
 */
public class Customer {
    private int id;             // Id de identificação do usuário
    private String fullName;
    private String birthDate;   // Validar classificação indicativa do livro
    private Address address;    // Conversão de moeda (Endereço de cobrança)
    private String email;
    private String phone;
    private double credits;     // Valor na moeda do usuário (Cashback, descontos, etc)
    private String username;    // Criado pelo usuário 
    private String password;    // Senha encriptada

    public Customer(String fullName, String birthDate, Address address, String email, String phone, double credits, String username, String password) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.credits = credits;
        this.username = username;
        this.password = password;
    }
}
