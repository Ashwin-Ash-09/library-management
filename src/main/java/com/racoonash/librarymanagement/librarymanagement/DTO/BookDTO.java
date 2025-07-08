package com.racoonash.librarymanagement.librarymanagement.DTO;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookDTO {

    private long bookId;

    @NotBlank(message = "book name should not be blank")
    @NotEmpty(message = "book name should not be empty")
    @NotNull
    private String bookName;

    @NotBlank(message = "author name should not be blank")
    @NotEmpty(message = "author name should not be empty")
    @NotNull
    private String author;

    @NotBlank(message = "isbn should not be blank")
    @NotEmpty(message = "isbn name should not be empty")
    @NotNull
    private String isbn;

    @Past(message = "Publish should be past")
    @FutureOrPresent(message = "Due date must be in the future or present")
    private Date publishDate;

    @JsonIgnore
    private List<BookLendDTO> bookLend;

    @JsonIgnore
    private List<BookCopyDTO> bookCopy;

}
