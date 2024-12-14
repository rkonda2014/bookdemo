package com.identity.test.bookdemo.service;

import com.identity.test.bookdemo.model.Book;

import java.util.List;

public interface BookService {

    Book addBook(Book book);
    void removeBook(String isbn);
    Book findBookByISBN(String isbn);
    List<Book> findBooksByAuthor(String isbn);
    Book borrowBook(String isbn);
    Book returnBook(String isbn);
}
