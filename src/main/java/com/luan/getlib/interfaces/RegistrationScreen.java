package com.luan.getlib.interfaces;

import com.luan.getlib.dao.CustomerDAO;
import com.luan.getlib.dao.EmployeeDAO;
import com.luan.getlib.models.Address;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Employee;
import com.luan.getlib.security.PasswordUtils;
import com.luan.getlib.service.RestCountriesService;
import com.luan.getlib.service.ZipCodeService;
import com.luan.getlib.utils.DataFormatter;
import com.luan.getlib.utils.DataValidator;
import com.luan.getlib.utils.InputReader;
import java.time.LocalDate;

/**
 * @since v0.1.2
 * @author luanpozzobon
 */
public class RegistrationScreen {
    private static final InputReader sc = new InputReader();
    private static final ZipCodeService zip = new ZipCodeService();
    private static String fullname, zipCode, nation, state, city, street, number, email, phone, currency, salt, password;
    private static LocalDate birthDate;
    
    public static void customer(){
        RestCountriesService curr = new RestCountriesService();
        System.out.print("Nome Completo: ");
        fullname = sc.getNextLine();
        System.out.print("Data de Nascimento (aaaa-mm-dd): ");
        birthDate = DataFormatter.formatDate(sc.getNextLine());
        System.out.print("CEP: ");
        zipCode = sc.getNextLine();
        Address add;
        if((add = zip.getAddressByZipCode(zipCode)) == null){
            System.out.println("Não foi possível encontrar o CEP fornecido! Preencha as informações manualmente!");
            System.out.print("País: ");
            nation = sc.getNextLine();
            System.out.print("Estado: ");
            state = sc.getNextLine();
            System.out.print("Cidade: ");
            city = sc.getNextLine();
            System.out.print("Rua: ");
            street = sc.getNextLine();
            System.out.print("Número: ");
            number = sc.getNextLine();
            add = new Address(nation, state, city, street, number, zipCode);
            currency = curr.getCurrencyByCountry(nation.replace(" ", "%20"), "name");
        } else {
            System.out.print("Rua: ");
            add.setStreet(sc.getNextLine());
            System.out.print("Número: ");
            add.setNumber(sc.getNextLine());
            currency = curr.getCurrencyByCountry(add.getNation(), "alpha");
        }
        
        System.out.print("E-Mail: ");
        email = sc.getNextLine();
        System.out.print("Telefone: ");
        phone = sc.getNextLine();
        System.out.println("Quase lá!");
        System.out.print("Nome de usuário: ");
        String username;
        while(!(DataValidator.isUsernameValid(username = sc.getNextLine()))){
            System.out.println("Tente Novamente!");
            System.out.print("Nome de usuário: ");
        }

        System.out.print("Defina uma senha: ");
        password = sc.getNextLine();
        System.out.print("Confirme a senha: ");
        while(!DataValidator.isPasswordValid(password, sc.getNextLine())){
            System.out.println("Tente Novamente!");
            System.out.print("Defina uma senha: ");
            password = sc.getNextLine();
            System.out.print("Confirme a senha: ");
        }

        salt = PasswordUtils.generateSalt();
        password = PasswordUtils.encryptPassword(password, salt);

        if(!CustomerDAO.saveCustomer(new Customer(fullname, birthDate, add, email, phone, 50.0, currency, username, salt, password))){
            System.out.println("Ocorreu um erro ao salvar as informações! Tente novamente mais tarde!");
        } else {
            System.out.println("Cliente cadastrado com sucesso! Você pode acessar sua conta imediatamente!");
        }
    }
    public static void employee(){
        System.out.print("Nome Completo: ");
        fullname = sc.getNextLine();
        System.out.print("Data de nascimento (aaaa-mm-dd): ");
        if(!DataValidator.isOldEnough(DataFormatter.formatDate(sc.getNextLine()), 18)){
            System.out.println("Não é possível cadastrá-lo como funcionário! É necessário ter no mínimo 18 anos!");
            return;
        }
        System.out.print("Email: ");
        email = sc.getNextLine();
        System.out.print("Telefone: ");
        phone = sc.getNextLine();

        Employee emp = new Employee(fullname, email, phone);
        emp.setAccessCode(emp.generateAccessCode());

        System.out.printf("Quase lá! O seu código de acesso é: %s\n", emp.getAccessCode());
        System.out.print("Defina uma senha: ");
        password = sc.getNextLine();
        System.out.print("Confirme a senha: ");
        while(!DataValidator.isPasswordValid(password, sc.getNextLine())){
            System.out.println("Tente novamente!");
            System.out.print("Defina uma senha: ");
            password = sc.getNextLine();
            System.out.print("Confirme a senha: ");
        }

        emp.setSalt(salt = PasswordUtils.generateSalt());
        emp.setPassword(password = PasswordUtils.encryptPassword(password, salt));

        if(!EmployeeDAO.saveEmployee(emp)){
            System.out.println("Ocorreu um erro ao salvar as informações! Tente novamente mais tarde!");
        } else {
            System.out.println("Funcionário cadastrado com sucesso! Você pode acessar sua conta imediatamente!");
        }
    }
}
