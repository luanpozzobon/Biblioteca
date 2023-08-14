package com.luan.getlib.models;

/**
 *
 * @author luanp
 */
public class Employee {
    private int id;             // Id de identificação
    private String fullName;
    private String birthDate;   // Validar idade mínima (18 anos)
    private String email;
    private String phone;
    private String accessCode;  // Código de acesso (Gerado automaticamente / Único)
    private String password;    // Senha encriptada
    
}
