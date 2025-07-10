package com.racoonash.librarymanagement.librarymanagement.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.racoonash.librarymanagement.librarymanagement.Enum.BookStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// This class represents a Data Transfer Object (DTO) for Book Copy.
// It contains fields for the copy ID, book details, book lend details, copy number,
// and the status of the book copy.
// The @JsonIgnore annotation is used to prevent serialization of the book and book lend details,
// as they may contain circular references or are not needed in the response.
// The @Min annotation ensures that the copy number is at least 1.
// The @NotNull annotation ensures that the book status is not null.


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookCopyDTO {

   private long copyId;

    @JsonIgnore
    private BookDTO book;
    
    @JsonIgnore
    private List<BookLendDTO> bookLend;

    @Min(value = 1)
    private long copyNumber;

    @NotNull(message = "Book status should not be null")
    private BookStatus status;

}
