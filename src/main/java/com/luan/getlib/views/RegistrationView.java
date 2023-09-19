package com.luan.getlib.views;

import com.luan.getlib.controllers.RegistrationController;
import com.luan.getlib.models.*;
import com.luan.getlib.utils.DataFormatter;
import com.luan.getlib.utils.InputReader;

import java.time.LocalDate;
import java.util.List;

/**
 * @since v1.3.0
 * @author luanpozzobon
 */
public class RegistrationView {
    private static final InputReader sc = new InputReader();

    public static void showCustomerRegistration() {
        Customer customer = new Customer();
        System.out.print("Nome completo: ");
        customer.setFullName(sc.getNextLine());
        Result<LocalDate> dateResult;
        System.out.print("Data de nascimento: ");
        while(!(dateResult = DataFormatter.formatDate(sc.getNextLine())).result) {
            System.out.println(dateResult.message);
            System.out.print("Data de nascimento: ");
        }
        customer.setBirthDate(dateResult.entity);
        List<String> address = CommonView.getAddressAndCurrency();
        customer.setAddress(address.get(0));
        customer.setCurrency(address.get(1));
        System.out.print("E-Mail: ");
        customer.setEmail(sc.getNextLine());
        System.out.print("Telefone: ");
        customer.setPhone(DataFormatter.removeNonNumbers(sc.getNextLine()));
        System.out.print("Nome de usuário: ");
        final RegistrationController registrationController = new RegistrationController();
        Result<String> usernameResult;
        while(!(usernameResult = registrationController.checkUsername(sc.getNextLine())).result) {
            System.out.println(usernameResult.message);
            System.out.print("Nome de usuário: ");
        }
        customer.setUsername(usernameResult.entity);
        System.out.print("Defina uma senha: ");
        char[] firstPassword = sc.getPassword();
        System.out.print("Confirme a senha: ");
        Result<Password> passwordResult;
        while(!(passwordResult = registrationController.checkAndEncryptPassword(firstPassword, sc.getPassword())).result){
            System.out.println(passwordResult.message);
            System.out.print("Defina uma senha: ");
            firstPassword = sc.getPassword();
            System.out.print("Confirme a senha: ");
        }
        customer.setSalt(passwordResult.entity.salt);
        customer.setPassword(passwordResult.entity.password);
        Result<Customer> registrationResult = registrationController.registerCustomer(customer);
        System.out.println(registrationResult.message);
        if(registrationResult.result)
            System.out.println("Username: " + registrationResult.entity.getUsername());
    }

    public static void showEmployeeRegistration() {
        Employee employee = new Employee();
        System.out.print("Nome completo: ");
        employee.setFullName(sc.getNextLine());
        Result<LocalDate> dateResult;
        System.out.print("Data de nascimento: ");
        while(!(dateResult = DataFormatter.formatDate(sc.getNextLine())).result) {
            System.out.println(dateResult.message);
            System.out.print("Data de nascimento: ");
        }
        final RegistrationController registrationController = new RegistrationController();
        if((dateResult = registrationController.checkEmployeeAge(dateResult.entity)).result){
            System.out.println(dateResult.message);
            return;
        }
        System.out.print("E-Mail: ");
        employee.setEmail(sc.getNextLine());
        System.out.print("Telefone: ");
        employee.setPhone(DataFormatter.removeNonNumbers(sc.getNextLine()));
        employee.setAccessCode(employee.generateAccessCode());
        System.out.print("Defina uma senha: ");
        char[] firstPassword = sc.getPassword();
        System.out.print("Confirme a senha: ");
        Result<Password> passwordResult;
        while(!(passwordResult = registrationController.checkAndEncryptPassword(firstPassword, sc.getPassword())).result){
            System.out.println(passwordResult.message);
            System.out.print("Defina uma senha: ");
            firstPassword = sc.getPassword();
            System.out.print("Confirme a senha: ");
        }
        employee.setSalt(passwordResult.entity.salt);
        employee.setPassword(passwordResult.entity.password);
        Result<Employee> registrationResult = registrationController.registerEmployee(employee);
        System.out.println(registrationResult.message);
        if(registrationResult.result)
            System.out.println("Código de Acesso: " + registrationResult.entity.getAccessCode());
    }
}