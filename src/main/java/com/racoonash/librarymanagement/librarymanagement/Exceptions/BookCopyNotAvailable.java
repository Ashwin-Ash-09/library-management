package com.racoonash.librarymanagement.librarymanagement.Exceptions;

public class BookCopyNotAvailable extends IllegalArgumentException {

    public BookCopyNotAvailable(String message) {
        super(message);
    }
}
