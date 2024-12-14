package com.identity.test.bookdemo.service;

import com.identity.test.bookdemo.exception.BookNotFoundException;
import com.identity.test.bookdemo.model.Book;
import com.identity.test.bookdemo.repository.BookRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    private final BookRepository bookRepository = mock(BookRepository.class);
    private final BookService bookService = new BookServiceImpl(bookRepository);

    @Test
    void testAddBook() {
        Book book = new Book();
        book.setIsbn("1234");
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book, bookService.addBook(book));
    }

    @Test
    void testFindBookByISBN_NotFound() {
        when(bookRepository.findById("1234")).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.findBookByISBN("1234"));
    }
}
