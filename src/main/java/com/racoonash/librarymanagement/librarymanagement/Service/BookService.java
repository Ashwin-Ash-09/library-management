package com.racoonash.librarymanagement.librarymanagement.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.racoonash.librarymanagement.librarymanagement.DTO.BookDTO;
import com.racoonash.librarymanagement.librarymanagement.Entity.BookEntity;
import com.racoonash.librarymanagement.librarymanagement.Exceptions.BookNotFoundExceptions;
import com.racoonash.librarymanagement.librarymanagement.Mappers.AllMappers;
import com.racoonash.librarymanagement.librarymanagement.Repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;
    private final AllMappers mapper;

    public BookService(BookRepository bookRepository, AllMappers mapper) {
        this.mapper = mapper;
        this.bookRepository = bookRepository;
    }

    public BookDTO addBook(@Validated BookDTO book) {

        BookEntity newBook = mapper.toBookEntity(book);
        book = mapper.toBookDTO(bookRepository.save(newBook));

        return book;
    }

    public List<BookDTO> retriveOGBooks() {
        List<BookDTO> listBooks = mapper.toBookDTOList(bookRepository.findAll());
        return listBooks;
    }

    protected BookEntity retriveBookEntityById(long id) {

        BookEntity book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            StringBuffer str = new StringBuffer();
            str.append("Book Not Found ");
            str.append(" There is no book with the ID " + id);
            throw new BookNotFoundExceptions(str.toString());
        }
        return book;
    }

    public BookDTO retriveBookDTOById(long id) {

        BookDTO book = mapper.toBookDTO(retriveBookEntityById(id));
        return book;
    }

    public BookDTO updateBook(long id, BookDTO bookDTO) {
        
        BookEntity oldBook = retriveBookEntityById(id);
        mapper.updateEntityFromDTO(bookDTO, oldBook);
        bookRepository.save(oldBook);
        return mapper.toBookDTO(oldBook);
    }

    public void deleteBook (long id){
        retriveBookDTOById(id);
        bookRepository.deleteById(id);
    }

    public void addMultipleOriginalBooks(List<BookDTO> bookDTOs) {
        bookDTOs.stream().forEach(bookDTO -> addBook(bookDTO));

    }

    public List<BookDTO> findAllByName(String bookName){

        List<BookEntity>bokList = bookRepository.findByBookNameContaining(bookName);

        return mapper.toBookDTOList(bokList);
    }

    public List<BookDTO> findAllByIssbn(String issbn) {
       List<BookEntity>bokList = bookRepository.findByIsbn(issbn);

        return mapper.toBookDTOList(bokList);
    }
}
