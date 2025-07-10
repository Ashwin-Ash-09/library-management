package com.racoonash.librarymanagement.librarymanagement.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// This class represents a Book Lend entity in the library management system.
// It contains fields for the book lend ID, book details, book copy details, user details,
// issue date and time, due date and time, and a boolean indicating if the book has been returned.

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@DynamicUpdate
@Entity(name = "book_lend")
public class BookLendEntity {

    public BookLendEntity(UserEntity reader, BookEntity book2, BookCopyEntity bookCopy2) {
        this.users = reader;
        this.book = book2;
        this.bookCopy = bookCopy2;
        this.issueDateTime = LocalDateTime.now();
        this.dueDateTime = this.issueDateTime.plusDays(7);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookLendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_copy_id")
    private BookCopyEntity bookCopy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private UserEntity users;

    private LocalDateTime issueDateTime;
    private LocalDateTime dueDateTime;

    @NotNull(message = "return is not null")
    private Boolean isReturned;
}
