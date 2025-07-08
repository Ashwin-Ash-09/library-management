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
