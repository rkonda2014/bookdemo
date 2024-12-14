package com.identity.test.bookdemo.repository;

import com.identity.test.bookdemo.model.Book;
import com.identity.test.bookdemo.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BookRepositoryTestIT {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testBooksByAuthor() {
        bookRepository.save(TestUtil.getBook("1122", "Rao"));
        List<Book> books = bookRepository.findByAuthor("Rao");
        assertEquals(1, books.size());
        assertEquals("Rao", books.get(0).getAuthor());

    }
}
