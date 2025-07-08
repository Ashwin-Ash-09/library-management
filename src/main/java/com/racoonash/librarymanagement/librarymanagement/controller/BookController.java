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

    private BookService bookService;
    private BookCopyService bookCopyService;

    public BookController(BookService bookService, BookCopyService bookCopyService) {
        this.bookService = bookService;
        this.bookCopyService = bookCopyService;
    }

    @GetMapping("")
    public List<BookDTO> getAllOriginalBook() {

        List<BookDTO> books = bookService.retriveOGBooks();
        return books;
    }

    @PostMapping("/add-many")
    public ResponseEntity<Object> addMultiOriginalBooks(@Valid @RequestBody List<BookDTO> bookDTOs) {

        bookService.addMultipleOriginalBooks(bookDTOs);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable long id) {
        BookDTO book = bookService.retriveBookDTOById(id);
        return new ResponseEntity<BookDTO>(book, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<BookDTO> updateoriginalBook(@Valid @RequestBody BookDTO book) {
        book = bookService.addBook(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(book.getBookId()).toUri();
        new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.created(location).body(book);
    }

    @GetMapping("/{id}/copies")
    public ResponseEntity<BookCopiesResponse> retriveBookCopy(@PathVariable long id) {
        BookCopiesResponse response = new BookCopiesResponse(bookService.retriveBookDTOById(id),
                bookCopyService.retriveAllCopies(id));
        return new ResponseEntity<BookCopiesResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/copies")
    public ResponseEntity<BookDTO> addBookCopy(@PathVariable long id, @Valid @RequestBody BookCopyDTO bookCopy) {
        bookCopy = bookCopyService.addBookCopy(id, bookCopy);
        return new ResponseEntity<BookDTO>(bookService.retriveBookDTOById(id), HttpStatus.ACCEPTED);
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable long id, @Valid @RequestBody BookDTO book) {
        book = bookService.updateBook(id, book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/copies/{copy-id}")
    public ResponseEntity<Object> deleteBookCopy(@PathVariable(name = "copy-id") long copyId) {
        bookCopyService.deleteBookCopyById(copyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/copies/{copy-id}")
    public ResponseEntity<BookCopyDTO> updateBookCopyById(@PathVariable(name = "copy-id") long id,
            @Valid @RequestBody BookCopyDTO book) {
        book = bookCopyService.updateBookCopyById(id, book);
        return new ResponseEntity<BookCopyDTO>(book, HttpStatus.OK);
    }

    @GetMapping("/find-by-name")
    public ResponseEntity<List<BookDTO>> findBookByName(@RequestParam(name = "name") String bookName) {
        List<BookDTO> bookDTOs = bookService.findAllByName(bookName);
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    @GetMapping("/find-by-isbn")
    public ResponseEntity<List<BookDTO>> findBookIssbn(@RequestParam String issbn) {
        List<BookDTO> bookDTOs = bookService.findAllByIssbn(issbn);
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

}
