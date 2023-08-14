package com.luan.getlib;

import com.luan.getlib.models.Address;
import com.luan.getlib.models.Customer;
import com.luan.getlib.service.ZipCodeService;
import com.luan.getlib.utils.InputReader;

/**
 *
 * @author luanp
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
                    // TODO - Cadastrar
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
            System.out.println("3-Voltar");
            switch(sc.getNextInt()){
                case 1:
                    System.out.print("Nome Completo: ");
                    String fullname = sc.getNextLine();
                    System.out.print("Data de Nascimento (aaaa-mm-dd): ");
                    String birthdate = sc.getNextLine();
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
                    String username = sc.getNextLine();
                    // TODO - Validar unicidade do username

                    System.out.print("Senha: ");
                    String password = sc.getNextLine();
                    System.out.print("Confirme a senha:");
                    while(!(password.equals(sc.getNextLine()))){
                        System.out.println("Senhas não conferem! Tente novamente!");
                        System.out.print("Senha: ");
                        password = sc.getNextLine();
                        System.out.print("Confirme a senha: ");
                    }
                    // TODO - Encriptar senha

                    Customer customer = new Customer(fullname, birthdate, add, email, phone, 50.0, username, password);
                    // TODO - Cadastrar no banco de dados
                    break;
                case 2:
                    // TODO - Funcionários
                    break;
                case 3:
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
