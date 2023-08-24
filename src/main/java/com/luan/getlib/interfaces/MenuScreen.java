package com.luan.getlib.interfaces;

import com.luan.getlib.dao.BookDao;
import com.luan.getlib.dao.CustomerDAO;
import com.luan.getlib.dao.EmployeeDAO;
import com.luan.getlib.dao.OperationDAO;
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
import java.util.List;

/**
 * @since v0.2.0
 * @author luanpozzobon
 */
public class MenuScreen {
    private static final InputReader sc = new InputReader();
    private static final ZipCodeService zip = new ZipCodeService();
    private static RestCountriesService curr = new RestCountriesService();
    private static CurrencyService currConvert = new CurrencyService();
    private static String zipCode, nation, state, city, street, number, currency, email, phone, username, password, salt;
    private static String title, genre;
    private static int amount, parentalRating;
    private static double value;
    private static List<Book> books;
    private static Book book;
    private static char exit;
    private static Address address;
    private static Operation operation;
    
    
    public static void employee(Employee emp){
        
        System.out.println("Bem-Vindo " + emp.getFullName());
        while(true){
            exit = 'n';
            System.out.println("Selecione uma opção:");
            System.out.println("1-Alterar senha");
            System.out.println("2-Listar livros");
            System.out.println("3-Buscar livros");
            System.out.println("0-Sair");
            
            switch(sc.getNextInt()){
                case 0:
                    return;
                case 1:
                    System.out.print("Nova senha: ");
                    password = sc.getNextLine();
                    System.out.print("Confirme a senha: ");
                    while (DataValidator.isPasswordValid(password, sc.getNextLine())){
                        System.out.println("Tente novamente!");
                        System.out.print("Nova senha: ");
                        password = sc.getNextLine();
                        System.out.print("Confirme a senha: ");
                    }
                    
                    emp.setSalt(salt = PasswordUtils.generateSalt());
                    emp.setPassword(password = PasswordUtils.encryptPassword(password, salt));
                    
                    if(EmployeeDAO.saveEmployee(emp)){
                        System.out.println("Senha alterada com sucesso!");
                    } else {
                        System.out.println("Ocorreu um erro ao salvar a nova senha. Tente novamente mais tarde!");
                    }
                    break;
                case 2:
                    listBooks();
                    System.out.println("Pressione [Qualquer tecla] para voltar!");
                    sc.getNextLine();
                    break;
                case 3:
                    // TODO
                    System.out.print("Digite o nome do livro: ");
                    title = sc.getNextLine();
                    if(!listBooks(title)) {
                        System.out.println("A busca não encontrou nenhuma correspondência!");
                        System.out.print("Deseja adicionar um novo livro (y/n)? ");
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
                                if(BookDao.saveBook(book)){
                                    System.out.println("Livro salvo com sucesso!");
                                } else {
                                    System.out.println("Falha no salvamento do livro! Tente novamente mais tarde!");
                                }
                        }
                        break;
                    }
                    System.out.print("Digite o Id do livro desejado: ");
                    book = BookDao.findById(sc.getNextInt());

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
                                System.out.print("Confirma a operação? (y/n)");
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
                                System.out.print("Confirma a operação? (y/n)");
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
                                System.out.println("Confirma a operação? (y/n)");
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
    
    public static void customer(Customer cst){
        System.out.println("Bem-Vindo " + cst.getFullName());
        while (true) {
            exit = 'n';
            System.out.println("Selecione uma opção: ");
            System.out.println("1-Editar cadastro");
            System.out.println("2-Listar livros");
            System.out.println("3-Buscar livros");
            System.out.println("0-Sair");
            
            switch(sc.getNextInt()){
                case 0:
                    return;
                case 1:
                    // TODO
                    System.out.println("Selecione o que deseja alterar: ");
                    System.out.println("1-Endereço");
                    System.out.println("2-E-Mail");
                    System.out.println("3-Telefone");
                    System.out.println("4-Nome de usuário");
                    System.out.println("5-Senha");
                    System.out.println("0-Voltar");
                    switch(sc.getNextInt()){
                        case 0:
                            break;
                        case 1:
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
                            break;
                        case 2:
                            System.out.print("Novo E-Mail: ");
                            email = sc.getNextLine();
                            cst.setEmail(email);
                            break;
                        case 3:
                            System.out.print("Novo telefone: ");
                            phone = sc.getNextLine();
                            cst.setPhone(phone);
                            break;
                        case 4:
                            System.out.print("Novo nome de usuário: ");
                            while(!DataValidator.isUsernameValid(username = sc.getNextLine())){
                                System.out.print("Novo nome de usuário: ");
                            }
                            cst.setUsername(username);
                            break;
                        case 5:
                            System.out.print("Nova senha: ");
                            password = sc.getNextLine();
                            System.out.print("Confirme a senha: ");
                            while(!DataValidator.isPasswordValid(password, sc.getNextLine())){
                                System.out.println("Tente novamente!");
                                System.out.print("Nova senha: ");
                                password = sc.getNextLine();
                                System.out.print("Confirme a senha: ");
                            }
                            
                            salt = PasswordUtils.generateSalt();
                            cst.setPassword(password = PasswordUtils.encryptPassword(password, salt));
                            break;
                    }
                    updateCustomer(cst);
                    break;
                case 2:
                    listBooks();
                    System.out.println("Pressione [Qualquer tecla] para voltar!");
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
                        if((book = BookDao.findById(sc.getNextInt())) != null){
                            value = currConvert.convertFromUSD(book.getValue(), cst.getCurrency());
                            double rentValue;
                            do{
                                System.out.println("\nId: " + book.getId());
                                System.out.println("Título: " + book.getTitle());
                                System.out.println("Gênero: " + book.getGenre());
                                System.out.println("Classificação Indicativa: " + book.getParentalRating());
                                System.out.println("Quantidade: " + book.getAmount());
                                System.out.printf("Valor: %s %.2f\n\n", cst.getCurrency(), value);
                                operation = OperationDAO.findRentByBookAndCustomer(book.getId(), cst.getId());
                                
                                if(operation != null){
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
                                                    if(OperationDAO.deleteOperation(operation.getId())){
                                                        System.out.println("Livro devolvido!");
                                                        book.setAmount(book.getAmount() + 1);
                                                        BookDao.updateBook(book);
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
                                                    if(OperationDAO.updateOperation(operation)){
                                                        System.out.println("Livro adquirido com sucesso!");
                                                    }
                                                    break;
                                                case 'n':
                                                    break;                                                    
                                            }
                                            break;
                                    }
                                } else {
                                    if(Period.between(cst.getBirthDate(), LocalDate.now()).getYears() < book.getParentalRating()){
                                        System.out.println("Você não possui idade suficiente para ler este livro!");
                                        System.out.println("Pressione [Qualquer tecla] para voltar!");
                                        sc.getNextLine();
                                        break;
                                    } else {
                                        System.out.println("1-Aluguel");
                                        System.out.println("2-Comprar");
                                        System.out.println("0-Voltar");

                                        switch(sc.getNextInt()){
                                            case 0:
                                                exit = 'y';
                                                break;
                                            case 1:
                                                rentValue = value/100;
                                                System.out.printf("O valor do aluguel deste livro é de %s %.2f / dia\n", cst.getCurrency(), rentValue);
                                                System.out.print("Deseja confirmar o aluguel? (y/n) ");
                                                switch(sc.getNext()){
                                                    case 'y':
                                                        if(OperationDAO.saveOperation(new Operation('r', book.getId(), cst.getId(), LocalDate.now(), rentValue))){
                                                            System.out.println("Livro alugado com sucesso!");
                                                            book.setAmount(book.getAmount() - 1);
                                                            BookDao.updateBook(book);
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
                                                        if(OperationDAO.saveOperation(new Operation('p', book.getId(), cst.getId(), LocalDate.now(), value))){
                                                            System.out.println("Livro adquirido com sucesso!");
                                                            book.setAmount(book.getAmount() - 1);
                                                            BookDao.updateBook(book);
                                                        }
                                                        break;
                                                    case 'n':
                                                        break;
                                                }
                                                break;
                                        }
                                    }
                                }                                    
                            } while (exit != 'y');
                        }
                    }
                    break;
            }
        }        
    }
    
    private static void listBooks(){
        if((books = BookDao.getAllBooks()) != null){
            for(Book item : books){
                System.out.println("\nId: " + item.getId());
                System.out.println("Título: " + item.getTitle());
            }
        }
    }
    
    private static boolean listBooks(String title){
        if((books = BookDao.findByTitle(title)) != null){
            for(Book item : books){
                System.out.println("\nId: " + item.getId());
                System.out.println("Título: " + item.getTitle());
            }
            
            return true;
        } else {
            return false;
        }
    }
    
    private static void updateBook(){
        if(BookDao.updateBook(book)){
            System.out.println("Operação concluída com sucesso!");
        } else {
            System.out.println("Ocorreu um erro ao alterar o livro: " + book.getTitle() + "! Tente novamente mais tarde!");
        }
    }
    
    private static void updateCustomer(Customer cst){
        if(CustomerDAO.updateCustomer(cst)){
            System.out.println("Operação concluída com sucesso!");
        } else {
            System.out.println("Occoreu um erro ao alterar o cadastro! Tente novamente mais tarde!");
        }
    }
    
    private static void purchaseBook(){
        
    }
}
