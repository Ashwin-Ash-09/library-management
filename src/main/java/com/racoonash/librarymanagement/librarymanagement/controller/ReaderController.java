package com.racoonash.librarymanagement.librarymanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import com.racoonash.librarymanagement.librarymanagement.DTO.UserDTO;
import com.racoonash.librarymanagement.librarymanagement.Entity.UserEntity;
import com.racoonash.librarymanagement.librarymanagement.Exceptions.UserNotFoundException;
import com.racoonash.librarymanagement.librarymanagement.Service.UserService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/users")
public class ReaderController {

    private UserService readerService;

    public ReaderController(UserService readerService) {
        this.readerService = readerService;
    }

    // This method retrieves a reader by their ID.
    // It returns a ResponseEntity containing a UserDTO object,
    // which includes the reader's details.
    // If the reader is not found, it throws a UserNotFoundException.
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getReader(@PathVariable long id) throws UserNotFoundException {
        UserDTO user = readerService.retriveReaderDTOById(id);
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }

    // This method retrieves all readers.
    // It returns a ResponseEntity containing a list of UserEntity objects,
    // which represent all readers in the system.
    // This method is typically used to display all registered readers.
    @GetMapping("")
    public ResponseEntity<List<UserEntity>> getAllReaders() {
        List<UserEntity> users = readerService.findAllUsers();
        return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);

    }

    // This method creates a new reader.
    // It accepts a UserDTO object in the request body, validates it,
    // and returns a ResponseEntity with the created reader's data and an HTTP status of CREATED.
    // This method is used to register a new reader in the system.
    // If the reader is successfully created, it returns the created UserDTO object.
    @PostMapping("")
    public ResponseEntity<UserDTO> createNewReader(@Valid @RequestBody UserDTO user) {
        readerService.createUser(user);
        return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
    }

    // This method deletes a reader by their ID.
    // It returns a ResponseEntity with an HTTP status of NO_CONTENT.
    // This method is used to remove a reader from the system.
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReaderByID(@PathVariable long id){
        readerService.deleteReaderByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // This method updates an existing reader by their ID.
    // It accepts a UserDTO object in the request body, validates it,
    // and returns a ResponseEntity with an HTTP status of NO_CONTENT.
    // This method is used to modify the details of an existing reader.
    // If the reader is successfully updated, it returns an HTTP status of NO_CONTENT.
    // If the reader is not found, it throws a UserNotFoundException.
    @PutMapping("/{id}")
     public ResponseEntity<Object> updateReaderByID(@PathVariable long id,@Valid @RequestBody UserDTO readersDTO){
        readerService.updateReaderByID(id,readersDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
