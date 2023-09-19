package com.luan.getlib.views;

import com.luan.getlib.controllers.LoginController;
import com.luan.getlib.interfaces.MenuScreen;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Employee;
import com.luan.getlib.models.Result;
import com.luan.getlib.utils.InputReader;

public class LoginView {
    private static final InputReader sc = new InputReader();
    public static void showCustomerLogin() {
        System.out.print("Username: ");
        String username = sc.getNextLine();
        System.out.print("Senha: ");
        char[] password = sc.getPassword();

        final LoginController loginController = new LoginController();
        Result<Customer> result = loginController.authenticateCustomer(username, password);
        System.out.println(result.message);
        CustomerApplicationView.showMainMenu(result.entity);
    }

    public static void showEmployeeLogin() {
        System.out.println("Código de acesso: ");
        String accessCode = sc.getNextLine();
        System.out.println("Senha: ");
        char[] password = sc.getPassword();

        final LoginController loginController = new LoginController();
        Result<Employee> result = loginController.authenticateEmployee(accessCode, password);
        System.out.println(result.message);
        MenuScreen.employee(result.entity, sc);
        // TODO -
    }
}
