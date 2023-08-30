package com.luan.getlib;

import com.luan.getlib.interfaces.LoginScreen;
import com.luan.getlib.interfaces.MenuScreen;
import com.luan.getlib.interfaces.RegistrationScreen;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Employee;
import com.luan.getlib.utils.InputReader;

/**
 * @since v0.1.0
 * @author luanpozzobon
 */

public class Getlib {
    private static final InputReader sc = new InputReader();
    private static Employee emp;
    private static Customer cst;
            
    public static void main(String[] args) {
        while(true){
            displayMainMenu();
            int option = sc.getNextInt();

            switch(option){
                case 0:
                    sc.closeScanner();
                    System.exit(0);
                case 1:
                    handleLoginMenu();
                    break;
                case 2:
                    handleRegisterMenu();
                    break;
                default:
                    System.out.println("Opção Inválida! Tente novamente!");
            }
        }
    }
    
    private static void displayMainMenu(){
        System.out.println("Bem-Vindo a biblioteca Getlib!");
        System.out.println("Para ter acesso a uma grande variedade de livros, acesse ou crie sua conta agora mesmo!");
        System.out.println("1-Login");
        System.out.println("2-Cadastrar");
        System.out.println("0-Sair");
    }
    
    private static void handleLoginMenu(){
        System.out.println("1-Cliente");
        System.out.println("2-Funcionário");
        System.out.println("0-Voltar");
        
        int option = sc.getNextInt();
        
        switch(option){
            case 0:
                break;
            case 1:
                cst = LoginScreen.customer(sc);
                MenuScreen.customer(cst, sc);
                break;
            case 2:
                emp = LoginScreen.employee(sc);
                MenuScreen.employee(emp, sc);
                break;
            default:
                System.out.println("Opção Inválida! Tente novamente!");
                break;
        }
    }
    
    private static void handleRegisterMenu(){
        System.out.println("1-Cliente");
        System.out.println("2-Funcionário");
        System.out.println("0-Voltar");
        
        int option = sc.getNextInt();
        
        switch(option){
            case 0:
                break;
            case 1:
                RegistrationScreen.customer(sc);
                break;
            case 2:
                RegistrationScreen.employee(sc);
                break;
            default:
                System.out.println("Opção Inválida! Tente novamente!");
                break;
        }
    }
}
