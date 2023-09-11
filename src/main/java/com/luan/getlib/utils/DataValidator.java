package com.luan.getlib.utils;

import com.luan.getlib.repository.CustomerRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since v0.1.1
 * @author luanpozzobon
 */
public class DataValidator {
    private static final Pattern UPPERCASE_CHAR = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE_CHAR = Pattern.compile("[a-z]");
    private static final Pattern NUMERIC_CHAR = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHAR = Pattern.compile("[!@#$%¨&*\\-_+=^]");
    private static final Pattern ALLOWED_CHAR = Pattern.compile("^[!@#$%¨&*\\-_+=^0-9a-zA-Z]+$");
    
    public static boolean isUsernameValid(String username){
        if(!isLengthValid(username, 4, 100)){
            System.out.println("Nome de Usuário Inválido! A senha deve conter entre 4 e 100 caracteres!");
            return false;
        }
        
        if(usernameExists(username)){
            System.out.printf("Nome de Usuário Inválido! O Nome de Usuário: %s já está em uso!\n", username);
            return false;
        }
        
        return true;
    }
    
    public static boolean isPasswordValid(char[] password, char[] password2){
        String p1 = new String(password);
        String p2 = new String(password2);
        if(!isLengthValid(p1, 8, 32)){
            System.out.println("Senha Inválida! A senha deve conter entre 8 e 32 caracteres!");
            return false;
        }
        
        if(!hasUppercaseCharacters(p1)){
            System.out.println("Senha Inválida! A senha deve conter pelo menos uma letra maiúscula!");
            return false;
        }
        
        if(!hasLowercaseCharacters(p1)){
            System.out.println("Senha Inválida! A senha deve conter pelo menos uma letra minúscula!");
            return false;
        }
        
        if(!hasNumericCharacters(p1)){
            System.out.println("Senha Inválida! A senha deve conter pelo menos um caractere numérico!");
            return false;
        }
        
        if(!hasSpecialCharacters(p1)){
            System.out.println("Senha Inválida! A senha deve conter pelo menos um caractere especial!");
            return false;
        }
        
        if(!hasInvalidCharacters(p1)){
            System.out.println("Senha Inválida! A senha contém caracteres inválidos!");
            return false;
        }
        
        if(!arePasswordsEqual(p1, p2)){
            System.out.println("Senhas não conferem!");
            return false;
        }
        
        return true;
    }
    
    public static boolean isOldEnough(LocalDate age, int requiredAge){
        return (Period.between(age, LocalDate.now()).getYears() >= requiredAge);
    }
    
    public static boolean arePasswordsEqual(String password, String password2){
        return password.equals(password2);
    }
    
    private static boolean usernameExists(String username){
        return CustomerRepository.usernameExists(username);
    }
    
    private static boolean isLengthValid(String value, int minLength, int maxLength){
        return value.length() >= minLength && value.length() <= maxLength;
    }
    
    private static boolean hasUppercaseCharacters(String password){
        Matcher m = UPPERCASE_CHAR.matcher(password);
        
        return m.find();
    }
    
    private static boolean hasLowercaseCharacters(String password){
        Matcher m = LOWERCASE_CHAR.matcher(password);
        
        return m.find();
    }
    
    private static boolean hasNumericCharacters(String password){
        Matcher m = NUMERIC_CHAR.matcher(password);
        
        return m.find();
    }
    
    private static boolean hasSpecialCharacters(String password){
        Matcher m = SPECIAL_CHAR.matcher(password);
        
        return m.find();
    }
    
    private static boolean hasInvalidCharacters(String password){
        Matcher m = ALLOWED_CHAR.matcher(password);
        
        return m.matches();
    }
}
