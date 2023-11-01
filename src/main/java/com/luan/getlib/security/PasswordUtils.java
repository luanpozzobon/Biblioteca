package com.luan.getlib.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * @since v0.1.1
 * @author luanpozzobon
 */
public class PasswordUtils {
    public static char[] encryptPassword(char[] password, char[] salt){
        char[] hashedPassword;
        if(Arrays.equals(hashedPassword = hashPassword(password, salt), "".toCharArray())){
            System.out.println("Não foi possível encriptar a senha!");
            return password;
        }
        
        return hashedPassword;
    }
    
    public static char[] generateSalt(){
        SecureRandom sRandom = new SecureRandom();
        char[] salt = new char[16];
        for (int i = 0; i < salt.length; ++i){
            salt[i] = (char) sRandom.nextInt(128);
        }
        
        return salt;
    }
    
    private static char[] hashPassword(char[] password, char[] salt){
        char[] saltedPassword = new char[password.length + salt.length];
        System.arraycopy(salt, 0, saltedPassword, 0, salt.length);
        System.arraycopy(password, 0, saltedPassword, salt.length, password.length);
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
        
            byte[] hashedBytes = md.digest(Arrays.toString(saltedPassword).getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes).toCharArray();
        } catch(NoSuchAlgorithmException e){
            System.out.println("Algoritmo de encriptação não suportado: " + e);
            return null;
        }
    }
}
