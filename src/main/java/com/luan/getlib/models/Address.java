package com.luan.getlib.models;

import jakarta.persistence.Embeddable;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Address {
    private String nation;
    private String state;
    private String city;
    private String street;
    private String number;
    private String zipCode;

    public Address(String nation, String state, String city, String zipCode) {
        this.nation = nation;
        this.state = state;
        this.city = city;
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

    @Override
    public String toString() {
        return street + ',' + number + ',' + city + ',' + state + ',' + nation + ',' + zipCode;
    }
}
