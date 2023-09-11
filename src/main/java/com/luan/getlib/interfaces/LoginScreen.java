package com.luan.getlib.interfaces;

import com.luan.getlib.repository.EmployeeRepository;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Employee;
import com.luan.getlib.repository.CustomerRepository;
import com.luan.getlib.security.PasswordUtils;
import com.luan.getlib.utils.DataValidator;
import com.luan.getlib.utils.InputReader;
import java.util.Arrays;

/**
 * @since v0.1.2
 * @author luanpozzobon
 */
public class LoginScreen {    
    public static Employee employee(InputReader sc){
        while(true){
            System.out.print("Digite seu código de acesso: ");
            String accessCode = sc.getNextLine();
            Employee emp = EmployeeRepository.findByAccessCode(accessCode);
            if(emp == null){
                System.out.println("Não foi possível encontrar o código de acesso digitado!");
                System.out.println("Verifique se o código de acesso foi digitado corretamente e tente novamente");
                continue;
            }
            System.out.print("Digite sua senha: ");
            char[] password = PasswordUtils.encryptPassword(sc.getPassword(), emp.getSalt());
            if(!DataValidator.arePasswordsEqual(new String(emp.getPassword()), new String(password))){
                System.out.println("Senha Incorreta!");
                continue;
            }
            
            Arrays.fill(password, ' ');
            return emp;
        }
    }
    
    public static Customer customer(InputReader sc){
        while(true){
            System.out.print("Digite seu nome de usuário: ");
            String username = sc.getNextLine();
            Customer cst = CustomerRepository.findByUsername(username);
            if(cst == null){
                System.out.println("Não foi possível encontrar o nome de usuário digitado!");
                System.out.println("Verifique se o nome de usuário foi digitado corretamente e tente novamente");
                continue;
            }
            System.out.print("Digite sua senha: ");
            char[] password = PasswordUtils.encryptPassword(sc.getPassword(), cst.getSalt());
            if(!DataValidator.arePasswordsEqual(new String(cst.getPassword()), new String(password))){
                System.out.println("Senha Incorreta!");
                continue;
            }
            
            Arrays.fill(password, ' ');
            return cst;
        }
    }
}
