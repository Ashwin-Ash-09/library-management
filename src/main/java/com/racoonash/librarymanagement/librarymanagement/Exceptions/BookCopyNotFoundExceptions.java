package com.racoonash.librarymanagement.librarymanagement.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// This exception is thrown when a book copy is not found in the library system.
// It extends RuntimeException and is annotated with @ResponseStatus to return a 404 NOT FOUND
// status code when this exception is thrown. This can be used to indicate that a requested book
// copy does not exist in the system, possibly due to it being deleted or never existing.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookCopyNotFoundExceptions extends RuntimeException{

    
    public BookCopyNotFoundExceptions (String message){
        super(message);
    }
}
