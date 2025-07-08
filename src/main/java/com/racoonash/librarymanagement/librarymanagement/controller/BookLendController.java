package com.racoonash.librarymanagement.librarymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.racoonash.librarymanagement.librarymanagement.DTO.BookLendDTO;
import com.racoonash.librarymanagement.librarymanagement.Response.BookLendResponse;
import com.racoonash.librarymanagement.librarymanagement.Service.BookLendService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class BookLendController {

    private BookLendService lendService;

    public BookLendController(BookLendService lendService){
        this.lendService = lendService;
    }

    @PostMapping("/lend-book/user-id-{user-id}/book-id-{book-id}/book-copy-id-{book-copy-id}")
    public ResponseEntity<Object> postMethodName(
            @PathVariable(name = "book-id") long bookId,
            @PathVariable(name = "book-copy-id") long bookCopyId,
            @PathVariable(name = "user-id") long userId

    ) {
        lendService.bookLendBYUser(userId,bookId,bookCopyId);
         
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/lend-book/{id}")
    public ResponseEntity<BookLendResponse> retriveBookLendInfo(@PathVariable long id) {
        
        return new ResponseEntity<>(lendService.retirveBookLendAllInfo(id),HttpStatus.OK);
    }


    @GetMapping("/lend-book")
    public ResponseEntity<List<BookLendDTO>> retriveAllBookLendInfo() {

        List<BookLendDTO> bookLendDTOs = lendService.retriveAllLendBooks();
        return new ResponseEntity<>(bookLendDTOs,HttpStatus.OK);
    }

    @PostMapping("/lend-book/{id}/return")
    public ResponseEntity<Object> bookReturened(@PathVariable long id) {
        lendService.updateReturnedBook(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
