package com.luan.getlib.views;

import com.luan.getlib.utils.InputReader;

/**
 * @since v1.3.0
 * @author luanpozzobon
 */
public class InitialView {
    private static final InputReader sc = new InputReader();
    private static final int RETURN = 0, CUSTOMER = 1, EMPLOYEE = 2;
    private static final int EXIT = 0, LOGIN = 1, REGISTER = 2;
    public static void showInitialMenu() {
        System.out.println("Bem-Vindo a biblioteca Getlib!");
        System.out.println("Para ter acesso a uma grande variedade de livros, acesse ou crie sua conta agora mesmo!");

        while(true) {
            System.out.println("1-Login");
            System.out.println("2-Cadastrar");
            System.out.println("0-Sair");

            int option = sc.getNextInt();
            switch (option) {
                case EXIT -> {
                    return;
                }
                case LOGIN -> {
                    showLoginOptions();
                }
                case REGISTER -> {
                    showRegistrationOptions();
                }
                default -> {
                    System.out.println("Opção Inválida! Tente Novamente!");
                }
            }
        }
    }

    private static void showLoginOptions() {
        System.out.println("1-Cliente");
        System.out.println("2-Funcionário");
        System.out.println("0-Voltar");

        int option = sc.getNextInt();
        switch (option) {
            case RETURN -> {
                break;
            }
            case CUSTOMER -> {
                LoginView.showCustomerLogin();
            }
            case EMPLOYEE -> {
                LoginView.showEmployeeLogin();
            }
            default -> {
                System.out.println();
            }
        }
    }

    private static void showRegistrationOptions() {
        System.out.println("1-Cliente");
        System.out.println("2-Funcionário");
        System.out.println("0-Voltar");

        int option = sc.getNextInt();
        switch (option) {
            case RETURN -> {
                break;
            }
            case CUSTOMER -> {
                RegistrationView.showCustomerRegistration();
            }
            case EMPLOYEE -> {
                // RegistrationView.showEmployeeRegistration();
            }
        }
    }
}
