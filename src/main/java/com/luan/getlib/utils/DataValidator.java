package com.luan.getlib.utils;

import com.luan.getlib.models.Result;

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
    private static final String INVALID_PASSWORD_LENGTH = "A senha deve conter entre 8 e 32 caracteres!";
    private static final String PASSWORD_HAS_NO_UPPERCASE = "A senha deve conter ao menos uma letra maíuscula!";
    private static final String PASSWORD_HAS_NO_LOWERCASE = "A senha deve conter ao menos uma letra minúscula!";
    private static final String PASSWORD_HAS_NO_NUMBER = "A senha deve conter ao menos um caractere numérico!";
    private static final String PASSWORD_HAS_NO_SPECIAL = "A senha deve conter ao menos um caractere especial!";
    private static final String PASSWORD_HAS_INVALID_CHARACTER = "A senha contém caracteres inválidos!";
    private static final String PASSWORD_DOESNT_MATCH = "As senhas não conferem";
    private static final String VALID_PASSWORD = "Senha válida!";
    
    public static boolean isUsernameValid(String username){
        return isLengthValid(username, 4, 100);
    }
    
    public static Result<String> isPasswordValid(String firstPassword, String secondPassword){
        if(!isLengthValid(firstPassword, 8, 32))
            return new Result<>(false, INVALID_PASSWORD_LENGTH, firstPassword);
        if(!hasUppercaseCharacters(firstPassword))
            return new Result<>(false, PASSWORD_HAS_NO_UPPERCASE, firstPassword);
        if(!hasLowercaseCharacters(firstPassword))
            return new Result<>(false, PASSWORD_HAS_NO_LOWERCASE, firstPassword);
        if(!hasNumericCharacters(firstPassword))
            return new Result<>(false, PASSWORD_HAS_NO_NUMBER, firstPassword);
        if(!hasSpecialCharacters(firstPassword))
            return new Result<>(false, PASSWORD_HAS_NO_SPECIAL, firstPassword);
        if(!hasInvalidCharacters(firstPassword))
            return new Result<>(false, PASSWORD_HAS_INVALID_CHARACTER, firstPassword);
        if(!arePasswordsEqual(firstPassword, secondPassword))
            return new Result<>(false, PASSWORD_DOESNT_MATCH, firstPassword);
        return new Result<>(true, VALID_PASSWORD, firstPassword);
    }
    
    public static boolean isOldEnough(LocalDate age, int requiredAge){
        return (Period.between(age, LocalDate.now()).getYears() >= requiredAge);
    }
    
    public static boolean arePasswordsEqual(String password, String password2){
        return password.equals(password2);
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
