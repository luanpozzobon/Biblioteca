package com.luan.getlib.controllers;

import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Employee;
import com.luan.getlib.models.Password;
import com.luan.getlib.models.Result;
import com.luan.getlib.repository.CustomerRepository;
import com.luan.getlib.repository.EmployeeRepository;
import com.luan.getlib.security.PasswordUtils;
import com.luan.getlib.utils.DataValidator;

import java.time.LocalDate;

/**
 * @since v1.3.0
 * @author luanpozzobon
 */
public class RegistrationController {
    private final String INVALID_USERNAME_LENGTH = "Nome de usuário deve conter entre 4 e 100 caracteres!";
    private final String USERNAME_ALREADY_EXISTS = "Nome de usuário já cadastrado!";
    private final String VALID_USERNAME = "Nome de usuário disponível!";
    private final String VALID_ENCRYPTED_PASSWORD = "Senha encriptada com sucesso!";
    private final String ERROR_ON_SAVING = "Não foi possível salvar o usuário!";
    private final String USER_SAVED = "Usuário cadastrado com sucesso!";
    private final String NOT_MINIMUM_AGE = "A idade mínima é 18 anos!";
    private final String IS_OLD_ENOUGH = "Idade mínima atingida!";
    private final int MINIMUN_AGE = 18;

    public Result<String> checkUsername(String username) {
        if(!DataValidator.isUsernameValid(username))
            return new Result<>(false, INVALID_USERNAME_LENGTH, username);
        if(!CustomerRepository.usernameExists(username))
            return new Result<>(false, USERNAME_ALREADY_EXISTS, username);

        return new Result<>(true, VALID_USERNAME, username);
    }

    public Result<Password> checkAndEncryptPassword(char[] firstPassword, char[] secondPassword) {
        Result<String> result;
        Password encryptedPassword = new Password();
        if(!(result = DataValidator.isPasswordValid(new String(firstPassword), new String(secondPassword))).result){
            encryptedPassword.password = firstPassword;
            return new Result<>(false, result.message, encryptedPassword);
        }
        encryptedPassword.salt = PasswordUtils.generateSalt();
        encryptedPassword.password = PasswordUtils.encryptPassword(firstPassword, encryptedPassword.salt);
        return new Result<>(true, VALID_ENCRYPTED_PASSWORD, encryptedPassword);
    }

    public Result<Customer> registerCustomer(Customer customer) {
        if(!CustomerRepository.saveCustomer(customer))
            return new Result<>(false, ERROR_ON_SAVING, customer);
        return new Result<>(true, USER_SAVED, customer);
    }

    public Result<LocalDate> checkEmployeeAge(LocalDate birthDate) {
        if(!DataValidator.isOldEnough(birthDate, MINIMUN_AGE))
            return new Result<>(false, NOT_MINIMUM_AGE, birthDate);
        return new Result<>(true, IS_OLD_ENOUGH, birthDate);
    }

    public Result<Employee> registerEmployee(Employee employee) {
        if(!EmployeeRepository.saveEmployee(employee))
            return new Result<>(false, ERROR_ON_SAVING, employee);
        return new Result<>(true, USER_SAVED, employee);
    }
}
