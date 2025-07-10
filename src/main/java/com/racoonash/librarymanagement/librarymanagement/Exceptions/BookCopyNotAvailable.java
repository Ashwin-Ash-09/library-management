package com.racoonash.librarymanagement.librarymanagement.Exceptions;


// This exception is thrown when a book copy is not available for lending.
// It extends IllegalArgumentException and can be used to indicate that a requested book copy
// is not available in the library system, possibly due to it being already issued or not existing
public class BookCopyNotAvailable extends IllegalArgumentException {

    public BookCopyNotAvailable(String message) {
        super(message);
    }
}
