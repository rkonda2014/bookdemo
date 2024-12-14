package com.identity.test.bookdemo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.identity.test.bookdemo.model.Book;

public class TestUtil {

    public static Book getBook(String isbn, String author) {
        return Book.builder().author(author).isbn(isbn).availableCopies(10).publicationYear(2000)
                .title("MYJAVA").build();
    }

    public static String getBookJsonString(Book book) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(book);
    }

}