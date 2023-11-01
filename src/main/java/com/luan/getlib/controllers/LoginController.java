package com.luan.getlib.controllers;

import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Employee;
import com.luan.getlib.models.Result;
import com.luan.getlib.repository.CustomerRepository;
import com.luan.getlib.repository.EmployeeRepository;
import com.luan.getlib.security.PasswordUtils;
import com.luan.getlib.utils.DataValidator;

/**
 * @since v1.3.0
 * @author luanpozzobon
 */
public class LoginController {
    private final String USER_NOT_FOUND = "Usuário não encontrado!";
    private final String INCORRECT_PASSWORD = "Senha incorreta!";
    private final String SUCCESS = "Usuário autenticado com sucesso!";

    public Result<Customer> authenticateCustomer(String username, char[] password) {
        Customer customer = CustomerRepository.findByUsername(username);
        if(customer.isEmpty())
            return new Result<>(false, USER_NOT_FOUND, customer);
        password = PasswordUtils.encryptPassword(password, customer.getSalt());
        if(!DataValidator.arePasswordsEqual(new String(password), new String(customer.getPassword())))
            return new Result<>(false, INCORRECT_PASSWORD, customer);

        return new Result<>(true, SUCCESS, customer);
    }

    public Result<Employee> authenticateEmployee(String accessCode, char[] password) {
        Employee employee = EmployeeRepository.findByAccessCode(accessCode);
        if(employee.isEmpty())
            return new Result<>(false, USER_NOT_FOUND, employee);
        password = PasswordUtils.encryptPassword(password, employee.getSalt());
        if(!DataValidator.arePasswordsEqual(new String(password), new String(employee.getPassword())))
            return new Result<>(false, INCORRECT_PASSWORD, employee);

        return new Result<>(true, SUCCESS, employee);
    }
}
