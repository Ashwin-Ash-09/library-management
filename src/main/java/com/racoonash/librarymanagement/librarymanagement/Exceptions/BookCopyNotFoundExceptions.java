package com.racoonash.librarymanagement.librarymanagement.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookCopyNotFoundExceptions extends RuntimeException{

    
    public BookCopyNotFoundExceptions (String message){
        super(message);
    }
}
