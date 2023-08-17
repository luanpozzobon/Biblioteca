package com.luan.getlib.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @since v0.1.1
 * @author luanpozzobon
 */
public class PasswordUtils {
    public static String encryptPassword(String password, String salt){
        String hashedPassword;
        if((hashedPassword = hashPassword(password, salt)).equals("")){
            System.out.println("Não foi possível encriptar a senha!");
            return password;
        }
        
        return hashedPassword;
    }
    
    public static String generateSalt(){
        SecureRandom sRandom = new SecureRandom();
        byte[] saltBytes = new byte[16];
        sRandom.nextBytes(saltBytes);
        
        return Base64.getEncoder().encodeToString(saltBytes);
    }
    
    private static String hashPassword(String password, String salt){
        String saltedPassword = salt + password;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
        
            byte[] hashedBytes = md.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return "";
        }
    }
}
