package com.luan.getlib.models;

import java.time.LocalDate;

public record UserInfoDTO(String fullName, LocalDate birthDate, Address address,
                          String email, String phone, String username, UserRole role) {

    public UserInfoDTO(User user) {
        this(user.getFullName(), user.getBirthDate(), user.getAddress(), user.getEmail(),
             user.getPhone(), user.getUsername(), user.getRole());
    }
}
