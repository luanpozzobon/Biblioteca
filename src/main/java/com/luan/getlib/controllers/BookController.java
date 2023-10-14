package com.luan.getlib.controllers;

import com.luan.getlib.models.Book;
import com.luan.getlib.models.Operation;
import com.luan.getlib.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping("/")
    public ResponseEntity<List<Book>> getAllBooks() {
        return service.getAllBooks();
    }

    @GetMapping("/find")
    public ResponseEntity<List<Book>> findByTitle(@RequestParam String title) {
        return service.findByTitle(title);
    }

    @PostMapping("/new")
    public ResponseEntity newBook(@RequestBody Book book) {
        return service.newBook(book);
    }

    @PostMapping("/{bookId}/lend")
    public ResponseEntity<Operation> lend(@PathVariable long bookId) {
        return service.lend(bookId);
    }

    @DeleteMapping("/{bookId}/return")
    public ResponseEntity returnBook(@PathVariable long bookId) {
        return service.returnBook(bookId);
    }

    @GetMapping("/my-books")
    public ResponseEntity<List<Operation>> userBooks() {
        return service.userBooks();
    }


}
