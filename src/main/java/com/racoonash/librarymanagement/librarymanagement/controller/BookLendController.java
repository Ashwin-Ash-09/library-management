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

    // This method allows a user to lend a book by providing the user ID, book ID, and book copy ID.
    // It returns a ResponseEntity with an HTTP status of OK.
    @PostMapping("/lend-book/user-id-{user-id}/book-id-{book-id}/book-copy-id-{book-copy-id}")
    public ResponseEntity<Object> postMethodName(
            @PathVariable(name = "book-id") long bookId,
            @PathVariable(name = "book-copy-id") long bookCopyId,
            @PathVariable(name = "user-id") long userId

    ) {
        lendService.bookLendBYUser(userId,bookId,bookCopyId);
         
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // This method retrieves all information about a specific book lend by its ID.
    // It returns a ResponseEntity containing a BookLendResponse object,
    // which includes the book details, user details, and copy details.
    @GetMapping("/lend-book/{id}")
    public ResponseEntity<BookLendResponse> retriveBookLendInfo(@PathVariable long id) {
        
        return new ResponseEntity<>(lendService.retirveBookLendAllInfo(id),HttpStatus.OK);
    }


    // This method retrieves all book lend information.
    // It returns a ResponseEntity containing a list of BookLendDTO objects,
    // which represent the details of all lent books.
    @GetMapping("/lend-book")
    public ResponseEntity<List<BookLendDTO>> retriveAllBookLendInfo() {

        List<BookLendDTO> bookLendDTOs = lendService.retriveAllLendBooks();
        return new ResponseEntity<>(bookLendDTOs,HttpStatus.OK);
    }

    // This method updates the status of a book lend to indicate that the book has been returned.
    // It accepts the book lend ID as a path variable and returns a ResponseEntity with an HTTP status of ACCEPTED.
    // This method is used to mark a book as returned.
    // It updates the book lend record in the database to reflect that the book has been returned.
    @PostMapping("/lend-book/{id}/return")
    public ResponseEntity<Object> bookReturened(@PathVariable long id) {
        lendService.updateReturnedBook(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
