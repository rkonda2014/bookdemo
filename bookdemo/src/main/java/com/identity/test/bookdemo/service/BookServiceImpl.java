package com.identity.test.bookdemo.service;

import com.identity.test.bookdemo.exception.BookNotFoundException;
import com.identity.test.bookdemo.model.Book;
import com.identity.test.bookdemo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void removeBook(String isbn) {
        if (!bookRepository.existsById(isbn)) {
            throw new BookNotFoundException("Book Not Found with ISBN: " + isbn);
        }
        bookRepository.deleteById(isbn);
    }

    @Override
    public Book findBookByISBN(String isbn) {
        return bookRepository.findById(isbn).orElseThrow(() ->
                new BookNotFoundException("Book Not Found with ISBN: " + isbn));
    }

    @Override
    public List<Book> findBooksByAuthor(String isbn) {
       return bookRepository.findByAuthor(isbn);
    }

    @Override
    public Book borrowBook(String isbn) {
        Book book = findBookByISBN(isbn);
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No copies available for ISBN " + isbn);
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        return bookRepository.save(book);
    }

    @Override
    public Book returnBook(String isbn) {
        Book book = findBookByISBN(isbn);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        return bookRepository.save(book);
    }
}
