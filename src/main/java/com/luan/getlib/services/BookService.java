package com.luan.getlib.services;

import com.luan.getlib.models.Book;
import com.luan.getlib.models.Operation;
import com.luan.getlib.models.User;
import com.luan.getlib.repositories.BookRepository;
import com.luan.getlib.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final OperationRepository operationRepository;
    private final UserService userService;

    @Autowired
    public BookService(BookRepository bookRepository, OperationRepository operationRepository,
                       UserService userService) {
        this.bookRepository = bookRepository;
        this.operationRepository = operationRepository;
        this.userService = userService;
    }

    public ResponseEntity<List<Book>> getAllBooks() {
        var books = bookRepository.findAll();
        if (books.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(books);
    }

    public ResponseEntity<List<Book>> findByTitle(String title) {
        var books = bookRepository.findByTitleContaining(title);
        if (books.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(books);
    }

    public ResponseEntity newBook(Book book) {
        if (bookRepository.existsByTitle(book.getTitle()))
            return new ResponseEntity<>(String.format("O livro %s já está cadastrado no sistema!", book.getTitle()),
                    HttpStatus.CONFLICT);
        bookRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    public ResponseEntity lend(long bookId) {
        var bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Book book = bookOptional.get();
        User user = userService.getAuthenticatedUser();
        if (operationRepository.existsByUserAndBook(user, book))
            return ResponseEntity.badRequest().body("Não é possível emprestar este livro pois o usuário já emprestou uma unidade deste livro!");

        var operation = new Operation(book, user, LocalDate.now());
        book.setAmount(book.getAmount() - 1);

        bookRepository.save(book);
        operationRepository.save(operation);

        return ResponseEntity.ok(operation);
    }

    public ResponseEntity returnBook(long bookId) {
        User user = userService.getAuthenticatedUser();
        var bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Book book = bookOptional.get();
        Operation operation = operationRepository.findByUserAndBook(user, book);
        if (operation == null)
            return ResponseEntity.notFound().build();

        book.setAmount(book.getAmount() + 1);
        bookRepository.save(book);
        operationRepository.delete(operation);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Operation>> userBooks() {
        User user = userService.getAuthenticatedUser();

        var operations = operationRepository.findAllByUser(user);
        if (operations.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(operations);
    }
}
