package com.racoonash.librarymanagement.librarymanagement.Response;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.racoonash.librarymanagement.librarymanagement.DTO.BookDTO;
import com.racoonash.librarymanagement.librarymanagement.Entity.BookCopyEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// This class represents a response containing book details and its associated copies.
// It includes the book information and a list of book copies.
@Getter
@Setter
@NoArgsConstructor
@Service
public class BookCopiesResponse {

    @NotNull(message= "Book Should Not Be null")
    @NotBlank
    private BookDTO book;
    
    @NotNull
    @jakarta.validation.constraints.NotEmpty
    private List<BookCopyEntity> bookCopies;


    public BookCopiesResponse(@Validated BookDTO book, List<BookCopyEntity> bookCopies) {
        this.book = book;
        this.bookCopies = bookCopies;
    }

}
