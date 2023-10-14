package com.luan.getlib.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    EMPLOYEE("employee"),
    CUSTOMER("customer");

    private final String role;
}
