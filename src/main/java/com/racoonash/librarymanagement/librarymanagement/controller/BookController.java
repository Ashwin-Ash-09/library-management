package com.racoonash.librarymanagement.librarymanagement.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.racoonash.librarymanagement.librarymanagement.DTO.BookCopyDTO;
import com.racoonash.librarymanagement.librarymanagement.DTO.BookDTO;
import com.racoonash.librarymanagement.librarymanagement.Exceptions.BookCopyNotFoundExceptions;
import com.racoonash.librarymanagement.librarymanagement.Response.BookCopiesResponse;
import com.racoonash.librarymanagement.librarymanagement.Response.BookCopyResponse;
import com.racoonash.librarymanagement.librarymanagement.Service.BookCopyService;
import com.racoonash.librarymanagement.librarymanagement.Service.BookService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // This controller handles requests related to books and book copies.
    private BookService bookService;
    private BookCopyService bookCopyService;

    // Constructor injection is used to inject the BookService and BookCopyService dependencies.
    public BookController(BookService bookService, BookCopyService bookCopyService) {
        this.bookService = bookService;
        this.bookCopyService = bookCopyService;
    }

    // This method retrieves all original books.
    // It returns a list of BookDTO objects representing the books.
    // The method is mapped to the GET request at "/api/books".
    @GetMapping("")
    public List<BookDTO> getAllOriginalBook() {

        List<BookDTO> books = bookService.retriveOGBooks();
        return books;
    }

    // This method adds a new original book.
    // It accepts a BookDTO object in the request body, validates it,
    // and returns a response with the created book's URI and the book data.
    @PostMapping("/add-many")
    public ResponseEntity<Object> addMultiOriginalBooks(@Valid @RequestBody List<BookDTO> bookDTOs) {

        bookService.addMultipleOriginalBooks(bookDTOs);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // This method retrieves a book by its ID.
    // It returns a ResponseEntity containing the BookDTO object and an HTTP status of OK.
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable long id) {
        BookDTO book = bookService.retriveBookDTOById(id);
        return new ResponseEntity<BookDTO>(book, HttpStatus.OK);
    }

    // This method updates an existing original book.
    // It accepts a BookDTO object in the request body, validates it,
    // and returns a response with the updated book's URI and the book data.
    @PostMapping("")
    public ResponseEntity<BookDTO> updateoriginalBook(@Valid @RequestBody BookDTO book) {
        book = bookService.addBook(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(book.getBookId()).toUri();
        new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.created(location).body(book);
    }

    // This method retrieves all copies of a book by its ID.
    // It returns a ResponseEntity containing a BookCopiesResponse object,
    // which includes the book details and a list of its copies.
    @GetMapping("/{id}/copies")
    public ResponseEntity<BookCopiesResponse> retriveBookCopy(@PathVariable long id) {
        BookCopiesResponse response = new BookCopiesResponse(bookService.retriveBookDTOById(id),
                bookCopyService.retriveAllCopies(id));
        return new ResponseEntity<BookCopiesResponse>(response, HttpStatus.OK);
    }

    // This method adds a new copy to an existing book.
    // It accepts a BookCopyDTO object in the request body, validates it,
    // and returns a response with the updated book's URI and the book data.
    @PostMapping("/{id}/copies")
    public ResponseEntity<BookDTO> addBookCopy(@PathVariable long id, @Valid @RequestBody BookCopyDTO bookCopy) {
        bookCopy = bookCopyService.addBookCopy(id, bookCopy);
        return new ResponseEntity<BookDTO>(bookService.retriveBookDTOById(id), HttpStatus.ACCEPTED);
    }

    // This method retrieves information about a specific book copy by its ID.
    // It checks if the book ID matches the one associated with the copy,
    // and returns a ResponseEntity containing a BookCopyResponse object,
    // which includes the book details and the copy details.
    @GetMapping("/{id}/copies/{copy-id}")
    public ResponseEntity<BookCopyResponse> getCopyInfo(@Valid @PathVariable Long id,
            @PathVariable(name = "copy-id") long copyId) {

        BookCopyDTO bookCopyDTO = bookCopyService.retriveCopyDTOById(copyId);
        if (id != bookCopyDTO.getBook().getBookId())
            throw new BookCopyNotFoundExceptions("Book Copy not found check book id and Copy Id are correct");

        BookCopyResponse res = new BookCopyResponse(
                bookService.retriveBookDTOById(id),
                bookCopyService.retriveCopyDTOById(copyId));

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // This method updates an existing book by its ID.
    // It accepts a BookDTO object in the request body, validates it,
    // and returns a ResponseEntity containing the updated book data.
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable long id, @Valid @RequestBody BookDTO book) {
        book = bookService.updateBook(id, book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // This method deletes a book by its ID.
    // It returns a ResponseEntity with an HTTP status of NO_CONTENT.
    // It also deletes all copies associated with the book.
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // This method deletes a specific book copy by its ID.
    // It returns a ResponseEntity with an HTTP status of NO_CONTENT.
    // It also checks if the book ID matches the one associated with the copy.
    // If the copy is not found, it throws a BookCopyNotFoundExceptions.
    @DeleteMapping("/{id}/copies/{copy-id}")
    public ResponseEntity<Object> deleteBookCopy(@PathVariable(name = "copy-id") long copyId) {
        bookCopyService.deleteBookCopyById(copyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // This method updates a specific book copy by its ID.
    // It accepts a BookCopyDTO object in the request body, validates it,
    // and returns a ResponseEntity containing the updated book copy data.
    @PutMapping("/{id}/copies/{copy-id}")
    public ResponseEntity<BookCopyDTO> updateBookCopyById(@PathVariable(name = "copy-id") long id,
            @Valid @RequestBody BookCopyDTO book) {
        book = bookCopyService.updateBookCopyById(id, book);
        return new ResponseEntity<BookCopyDTO>(book, HttpStatus.OK);
    }

    // This method finds books by their name.
    // It accepts a book name as a request parameter and returns a list of BookDTO objects
    // that match the given name.
    @GetMapping("/find-by-name")
    public ResponseEntity<List<BookDTO>> findBookByName(@RequestParam(name = "name") String bookName) {
        List<BookDTO> bookDTOs = bookService.findAllByName(bookName);
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    // This method finds books by their ISBN.
    // It accepts an ISBN as a request parameter and returns a list of BookDTO objects
    // that match the given ISBN.
    @GetMapping("/find-by-isbn")
    public ResponseEntity<List<BookDTO>> findBookIssbn(@RequestParam String issbn) {
        List<BookDTO> bookDTOs = bookService.findAllByIssbn(issbn);
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

}
