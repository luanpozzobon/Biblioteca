package com.luan.getlib.repositories;

import com.luan.getlib.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(String title);
    boolean existsByTitle(String title);
}
