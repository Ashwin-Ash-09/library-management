package com.racoonash.librarymanagement.librarymanagement.Response;


import com.racoonash.librarymanagement.librarymanagement.DTO.BookCopyDTO;
import com.racoonash.librarymanagement.librarymanagement.DTO.BookDTO;
import com.racoonash.librarymanagement.librarymanagement.DTO.BookLendDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// This class represents a response for lending a book.
// It contains the book details, the book copy details, the username of the borrower,
// and the book lend details.

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BookLendResponse {

    private BookDTO bookDTO;
    private BookCopyDTO bookCopyDTO;
    private String username;    
    private BookLendDTO bookLendDTO;
}
