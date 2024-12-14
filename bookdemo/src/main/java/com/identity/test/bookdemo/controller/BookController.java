package com.identity.test.bookdemo.controller;

import com.identity.test.bookdemo.model.Book;
import com.identity.test.bookdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
       return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> findBookByISBN(@PathVariable String isbn) {
        return new ResponseEntity<>(bookService.findBookByISBN(isbn), HttpStatus.OK);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> removeBook(@PathVariable String isbn) {
        bookService.removeBook(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> findBooksByAuthor(@PathVariable String author) {
        return new ResponseEntity<>(bookService.findBooksByAuthor(author), HttpStatus.OK);
    }

    @PutMapping("/borrow/{isbn}")
    public ResponseEntity<Book> borrowBook(@PathVariable String isbn) {
        return new ResponseEntity<>(bookService.borrowBook(isbn), HttpStatus.OK);
    }

    @PutMapping("/return/{isbn}")
    public ResponseEntity<Book> returnBook(@PathVariable String isbn) {
        return new ResponseEntity<>(bookService.returnBook(isbn), HttpStatus.OK);
    }


}
