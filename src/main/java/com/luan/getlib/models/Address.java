package com.luan.getlib.models;

/**
 *
 * @author luanp
 */
public class Address {
    private String nation;
    private String state;
    private String city;
    private String street;
    private String number;
    private String zipCode; // Futura implementação de API, para consulta de endereço com ZIP code

    public Address() {}
    
    public Address(String nation, String state, String city, String zipCode) {
        this.nation = nation;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
    }
    
    public Address(String nation, String state, String city, String street, String number, String zipCode) {
        this.nation = nation;
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNation() {
        return nation;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }
}
