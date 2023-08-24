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
            System.out.println("Bem-Vindo a biblioteca Getlib!");
            System.out.println("Para ter acesso a uma grande variedade de livros, acesse ou crie sua conta agora mesmo!");
            System.out.println("1-Login");
            System.out.println("2-Cadastrar");
            System.out.println("3-Termos de Uso");
            System.out.println("0-Sair");

            switch(sc.getNextInt()){
                case 0:
                    System.exit(0);
                case 1:
                    // TODO - Login
                    System.out.println("1-Cliente");
                    System.out.println("2-Funcionário");
                    System.out.println("0-Voltar");
                    switch(sc.getNextInt()){
                        case 0:
                            break;
                        case 1:
                            cst = LoginScreen.customer();
                            MenuScreen.customer(cst);
                            break;
                        case 2:
                            emp = LoginScreen.employee();
                            MenuScreen.employee(emp);
                            break;
                        default:
                            System.out.println("Opção Inválida! Tente novamente!");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("1-Cliente");
                    System.out.println("2-Funcionário");
                    System.out.println("0-Voltar");
                    switch(sc.getNextInt()){
                        case 0:
                            break;
                        case 1:
                            RegistrationScreen.customer();
                            break;
                        case 2:
                            RegistrationScreen.employee();
                            break;
                        default:
                            System.out.println("Opção Inválida! Tente novamente!");
                            break;
                    }
                    break;
                case 3:
                    // TODO - Termos de Uso
                    break;
                default:
                    System.out.println("Opção Inválida! Tente novamente!");
            }
        }
    }
    
    private static void readTerms(){
        // TODO
    }
}
