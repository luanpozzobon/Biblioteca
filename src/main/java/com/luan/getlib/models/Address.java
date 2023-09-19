package com.luan.getlib.models;

/**
 * @since v0.1.0
 * @author luanpozzobon
 */
public class Address {
    private String nation;
    private String state;
    private String city;
    private String street;
    private String number;
    private String zipCode;

    public Address() { }

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
    
    public Address(String address){
        String[] values = address.split(",");        
        
        this.nation = values[4];
        this.state = values[3];
        this.city = values[2];
        this.street = values[0];
        this.number = values[1];
        this.zipCode = values[5];
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

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return street + ',' + number + ',' + city + ',' + state + ',' + nation + ',' + zipCode;
    }

    public boolean isEmpty() {
        return zipCode == null;
    }
}
