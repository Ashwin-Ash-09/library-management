package com.racoonash.librarymanagement.librarymanagement.Entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.racoonash.librarymanagement.librarymanagement.Enum.Roles;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


// This class represents a User entity in the library management system.
// It contains fields for the reader ID, book lend details, username, email, role,
// and join date and time.

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
@Entity(name = "readers")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readerId;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BookLendEntity> bookLend;

    @NotNull(message = "username should not be null")
    @NotEmpty(message = "username should not be empty")
    private String username;
    
    @NotNull(message = "username should not be null")
    @NotEmpty(message = "username should not be empty")
    private String email;

    private Roles role;

    private LocalDateTime joinDateTime;

}
