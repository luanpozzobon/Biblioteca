package com.luan.getlib.models;

public record PasswordDTO(String oldPassword, String newPassword, String confirmedPassword) {

    public boolean arePasswordsEqual() {
        return newPassword.equals(confirmedPassword);
    }
}
