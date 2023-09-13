package com.luan.getlib.interfaces;

import com.luan.getlib.repository.BookRepository;
import com.luan.getlib.repository.CustomerRepository;
import com.luan.getlib.repository.EmployeeRepository;
import com.luan.getlib.repository.OperationRepository;
import com.luan.getlib.models.Address;
import com.luan.getlib.models.Book;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Employee;
import com.luan.getlib.models.Operation;
import com.luan.getlib.security.PasswordUtils;
import com.luan.getlib.service.CurrencyService;
import com.luan.getlib.service.RestCountriesService;
import com.luan.getlib.service.ZipCodeService;
import com.luan.getlib.utils.DataValidator;
import com.luan.getlib.utils.InputReader;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @since v0.2.0
 * @author luanpozzobon
 */
public class MenuScreen {
    private static final ZipCodeService zip = new ZipCodeService();
    private static final RestCountriesService curr = new RestCountriesService();
    private static final CurrencyService currConvert = new CurrencyService();
    private static String zipCode, nation, state, city, street, number, currency, email, phone, username;
    private static char[] salt, password;
    private static String title, genre;
    private static int amount, parentalRating;
    private static double value, rentValue;;
    private static List<Book> books;
    private static Book book;
    private static char exit;
    private static Address address;
    private static Operation operation;
    private static List<Operation> operations;
    private static Map<String, Operation> myBooks;
    
    
    public static void employee(Employee emp, InputReader sc){
        System.out.println("Bem-Vindo " + emp.getFullName());
        while(true){
            exit = 'n';
            System.out.println("Selecione uma opção:");
            System.out.println("1-Editar cadastro");
            System.out.println("2-Listar livros");
            System.out.println("3-Buscar livros");
            System.out.println("0-Sair");
            
            switch(sc.getNextInt()){
                case 0:
                    return;
                case 1:
                    System.out.println("1-Alterar senha");
                    System.out.println("2-Excluir conta");
                    System.out.println("0-Sair");
                    switch(sc.getNextInt()){
                        case 0:
                            break;
                        case 1:
                            System.out.print("Nova senha: ");
                            password = sc.getPassword();
                            System.out.print("Confirme a senha: ");
                            while (!DataValidator.isPasswordValid(password, sc.getPassword())){
                                System.out.println("Tente novamente!");
                                System.out.print("Nova senha: ");
                                password = sc.getPassword();
                                System.out.print("Confirme a senha: ");
                            }

                            emp.setSalt(salt = PasswordUtils.generateSalt());
                            emp.setPassword(password = PasswordUtils.encryptPassword(password, salt));

                            if(EmployeeRepository.updateEmployee(emp)){
                                System.out.println("Senha alterada com sucesso!");
                            } else {
                                System.out.println("Ocorreu um erro ao salvar a nova senha. Tente novamente mais tarde!");
                            }
                            
                            Arrays.fill(salt, ' ');
                            Arrays.fill(password, ' ');
                            break;
                        case 2:
                            System.out.print("Deseja realmente excluir sua conta? (y/n) ");
                            switch(sc.getNext()){
                                case 'y':
                                    System.out.print("Digite sua senha para confirmar a operação: ");
                                    if(DataValidator.arePasswordsEqual(new String(PasswordUtils.encryptPassword(sc.getPassword(), emp.getSalt())), new String(emp.getPassword()))){
                                        if(EmployeeRepository.deleteEmployee(emp)){
                                            emp = new Employee();
                                            System.out.println("Cadastro excluído com sucesso!");
                                            return;
                                        }
                                    } else {
                                        System.out.println("Senha incorreta");
                                    }
                                    break;
                                case 'n':
                                    break;
                            }                                   
                    }
                    break;

                case 2:
                    listBooks();
                    System.out.println("Pressione [ENTER] para voltar!");
                    sc.getNextLine();
                    break;
                case 3:
                    System.out.print("Digite o nome do livro: ");
                    title = sc.getNextLine();
                    if(!listBooks(title)) {
                        System.out.println("A busca não encontrou nenhuma correspondência!");
                        System.out.print("Deseja adicionar um novo livro? (y/n) ");
                        switch(sc.getNext()){
                            case 'y':
                                System.out.print("Título: ");
                                title = sc.getNextLine();
                                System.out.print("Gênero: ");
                                genre = sc.getNextLine();
                                System.out.print("Classificação Indicativa: ");
                                parentalRating = sc.getNextInt();
                                System.out.print("Quantidade: ");
                                amount = sc.getNextInt();
                                System.out.print("Valor: $");
                                value = sc.getNextDouble();
                                
                                book = new Book(title, genre, amount, value, parentalRating);
                                if(BookRepository.saveBook(book)){
                                    System.out.println("Livro salvo com sucesso!");
                                } else {
                                    System.out.println("Falha no salvamento do livro! Tente novamente mais tarde!");
                                }
                        }
                        break;
                    }
                    System.out.print("Digite o Id do livro desejado: ");
                    book = BookRepository.findById(sc.getNextInt());

                    do{
                        System.out.println("\nId: " + book.getId());
                        System.out.println("Título: " + book.getTitle());
                        System.out.println("Gênero: " + book.getGenre());
                        System.out.println("Classificação Indicativa: " + book.getParentalRating());
                        System.out.println("Quantidade: " + book.getAmount());
                        System.out.println("Valor: $" + book.getValue() + "\n");

                        System.out.println("1-Inserir");
                        System.out.println("2-Remover");
                        System.out.println("3-Editar preço");
                        System.out.println("0-Voltar");
                        switch(sc.getNextInt()){
                            case 0:
                                exit = 'y';
                                break;
                            case 1:
                                System.out.println("Quantidade anterior: " + book.getAmount());
                                System.out.print("Quantidade a inserir: ");
                                amount = sc.getNextInt();
                                System.out.println("Nova quantidade: " + (book.getAmount() + amount));
                                System.out.print("Confirma a operação? (y/n) ");
                                switch(sc.getNext()){
                                    case 'y':
                                        book.setAmount(book.getAmount() + amount);
                                        updateBook();
                                        break;
                                    case 'n':
                                        continue;
                                    default:
                                        System.out.println("Opção Inválida!");
                                }
                                break;
                            case 2:
                                System.out.println("Quantidade anterior: " + book.getAmount());
                                System.out.print("Quantidade a remover: ");
                                amount = sc.getNextInt();
                                System.out.println("Livros restantes: " + (book.getAmount() - amount));
                                System.out.print("Confirma a operação? (y/n) ");
                                switch(sc.getNext()){
                                    case 'y':
                                        book.setAmount(book.getAmount() - amount);
                                        updateBook();
                                        break;
                                    case 'n':
                                        continue;
                                    default:
                                        System.out.println("Opção Inválida!");
                                }
                                break;
                            case 3:
                                System.out.println("Valor anterior: $" + book.getValue());
                                System.out.print("Novo Valor: $");
                                value = sc.getNextDouble();
                                System.out.print("Confirma a operação? (y/n) ");
                                switch(sc.getNext()){
                                    case 'y':
                                        book.setValue(value);
                                        updateBook();
                                        break;
                                    case 'n':
                                        continue;
                                    default:
                                        System.out.println("Opção Inválida!");
                                }
                        }
                    } while(exit != 'y');
            }            
        }
    }
    
