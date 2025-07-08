package com.racoonash.librarymanagement.librarymanagement.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.racoonash.librarymanagement.librarymanagement.Enum.Roles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
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
public class UserDTO {

    private Long readerId;
    @JsonIgnore
    private List<BookLendDTO> bookLend;

    @NotBlank(message = "book name should not be blank")
    @NotEmpty(message = "book name should not be empty")
    private String username;
    
    @NotBlank(message = "email should not be blank")
    @NotEmpty(message = "email should not be empty")
    private String email;
    
    
    private Roles role;
    
    @PastOrPresent(message = "Join date should not be future")
    private LocalDateTime joinDateTime;
}
