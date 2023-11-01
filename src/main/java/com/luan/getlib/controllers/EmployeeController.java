package com.luan.getlib.controllers;

import com.luan.getlib.models.Employee;
import com.luan.getlib.models.Result;
import com.luan.getlib.repository.EmployeeRepository;
import com.luan.getlib.security.PasswordUtils;
import com.luan.getlib.utils.DataValidator;

/**
 * @since v1.3.1
 * @author luanpozzobon
 */
public class EmployeeController {
    private final String UPDATE_ERROR = "Operação cancelada! Os novos dados não foram salvos!";
    private final String UPDATE_SUCCESS = "Atualização de dados concluída com sucesso!";

    public Result<Employee> changeEmail(Employee employee, String email) {
        Employee originalEmployee = employee.clone();
        employee.setEmail(email);
        if(!EmployeeRepository.updateEmployee(employee)) {
            employee.rollback(originalEmployee);
            return new Result<>(false, UPDATE_ERROR, employee);
        }
        return new Result<>(true, UPDATE_SUCCESS, employee);
    }

    public Result<Employee> changePhone(Employee employee, String phone) {
        Employee originalEmployee = employee.clone();
        employee.setPhone(phone);
        if(!EmployeeRepository.updateEmployee(employee)) {
            employee.rollback(originalEmployee);
            return new Result<>(false, UPDATE_ERROR, employee);
        }
        return new Result<>(true, UPDATE_SUCCESS, employee);
    }

    public Result<Employee> changePassword(Employee employee, char[] firstPassword, char[] secondPassword) {
        Result<String> passwordResult = DataValidator.isPasswordValid(new String(firstPassword), new String(secondPassword));
        if(!passwordResult.result)
            return new Result<>(false, passwordResult.message, employee);
        Employee originalEmployee = employee.clone();
        employee.setSalt(PasswordUtils.generateSalt());
        employee.setPassword(PasswordUtils.encryptPassword(firstPassword, employee.getSalt()));
        if(!EmployeeRepository.updateEmployee(employee)) {
            employee.rollback(originalEmployee);
            return new Result<>(false, UPDATE_ERROR, employee);
        }
        return new Result<>(true, UPDATE_SUCCESS, employee);
    }

    public Result<Employee> deleteCustomer(Employee employee, char[] password) {
        final String PASSWORD_DOESNT_MATCH = "As senhas não conferem";
        password = PasswordUtils.encryptPassword(password ,employee.getSalt());
        if(!DataValidator.arePasswordsEqual(new String(password), new String(employee.getPassword())))
            return new Result<>(false, PASSWORD_DOESNT_MATCH, employee);
        if(!EmployeeRepository.deleteEmployee(employee))
            return new Result<>(false, UPDATE_ERROR, employee);
        return new Result<>(true, UPDATE_SUCCESS, new Employee());
    }
}