    public static void customer(Customer cst, InputReader sc){
        System.out.println("Bem-Vindo " + cst.getFullName());
        while (true) {
            exit = 'n';
            System.out.println("Selecione uma opção: ");
            System.out.println("1-Editar cadastro");
            System.out.println("2-Listar livros");
            System.out.println("3-Buscar livros");
            System.out.println("4-Meus livros");
            System.out.println("5-Recarregar créditos");
            System.out.println("0-Sair");
            
            switch(sc.getNextInt()){
                case 0:
                    return;
                case 1:
                    System.out.println("Selecione o que deseja alterar: ");
                    System.out.println("1-Endereço");
                    System.out.println("2-E-Mail");
                    System.out.println("3-Telefone");
                    System.out.println("4-Nome de usuário");
                    System.out.println("5-Senha");
                    System.out.println("6-Excluir conta");
                    System.out.println("0-Voltar");
                    switch(sc.getNextInt()){
                        case 0:
                            break;
                        case 1:
                            System.out.print("CEP: ");
                            zipCode = sc.getNextLine();
                            if((address = zip.getAddressByZipCode(zipCode)) == null){
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
                                address = new Address(nation, state, city, street, number, zipCode);
                                currency = curr.getCurrencyByCountry(nation.replace(" ", "%20"), "name");
                            } else {
                                System.out.print("Rua: ");
                                address.setStreet(sc.getNextLine());
                                System.out.print("Número: ");
                                address.setNumber(sc.getNextLine());
                                currency = curr.getCurrencyByCountry(address.getNation(), "alpha");
                            }
                            
                            cst.setAddress(address);
                            cst.setCurrency(currency);
                            updateCustomer(cst);
                            break;
                        case 2:
                            System.out.print("Novo E-Mail: ");
                            email = sc.getNextLine();
                            cst.setEmail(email);
                            updateCustomer(cst);
                            break;
                        case 3:
                            System.out.print("Novo telefone: ");
                            phone = sc.getNextLine();
                            cst.setPhone(phone);
                            updateCustomer(cst);
                            break;
                        case 4:
                            System.out.print("Novo nome de usuário: ");
                            while(!DataValidator.isUsernameValid(username = sc.getNextLine())){
                                System.out.print("Novo nome de usuário: ");
                            }
                            cst.setUsername(username);
                            updateCustomer(cst);
                            break;
                        case 5:
                            System.out.print("Nova senha: ");
                            password = sc.getPassword();
                            System.out.print("Confirme a senha: ");
                            while(!DataValidator.isPasswordValid(password, sc.getPassword())){
                                System.out.println("Tente novamente!");
                                System.out.print("Nova senha: ");
                                password = sc.getPassword();
                                System.out.print("Confirme a senha: ");
                            }
                            
                            salt = PasswordUtils.generateSalt();
                            cst.setPassword(password = PasswordUtils.encryptPassword(password, salt));
                            updateCustomer(cst);
                            
                            Arrays.fill(salt, ' ');
                            Arrays.fill(password, ' ');
                            break;
                        case 6:
                            System.out.print("Deseja realmente excluir sua conta? (y/n) ");
                            switch(sc.getNext()){
                                case 'y':
                                    if(cst.getCredits() < 0){
                                        System.out.println("Não é possível encerrar a conta! Você ainda possui ainda possui débitos!");
                                        break;
                                    }
                                    if((operations = OperationRepository.findRentByCustomer(cst)) != null){
                                        if(operations.isEmpty()){
                                            System.out.print("Digite sua senha para confirmar a operação: ");
                                            if(DataValidator.arePasswordsEqual(new String(PasswordUtils.encryptPassword(sc.getPassword(), cst.getSalt())), new String(cst.getPassword()))){
                                                if(CustomerRepository.deleteCustomer(cst)){
                                                    cst = new Customer();
                                                    System.out.println("Cadastro excluído com sucesso!");
                                                    return;
                                                }
                                            } else {
                                                System.out.println("Senha incorreta");
                                            }
                                        } else {
                                            System.out.println("Não é possível encerrar a conta! Você ainda possui livros alugados!");
                                        }
                                    }
                                case 'n':
                                    break;
                                    
                            }
                            break;
                    }
                    break;
                case 2:
                    listBooks();
                    System.out.println("Pressione [ENTER] para voltar!");
                    sc.getNextLine();
                    break;
                case 3:
                    System.out.print("Digite o nome do livro: ");
                    title = sc.getNextLine();
                    if(!listBooks(title)){
                        System.out.println("A busca não encontrou nenhuma correspondência!");
                        continue;
                    } else {
                        System.out.print("Digite o Id do livro desejado: ");
                        if((book = BookRepository.findById(sc.getNextInt())) != null){
                            exit = 'n';
                            do{
                                operation = OperationRepository.findByBookAndCustomer(book, cst);
                                bookMenu(book, operation, cst, sc);
                            } while (exit != 'y');
                        }
                    }
                    break;
                case 4:
                    listBooks(cst);
                    System.out.print("Digite o id desejado ou digite [0] para sair: ");
                    int opt = sc.getNextInt();
                    switch(opt){
                        case 0:
                            break;
                        default:
                            book = BookRepository.findById(Integer.parseInt(String.valueOf(opt)));
                            if(myBooks.containsKey(book.getTitle())){
                                operation = myBooks.get(book.getTitle());
                                bookMenu(book, operation, cst, sc);
                            }
                            break;
                    }
                    break;
                case 5:
                    System.out.print("Quanto deseja recarregar: " + cst.getCurrency());
                    value = sc.getNextDouble();
                    
                    cst.setCredits(cst.getCredits() + value);
                    if(updateCustomer(cst)){
                        System.out.println("Recarga realizada com sucesso!");
                        System.out.printf("Novo saldo: %s %.2f", cst.getCurrency(), cst.getCredits());
                    }
                    break;
            }
        }        
    }
    
