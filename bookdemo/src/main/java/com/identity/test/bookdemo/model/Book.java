package com.identity.test.bookdemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private  String isbn;
    private  String title;
    private  String author;
    private  int publicationYear;
    private  int availableCopies;
}
