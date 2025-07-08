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

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getReader(@PathVariable long id) throws UserNotFoundException {
        UserDTO user = readerService.retriveReaderDTOById(id);
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<UserEntity>> getAllReaders() {
        List<UserEntity> users = readerService.findAllUsers();
        return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);

    }

    @PostMapping("")
    public ResponseEntity<UserDTO> createNewReader(@Valid @RequestBody UserDTO user) {
        readerService.createUser(user);
        return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReaderByID(@PathVariable long id){
        readerService.deleteReaderByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
     public ResponseEntity<Object> updateReaderByID(@PathVariable long id,@Valid @RequestBody UserDTO readersDTO){
        readerService.updateReaderByID(id,readersDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