    private static void bookMenu(Book book, Operation operation, Customer cst, InputReader sc){
        value = currConvert.convertFromUSD(book.getValue(), cst.getCurrency());
        System.out.println("\nId: " + book.getId());
        System.out.println("Título: " + book.getTitle());
        System.out.println("Gênero: " + book.getGenre());
        System.out.println("Classificação Indicativa: " + book.getParentalRating());
        System.out.println("Quantidade: " + book.getAmount());
        System.out.printf("Valor: %s %.2f\n\n", cst.getCurrency(), value);

        if(!DataValidator.isOldEnough(cst.getBirthDate(), book.getParentalRating())){
            System.out.println("Você não possui idade suficiente para ler este livro!");
            System.out.println("Pressione [ENTER] para voltar!");
            sc.getNextLine();
            exit = 'y';
            return;
        }
        if(operation != null){
            switch(operation.getType()){
                case 'r':
                    rentValue = operation.calculateRentValue();
                    System.out.printf("1-Livro alugado em %s por %s %.2f / dia! Valor devido = %s %.2f! Devolver\n",
                                  operation.getOperationDate().toString(), cst.getCurrency(), operation.getValue(), cst.getCurrency(), rentValue);
                    System.out.println("2-Comprar");
                    System.out.println("0-Voltar");
                    switch(sc.getNextInt()){
                        case 0:
                            exit = 'y';
                            break;
                        case 1:
                            System.out.printf("Valor a pagar: %s %.2f\n", cst.getCurrency(), rentValue);
                            System.out.print("Confirma a devolução? (y/n) ");
                            switch(sc.getNext()){
                                case 'y':
                                    if(OperationRepository.deleteOperation(operation.getId())){
                                        System.out.println("Livro devolvido!");
                                        cst.setCredits(cst.getCredits() - rentValue);
                                        updateCustomer(cst);
                                        book.setAmount(book.getAmount() + 1);
                                        BookRepository.updateBook(book);
                                    }
                                    break;
                                case 'n':
                                    break;
                            }
                            break;
                        case 2:
                            System.out.printf("Valor do livro: %s %.2f\n", cst.getCurrency(), value);
                            System.out.print("Confirma a compra? (y/n) ");
                            switch(sc.getNext()){
                                case 'y':
                                    operation.setType('p');
                                    operation.setOperationDate(LocalDate.now());
                                    operation.setValue(value);
                                    if(OperationRepository.updateOperation(operation)){
                                        cst.setCredits(cst.getCredits() - value);
                                        updateCustomer(cst);
                                        System.out.println("Livro adquirido com sucesso!");
                                    }
                                    break;
                                case 'n':
                                    break;                                                    
                            }
                            break;
                    }
                    break;
                case 'p':
                    System.out.println("Você já adquiriu este livro!");
                    System.out.println("1-Devolver");
                    System.out.println("2-Trocar");
                    System.out.println("0-Voltar");
                    switch(sc.getNextInt()){
                        case 0:
                            exit = 'y';
                            break;
                        case 1:
                            if(Period.between(operation.getOperationDate(), LocalDate.now()).getDays() > 7){
                                System.out.println("Não é possível realizar a devolução do livro!");
                                System.out.println("Caso deseje, pode fazer a troca do livro, por outro de sua escolha!");
                                break;
                            }
                            System.out.print("Confirma a devolução? (y/n) ");
                            switch(sc.getNext()){
                                case 'y':
                                    value = operation.getValue();
                                    
                                    if(OperationRepository.deleteOperation(operation.getId())){
                                        book.setAmount(book.getAmount() + 1);
                                        cst.setCredits(cst.getCredits() + value);
                                        BookRepository.updateBook(book);
                                        updateCustomer(cst);
                                        System.out.println("Livro devolvido!");
                                    }
                                    break;
                                case 'n':
                                    break;
                            }
                            break;
                        case 2:
                            double exValue = operation.calculateExchangeValue();
                            
                            System.out.print("Digite o livro desejado: ");
                            listBooks(sc.getNextLine());
                            System.out.print("Digite o id do livro desejado: ");
                            Book book2 = BookRepository.findById(sc.getNextInt());
                            value = currConvert.convertFromUSD(book2.getValue(), cst.getCurrency());
                            
                            System.out.println("\nId: " + book2.getId());
                            System.out.println("Título: " + book2.getTitle());
                            System.out.println("Gênero: " + book2.getGenre());
                            System.out.println("Classificação Indicativa: " + book2.getParentalRating());
                            System.out.println("Quantidade: " + book2.getAmount());
                            System.out.printf("Valor: %s %.2f\n\n", cst.getCurrency(), value);
                            
                            System.out.printf("Valor p/ troca: %s %.2f\n", cst.getCurrency(), value - exValue);
                            System.out.print("Confirma a troca? (y/n) ");
                            switch(sc.getNext()){
                                case 'y':
                                    operation.setBook(book2);
                                    operation.setOperationDate(LocalDate.now());
                                    operation.setValue(value);
                                    
                                    if(OperationRepository.updateOperation(operation)){
                                        cst.setCredits(cst.getCredits() - (value - exValue));
                                        updateCustomer(cst);
                                        book.setAmount(book.getAmount() + 1);
                                        BookRepository.updateBook(book);
                                        book2.setAmount(book2.getAmount() - 1);
                                        BookRepository.updateBook(book2);
                                        System.out.println("Livro trocado com sucesso!");
                                    }
                                    break;
                            }
                            break;
                        default:
                            System.out.println("Opção Inválida!");
                            break;
                    }
                    break;
            }
            
        } else {
            System.out.println("1-Alugar");
            System.out.println("2-Comprar");
            System.out.println("0-Voltar");
            switch(sc.getNextInt()){
                case 0:
                    exit = 'y';
                    break;
                case 1:
                    rentValue = value/100;
                    System.out.printf("O valor do aluguel deste livro é de %s %.2f / dia\n", cst.getCurrency(), rentValue);
                    System.out.print("Confirma o aluguel? (y/n) ");
                    switch(sc.getNext()){
                        case 'y':
                            if(OperationRepository.saveOperation(new Operation('r', book, cst, LocalDate.now(), rentValue))){
                                System.out.println("Livro alugado com sucesso!");
                                book.setAmount(book.getAmount() - 1);
                                BookRepository.updateBook(book);
                            }
                            break;
                        case 'n':
                            break;
                    }
                    break;
                case 2:
                    System.out.printf("Valor do livro: %s %.2f\n", cst.getCurrency(), value);
                    System.out.print("Confirma a compra? (y/n) ");
                    switch(sc.getNext()){
                        case 'y':
                            operation = new Operation();
                            operation.setType('p');
                            operation.setOperationDate(LocalDate.now());
                            operation.setValue(value);
                            if(OperationRepository.updateOperation(operation)){
                                cst.setCredits(cst.getCredits() - value);
                                updateCustomer(cst);
                                book.setAmount(book.getAmount() - 1);
                                BookRepository.updateBook(book);
                                System.out.println("Livro adquirido com sucesso!");
                            }
                            break;
                        case 'n':
                            break;                                                    
                    }
                    break;
            }
        }
    }
        
    
    private static void listBooks(){
        if((books = BookRepository.getAllBooks()) != null){
            for(Book item : books){
                System.out.println("\nId: " + item.getId());
                System.out.println("Título: " + item.getTitle());
            }
        }
    }
    
    private static boolean listBooks(String title){
        if((books = BookRepository.findByTitle(title)) != null){
            for(Book item : books){
                System.out.println("\nId: " + item.getId());
                System.out.println("Título: " + item.getTitle());
            }
            
            return true;
        } else {
            return false;
        }
    }
    
    private static void listBooks(Customer cst){
        if((myBooks = OperationRepository.findByCustomerId(cst)) != null){
            for(String key : myBooks.keySet()){
                System.out.println("\nId: " + myBooks.get(key).getBook().getId());
                System.out.println("Título: " + key);
                System.out.println("Tipo: " + myBooks.get(key).getTypeAsString());
            }
        }
    }
    
    private static void updateBook(){
        if(BookRepository.updateBook(book)){
            System.out.println("Operação concluída com sucesso!");
        } else {
            System.out.println("Ocorreu um erro ao alterar o livro: " + book.getTitle() + "! Tente novamente mais tarde!");
        }
    }
    
    private static boolean updateCustomer(Customer cst){
        if(CustomerRepository.updateCustomer(cst)){
            System.out.println("Operação concluída com sucesso!");
            return true;
        } else {
            System.out.println("Occoreu um erro ao alterar o cadastro! Tente novamente mais tarde!");
            return false;
        }
    }
}