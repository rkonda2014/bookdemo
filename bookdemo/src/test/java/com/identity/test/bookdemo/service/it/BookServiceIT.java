package com.identity.test.bookdemo.service.it;

import com.identity.test.bookdemo.exception.BookNotFoundException;
import com.identity.test.bookdemo.model.Book;
import com.identity.test.bookdemo.repository.BookRepository;
import com.identity.test.bookdemo.service.BookService;
import com.identity.test.bookdemo.util.TestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookServiceIT {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testAddBook() {
       Book dbBook = bookService.addBook(TestUtil.getBook("2233","Rao"));
       assertNotNull(dbBook);
       assertEquals("2233", dbBook.getIsbn());
       assertEquals("Rao", dbBook.getAuthor());
    }

    @Test
    void testRemoveBook_NotFound() {
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, ()
                -> bookService.removeBook("99999"));
        assertEquals("Book Not Found with ISBN: 99999", exception.getMessage());
    }

    @Test
    void testFindBookByISBN() {
        Book book = TestUtil.getBook("2233","Rao");
        bookRepository.save(book);
        Book dbBook = bookService.findBookByISBN("2233");
        assertNotNull(dbBook);
        assertEquals("2233", dbBook.getIsbn());
        assertEquals("MYJAVA", dbBook.getTitle());
    }

    @Test
    void testFindBookByISBN_NotFound() {
        assertThrows(BookNotFoundException.class, () -> bookService.findBookByISBN("99999"));
    }

    @Test
    void testFindBooksByAuthor() {
        Book book1 = TestUtil.getBook("7788", "MYAUTHOR");
        Book book2 = TestUtil.getBook("8899", "MYAUTHOR");
        Book book3 = TestUtil.getBook("4455", "KONDA");
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        List<Book> books = bookService.findBooksByAuthor("MYAUTHOR");
        assertEquals(2, books.size());
    }

    @Test
    void testBorrowBook() {
        Book book = TestUtil.getBook("7788", "MYAUTHOR");
        book.setAvailableCopies(5);
        bookRepository.save(book);
        Book updatedBook = bookService.borrowBook("7788");
        assertEquals(4, updatedBook.getAvailableCopies());
    }

    @Test
    void testBorrowBook_NoCopiesAvailable() {
        Book book = TestUtil.getBook("7788", "MYAUTHOR");
        book.setAvailableCopies(0);
        bookRepository.save(book);
        assertThrows(IllegalStateException.class, () -> bookService.borrowBook("7788"));
    }

    @Test
    void testReturnBook() {
        Book book = TestUtil.getBook("6789", "RK");
        book.setAvailableCopies(5);
        bookRepository.save(book);
        Book updatedBook = bookService.returnBook("6789");
        assertEquals(6, updatedBook.getAvailableCopies());
    }

}
