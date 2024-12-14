package com.identity.test.bookdemo.repository;

import com.identity.test.bookdemo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByAuthor(String isbn);
}
