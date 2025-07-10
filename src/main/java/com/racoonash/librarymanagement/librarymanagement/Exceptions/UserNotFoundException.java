package com.racoonash.librarymanagement.librarymanagement.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;


// This exception is thrown when a user is not found in the library management system.
// It extends UsernameNotFoundException and is annotated with @ResponseStatus to return a 404 NOT FOUND
// status code when this exception is thrown. This can be used to indicate that a requested user
// does not exist in the system, possibly due to it being deleted or never existing.


@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends UsernameNotFoundException {

    public UserNotFoundException(String message) {
        super(message + " User Not Found");
    }
 
}
