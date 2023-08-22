package com.luan.getlib.interfaces;

import com.luan.getlib.dao.BookDao;
import com.luan.getlib.dao.EmployeeDAO;
import com.luan.getlib.models.Book;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Employee;
import com.luan.getlib.security.PasswordUtils;
import com.luan.getlib.utils.DataValidator;
import com.luan.getlib.utils.InputReader;
import java.util.List;

/**
 * @since v0.2.0
 * @author luanpozzobon
 */
public class MenuScreen {
    private static final InputReader sc = new InputReader();
    private static String password, salt;
    private static String title, genre, parentalRating;
    private static int amount;
    private static double value;
    private static List<Book> books;
    private static Book book;
    private static char exit = 'n';
    public static void employee(Employee emp){
        // TODO
        System.out.println("Bem-Vindo " + emp.getFullName());
        while(true){
            System.out.println("Selecione uma opção:");
            System.out.println("1-Alterar senha");
            System.out.println("2-Listar livros");
            System.out.println("3-Buscar livros");
            System.out.println("0-Sair");
            
            switch(sc.getNextInt()){
                case 0:
                    return;
                case 1:
                    System.out.print("Defina uma senha: ");
                    password = sc.getNextLine();
                    System.out.print("Confirme a senha: ");
                    while (DataValidator.isPasswordValid(password, sc.getNextLine())){
                        System.out.println("Tente novamente!");
                        System.out.print("Defina uma senha: ");
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
                    System.out.println("Pressione [ENTER] para voltar!");
                    while(sc.getNextLine().equals("\n"));
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
                                parentalRating = sc.getNextLine();
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
                                System.out.print("Confirma a operação (y/n)? ");
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
                                System.out.print("Confirma a operação (y/n)? ");
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
                                System.out.println("Confirma a operação (y/n)?");
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
        // TODO
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
}
