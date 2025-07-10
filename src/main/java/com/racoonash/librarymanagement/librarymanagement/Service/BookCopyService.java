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

    // This method adds a new book copy to the repository.
    // It retrieves the book entity by its ID, sets it to the book copy entity,
    // saves the book copy entity, and returns the corresponding BookCopyDTO.
    // If the book entity is not found, it throws a BookCopyNotFoundExceptions.
    public BookCopyDTO addBookCopy(long id, BookCopyDTO bookCopy) {

        BookCopyEntity bookCopyEntity = mapper.toBookCopyEntity(bookCopy);
        bookCopyEntity.setBook(bookService.retriveBookEntityById(id));
        bookCopyRepository.save(bookCopyEntity);
        return mapper.toBookCopyDTO(bookCopyEntity);

    }

    // This method retrieves all book copies associated with a specific book ID.
    // It returns a list of BookCopyEntity objects representing all copies of the book.
    // This is typically used to display all copies of a particular book.
    public List<BookCopyEntity> retriveAllCopies(long id) {
        return bookCopyRepository.findAllByBookBookId(id);
    }

    // This method retrieves a specific book copy entity by its ID.
    // If the book copy is not found, it throws a BookCopyNotFoundExceptions.
    // It returns the BookCopyEntity object corresponding to the provided ID.
    public BookCopyEntity retriveCopyEntityById(long id) {

        BookCopyEntity copy = bookCopyRepository.findById(id).orElse(null);
        if (copy == null)
            throw new BookCopyNotFoundExceptions("Entered Book Copy Not Found");
        return copy;
    }

    // This method retrieves a specific book copy DTO by its ID.
    // It converts the BookCopyEntity to a BookCopyDTO using the mapper and returns it.
    // This is typically used to get the details of a specific book copy in a DTO format
    public BookCopyDTO retriveCopyDTOById(long id) {

        BookCopyDTO copy = mapper.toBookCopyDTO(retriveCopyEntityById(id));
        return copy;
    }

    // This method updates the status of a book copy by its ID.
    // It retrieves the book copy entity, sets the new status, saves it,
    // and returns the updated BookCopyDTO.
    public BookCopyDTO updateBookCopyStatus(long id, BookStatus status) {
        BookCopyEntity book = retriveCopyEntityById(id);
        book.setStatus(status);
        book = bookCopyRepository.save(book);
        return mapper.toBookCopyDTO(book);

    }

    // This method deletes a book copy by its ID.
    // It checks if the book copy exists, and if it does, it deletes it from the repository.
    // If the book copy is not found, it throws a BookCopyNotFoundExceptions.
    public void deleteBookCopyById(long id) {
        bookCopyRepository.deleteById(id);
    }


    // This method updates an existing book copy by its ID.
    // It retrieves the book copy entity, updates its fields from the provided BookCopyDTO,
    // saves the updated entity, and returns the updated BookCopyDTO.
    // This is typically used to modify the details of an existing book copy.
    public BookCopyDTO updateBookCopyById(long id, BookCopyDTO book) {

        BookCopyEntity bookCopyEntity = retriveCopyEntityById(id);

        mapper.updateEntityFromDTO(book, bookCopyEntity);
        bookCopyEntity = bookCopyRepository.save(bookCopyEntity);
        book = mapper.toBookCopyDTO(bookCopyEntity);

        return book;
    }
}
