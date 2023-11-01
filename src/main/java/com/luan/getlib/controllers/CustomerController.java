package com.luan.getlib.controllers;

import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Result;
import com.luan.getlib.repository.CustomerRepository;
import com.luan.getlib.security.PasswordUtils;
import com.luan.getlib.utils.DataFormatter;
import com.luan.getlib.utils.DataValidator;

import java.util.List;

/**
 * @since v1.3.0
 * @author luanpozzobon
 */
public class CustomerController {
    private final String UPDATE_ERROR = "Operação cancelada! Os novos dados não foram salvos!";
    private final String UPDATE_SUCCESS = "Atualização de dados concluída com sucesso!";

    public Result<Customer> makeRecharge(Customer customer, double rechargeValue) {
        final String NEGATIVE_VALUE = "Valor de recarga não pode ser negativo!";
        Customer originalCustomer = customer.clone();
        if(rechargeValue < 0)
            return new Result<>(false, NEGATIVE_VALUE, customer);
        customer.setCredits(customer.getCredits() + rechargeValue);
        if(!CustomerRepository.updateCustomer(customer)) {
            customer.rollback(originalCustomer);
            return new Result<>(false, UPDATE_ERROR, customer);
        }
        return new Result<>(true, UPDATE_SUCCESS, customer);
    }

    public Result<Customer> changeAddressAndCurrency(Customer customer, List<String> newAddress) {
        final String EMPTY_ADDRESS = "O endereço não foi preenchido!";
        if(newAddress.isEmpty())
            return new Result<>(false, EMPTY_ADDRESS, customer);
        Customer originalCustomer = customer.clone();
        customer.setAddress(newAddress.get(0));
        customer.setCurrency(newAddress.get(1));
        if(!CustomerRepository.updateCustomer(customer)){
            customer.rollback(originalCustomer);
            return new Result<>(false, UPDATE_ERROR, customer);
        }
        return new Result<>(true, UPDATE_SUCCESS, customer);
    }

    public Result<Customer> changeEmail(Customer customer, String newEmail) {
        Customer originalCustomer = customer.clone();
        customer.setEmail(newEmail);
        if(!CustomerRepository.updateCustomer(customer)) {
            customer.rollback(originalCustomer);
            return new Result<>(false, UPDATE_ERROR, customer);
        }
        return new Result<>(true, UPDATE_SUCCESS, customer);
    }

    public Result<Customer> changePhone(Customer customer, String newPhone) {
        Customer originalCustomer = customer.clone();
        customer.setPhone(newPhone);
        if(!CustomerRepository.updateCustomer(customer)) {
            customer.rollback(originalCustomer);
            return new Result<>(false, UPDATE_ERROR, customer);
        }
        return new Result<>(true, UPDATE_SUCCESS, customer);
    }

    public Result<Customer> changeUsername(Customer customer, String newUsername) {
        final String INVALID_USERNAME_LENGTH = "Nome de usuário deve conter entre 4 e 100 caracteres!";
        final String USERNAME_ALREADY_EXISTS = "Nome de usuário já cadastrado!";
        Customer originalCustomer = customer.clone();
        if(!DataValidator.isUsernameValid(newUsername))
            return new Result<>(false, INVALID_USERNAME_LENGTH, customer);
        if(CustomerRepository.usernameExists(newUsername))
            return new Result<>(false, USERNAME_ALREADY_EXISTS, customer);
        customer.setUsername(newUsername);
        if(!CustomerRepository.updateCustomer(customer)) {
            customer.rollback(originalCustomer);
            return new Result<>(false, UPDATE_ERROR, customer);
        }
        return new Result<>(true, UPDATE_SUCCESS, customer);
    }

    public Result<Customer> changePassword(Customer customer, char[] firstPassword, char[] secondPassword) {
        Result<String> passwordResult = DataValidator.isPasswordValid(new String(firstPassword), new String(secondPassword));
        if(!passwordResult.result)
            return new Result<>(false, passwordResult.message, customer);
        Customer originalCustomer = customer.clone();
        customer.setSalt(PasswordUtils.generateSalt());
        customer.setPassword(PasswordUtils.encryptPassword(firstPassword, customer.getSalt()));
        if(!CustomerRepository.updateCustomer(customer)) {
            customer.rollback(originalCustomer);
            return new Result<>(false, UPDATE_ERROR, customer);
        }
        return new Result<>(true, UPDATE_SUCCESS, customer);
    }

    public Result<Customer> deleteCustomer(Customer customer, char[] password) {
        final String PASSWORD_DOESNT_MATCH = "As senhas não conferem";
        password = PasswordUtils.encryptPassword(password ,customer.getSalt());
        if(!DataValidator.arePasswordsEqual(new String(password), new String(customer.getPassword())))
            return new Result<>(false, PASSWORD_DOESNT_MATCH, customer);
        if(!CustomerRepository.deleteCustomer(customer))
            return new Result<>(false, UPDATE_ERROR, customer);
        return new Result<>(true, UPDATE_SUCCESS, new Customer());
    }
}
