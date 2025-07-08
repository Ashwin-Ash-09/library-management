package com.racoonash.librarymanagement.librarymanagement.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.racoonash.librarymanagement.librarymanagement.DTO.BookCopyDTO;
import com.racoonash.librarymanagement.librarymanagement.Entity.BookCopyEntity;
import com.racoonash.librarymanagement.librarymanagement.Enum.BookStatus;
import com.racoonash.librarymanagement.librarymanagement.Exceptions.BookCopyNotFoundExceptions;
import com.racoonash.librarymanagement.librarymanagement.Mappers.AllMappers;
import com.racoonash.librarymanagement.librarymanagement.Repository.BookCopyRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookCopyService {

    private BookCopyRepository bookCopyRepository;
    private BookService bookService;
    AllMappers mapper;

    public BookCopyService(BookCopyRepository bookCopyRepository, BookService bookService, AllMappers mapper) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookService = bookService;
        this.mapper = mapper;
    }

    public BookCopyDTO addBookCopy(long id, BookCopyDTO bookCopy) {

        BookCopyEntity bookCopyEntity = mapper.toBookCopyEntity(bookCopy);
        bookCopyEntity.setBook(bookService.retriveBookEntityById(id));
        bookCopyRepository.save(bookCopyEntity);
        return mapper.toBookCopyDTO(bookCopyEntity);

    }

    public List<BookCopyEntity> retriveAllCopies(long id) {
        return bookCopyRepository.findAllByBookBookId(id);
    }

    public BookCopyEntity retriveCopyEntityById(long id) {

        BookCopyEntity copy = bookCopyRepository.findById(id).orElse(null);
        if (copy == null)
            throw new BookCopyNotFoundExceptions("Entered Book Copy Not Found");
        return copy;
    }

    public BookCopyDTO retriveCopyDTOById(long id) {

        BookCopyDTO copy = mapper.toBookCopyDTO(retriveCopyEntityById(id));
        return copy;
    }

    public BookCopyDTO updateBookCopyStatus(long id, BookStatus status) {
        BookCopyEntity book = retriveCopyEntityById(id);
        book.setStatus(status);
        book = bookCopyRepository.save(book);
        return mapper.toBookCopyDTO(book);

    }

    public void deleteBookCopyById(long id) {
        bookCopyRepository.deleteById(id);
    }

    public BookCopyDTO updateBookCopyById(long id, BookCopyDTO book) {

        BookCopyEntity bookCopyEntity = retriveCopyEntityById(id);

        mapper.updateEntityFromDTO(book, bookCopyEntity);
        bookCopyEntity = bookCopyRepository.save(bookCopyEntity);
        book = mapper.toBookCopyDTO(bookCopyEntity);

        return book;
    }
}
