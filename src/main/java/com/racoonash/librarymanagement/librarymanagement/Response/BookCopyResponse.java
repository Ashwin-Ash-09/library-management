package com.racoonash.librarymanagement.librarymanagement.Response;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.racoonash.librarymanagement.librarymanagement.DTO.BookCopyDTO;
import com.racoonash.librarymanagement.librarymanagement.DTO.BookDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Service
public class BookCopyResponse {

    @NotNull(message = "Book Should Not Be null")
    @NotBlank
    private BookDTO book;

    @NotNull
    @NotBlank
    private BookCopyDTO bookCopy;

    public BookCopyResponse(@Validated BookDTO book, BookCopyDTO bookCopy) {
        this.book = book;
        this.bookCopy = bookCopy;
    }

}
