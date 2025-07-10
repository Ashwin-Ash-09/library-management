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

    // This method adds a new book to the repository.
    // It converts the BookDTO to a BookEntity, saves it in the repository,
    // and returns the saved BookDTO.
    // This is typically used to create a new book record in the system.
    public BookDTO addBook(@Validated BookDTO book) {

        BookEntity newBook = mapper.toBookEntity(book);
        book = mapper.toBookDTO(bookRepository.save(newBook));

        return book;
    }

    // This method retrieves all original books from the repository.
    // It returns a list of BookDTO objects representing all the books.
    // This is typically used to display all books in the system.
    // It uses the mapper to convert the list of BookEntity objects to BookDTO objects.
    public List<BookDTO> retriveOGBooks() {
        List<BookDTO> listBooks = mapper.toBookDTOList(bookRepository.findAll());
        return listBooks;
    }

    // This method retrieves a BookEntity by its ID.
    // It checks if the book exists in the repository,
    // and if it does, it returns the BookEntity.
    // If the book is not found, it throws a BookNotFoundExceptions.
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

    // This method retrieves a BookDTO by its ID.
    // It uses the mapper to convert the BookEntity to a BookDTO and returns it.
    // This is typically used to get the details of a specific book in a DTO format.
    public BookDTO retriveBookDTOById(long id) {

        BookDTO book = mapper.toBookDTO(retriveBookEntityById(id));
        return book;
    }

    // This method updates an existing book by its ID.
    // It retrieves the existing book entity, updates its fields from the provided BookDTO,
    // saves the updated entity, and returns the updated BookDTO.
    // This is typically used to modify the details of an existing book.
    public BookDTO updateBook(long id, BookDTO bookDTO) {
        
        BookEntity oldBook = retriveBookEntityById(id);
        mapper.updateEntityFromDTO(bookDTO, oldBook);
        bookRepository.save(oldBook);
        return mapper.toBookDTO(oldBook);
    }

    // This method deletes a book by its ID.
    // It checks if the book exists, and if it does, it deletes it from the repository.
    // If the book is not found, it throws a BookNotFoundExceptions.
    // This is typically used to remove a book from the system.
    public void deleteBook (long id){
        retriveBookDTOById(id);
        bookRepository.deleteById(id);
    }

    // This method adds multiple original books to the repository.
    // It accepts a list of BookDTO objects, iterates through them,
    // and calls the addBook method for each book.
    // This is typically used to register multiple books in the system at once.
    public void addMultipleOriginalBooks(List<BookDTO> bookDTOs) {
        bookDTOs.stream().forEach(bookDTO -> addBook(bookDTO));

    }

    // This method finds books by their name.
    // It accepts a book name as a request parameter and returns a list of BookDTO objects
    // that match the provided name. It uses the bookRepository to search for books.
    public List<BookDTO> findAllByName(String bookName){

        List<BookEntity>bokList = bookRepository.findByBookNameContaining(bookName);

        return mapper.toBookDTOList(bokList);
    }

    // This method finds books by their ISBN.
    // It accepts an ISBN as a request parameter and returns a list of BookDTO objects
    // that match the provided ISBN. It uses the bookRepository to search for books.
    public List<BookDTO> findAllByIssbn(String issbn) {
       List<BookEntity>bokList = bookRepository.findByIsbn(issbn);

        return mapper.toBookDTOList(bokList);
    }
}
