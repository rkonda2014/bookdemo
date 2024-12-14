package com.identity.test.bookdemo.controller;

import com.identity.test.bookdemo.model.Book;
import com.identity.test.bookdemo.repository.BookRepository;
import com.identity.test.bookdemo.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.identity.test.bookdemo.util.TestUtil.getBookJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;
    private Book book;


    @Test
    public void testAddBook() throws Exception {
        book = TestUtil.getBook("12345", "Rao");
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getBookJsonString(book)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("MYJAVA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Rao"));
    }

    @Test
    public void testFindBookByISBN() throws Exception {
        Book book = TestUtil.getBook("12345", "Rao");
        bookRepository.save(book);
        mockMvc.perform(get("/api/books/{isbn}", "12345"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("MYJAVA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Rao"));
    }


    @Test
    public void testFindBookByISBN_NotFound() throws Exception {
        mockMvc.perform(get("/api/books/{isbn}", "99999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Book Not Found with ISBN: 99999"));
    }

    @Test
    public void testRemoveBook() throws Exception {
        Book book = TestUtil.getBook("12345", "Rao");
        bookRepository.save(book);
        mockMvc.perform(delete("/api/books/{isbn}", "12345"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    public void testRemoveBook_NotFound() throws Exception {
        mockMvc.perform(delete("/api/books/{isbn}", "99999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Book Not Found with ISBN: 99999"));
    }


    @Test
    public void testBorrowBook() throws Exception {
        Book book = TestUtil.getBook("12345", "Rao");
        bookRepository.save(book);
        mockMvc.perform(put("/api/books/borrow/{isbn}", "12345"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.availableCopies").value(9));
    }

    @Test
    public void testBorrowBook_NoCopiesAvailable() throws Exception {
        book = TestUtil.getBook("12345", "Rao");
        book.setAvailableCopies(0);
        bookRepository.save(book);
        mockMvc.perform(put("/api/books/borrow/{isbn}", "12345"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("No copies available for ISBN 12345"));
    }


    @Test
    public void testReturnBook() throws Exception {
        book = TestUtil.getBook("12345", "Rao");
        bookRepository.save(book);
        mockMvc.perform(put("/api/books/return/{isbn}", "12345"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.availableCopies").value(11));
    }

}
