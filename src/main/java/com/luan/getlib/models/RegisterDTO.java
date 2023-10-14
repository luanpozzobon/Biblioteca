package com.luan.getlib.models;

import java.time.LocalDate;

public record RegisterDTO(String fullName, LocalDate birthDate, Address address, String email, String phone,
                          String username, String password, UserRole role) { }
