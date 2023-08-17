package com.luan.getlib;

import com.luan.getlib.dao.CustomerDAO;
import com.luan.getlib.models.Address;
import com.luan.getlib.models.Customer;
import com.luan.getlib.security.PasswordUtils;
import com.luan.getlib.service.ZipCodeService;
import com.luan.getlib.utils.DataFormatter;
import com.luan.getlib.utils.DataValidator;
import com.luan.getlib.utils.InputReader;
import java.time.LocalDate;

/**
 * @since v0.1.0
 * @author luanpozzobon
 */
public class Getlib {
    private static InputReader sc = new InputReader();
    private static ZipCodeService zip = new ZipCodeService();
            
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
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    // TODO - Termos de Uso
                    break;
                default:
                    System.out.println("Opção Inválida! Tente novamente!");
            }
        }
    }
    
    private static void login(){
        // TODO
    }
    
    private static void register(){
        while(true){
            System.out.println("1-Cliente");
            System.out.println("2-Funcionário");
            System.out.println("0-Voltar");
            switch(sc.getNextInt()){
                case 1:
                    System.out.print("Nome Completo: ");
                    String fullname = sc.getNextLine();
                    System.out.print("Data de Nascimento (aaaa-mm-dd): ");
                    LocalDate birthdate = DataFormatter.formatDate(sc.getNextLine());
                    System.out.print("CEP: ");
                    String zipCode = sc.getNextLine();
                    Address add;
                    if((add = zip.getAddressByZipCode(zipCode)) == null){
                        System.out.println("Não foi possível encontar o CEP fornecido! Preencha as informações manualmente!");
                        System.out.print("País: ");
                        String nation = sc.getNextLine();
                        System.out.print("Estado: ");
                        String state = sc.getNextLine();
                        System.out.print("Cidade: ");
                        String city = sc.getNextLine();
                        System.out.print("Rua: ");
                        String street = sc.getNextLine();
                        System.out.print("Número: ");
                        String number = sc.getNextLine();
                        add = new Address(nation, state, city, street, number, zipCode);
                    } else {
                        System.out.print("Rua: ");
                        add.setStreet(sc.getNextLine());
                        System.out.print("Número: ");
                        add.setNumber(sc.getNextLine());   
                    }
                    System.out.print("E-Mail: ");
                    String email = sc.getNextLine();
                    System.out.print("Telefone: ");
                    String phone = sc.getNextLine();
                    System.out.println("Quase lá!");
                    System.out.print("Nome de usuário: ");
                    String username;
                    while(!(DataValidator.isUsernameValid(username = sc.getNextLine()))){
                        System.out.println("Tente Novamente!");
                        System.out.print("Nome de usuário: ");
                    }

                    System.out.print("Senha: ");
                    String password = sc.getNextLine();
                    System.out.print("Confirme a senha:");
                    while(!DataValidator.isPasswordValid(password, sc.getNextLine())){
                        System.out.println("Tente Novamente!");
                        System.out.print("Senha: ");
                        password = sc.getNextLine();
                        System.out.print("Confirme a senha: ");
                    }
                    
                    String salt = PasswordUtils.generateSalt();
                    password = PasswordUtils.encryptPassword(password, salt);

                    if(!CustomerDAO.saveCustomer(new Customer(fullname, birthdate, add, email, phone, 50.0, username, salt, password))){
                        System.out.println("Ocorreu um erro ao salvar o usuário! Tente novamente mais tarde!");
                    }
                    break;
                case 2:
                    // TODO - Funcionários
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        }
    }
    
    private static void readTerms(){
        // TODO
    }
}
