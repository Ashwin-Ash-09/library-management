package com.racoonash.librarymanagement.librarymanagement.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.racoonash.librarymanagement.librarymanagement.Entity.BookEntity;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookLendDTO {

    private Long bookLendId;
    @JsonIgnore
    private BookEntity book;
    @JsonIgnore
    private BookCopyDTO bookCopy;
    @JsonIgnore
    private UserDTO users;

    @FutureOrPresent(message = "Due date must be in the future or present")
    private LocalDateTime issueDateTime;

    @FutureOrPresent(message = "Due date must be in the future or present")
    private LocalDateTime dueDateTime;

    @NotNull(message = "returned should not be null")
    private Boolean isReturned;

}
