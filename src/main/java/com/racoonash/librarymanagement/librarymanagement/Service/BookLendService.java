package com.racoonash.librarymanagement.librarymanagement.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.racoonash.librarymanagement.librarymanagement.DTO.BookCopyDTO;
import com.racoonash.librarymanagement.librarymanagement.DTO.BookDTO;
import com.racoonash.librarymanagement.librarymanagement.DTO.BookLendDTO;
import com.racoonash.librarymanagement.librarymanagement.DTO.UserDTO;
import com.racoonash.librarymanagement.librarymanagement.Entity.BookCopyEntity;
import com.racoonash.librarymanagement.librarymanagement.Entity.BookEntity;
import com.racoonash.librarymanagement.librarymanagement.Entity.BookLendEntity;
import com.racoonash.librarymanagement.librarymanagement.Entity.UserEntity;
import com.racoonash.librarymanagement.librarymanagement.Enum.BookStatus;
import com.racoonash.librarymanagement.librarymanagement.Exceptions.BookCopyNotAvailable;
import com.racoonash.librarymanagement.librarymanagement.Exceptions.BookNotFoundExceptions;
import com.racoonash.librarymanagement.librarymanagement.Mappers.AllMappers;
import com.racoonash.librarymanagement.librarymanagement.Repository.BookLendRepository;
import com.racoonash.librarymanagement.librarymanagement.Response.BookLendResponse;

@Service
public class BookLendService {

    private BookLendRepository lendRepository;
    private BookCopyService bookCopyService;
    private BookService bookService;
    private UserService readerService;
    private AllMappers mapper;

    public BookLendService(BookLendRepository lendRepository, BookService bookService,
            BookCopyService bookCopyService, UserService readerService, AllMappers mapper) {
        this.lendRepository = lendRepository;
        this.bookService = bookService;
        this.bookCopyService = bookCopyService;
        this.readerService = readerService;
        this.mapper = mapper;
    }

    // This method retrieves a BookLendDTO by its ID.
    // It uses the mapper to convert the BookLendEntity to a BookLendDTO
    // and returns it. This is typically used to get the details of a specific book lend
    // in a DTO format.
    public BookLendDTO retriveBookLendDTO(long id) {
        return mapper.toBookLendDTO(retriveBookLendEntity(id));
    }


    // This method retrieves a BookLendEntity by its ID.
    // It checks if the book lend exists in the repository,
    // and if it does, it returns the BookLendEntity.
    // If the book lend is not found, it throws a BookNotFoundExceptions.
    public BookLendEntity retriveBookLendEntity(long id) {

        BookLendEntity lend = lendRepository.findById(id).orElse(null);

        if (lend == null)
            throw new BookNotFoundExceptions("Book Lend is not fOund with id = " + id);

        return lend;

    }

    // This method allows a user to lend a book by providing the user ID, book ID, and book copy ID.
    // It retrieves the book entity, book copy entity, and user entity,
    // checks if the book copy is available, updates its status to ISSUED,
    // creates a new BookLendEntity, saves it, and returns a ResponseEntity with the book lend details.
    // If the book copy is not available, it throws a BookCopyNotAvailable exception.
    public ResponseEntity<BookLendResponse> bookLendBYUser(long readerId, long bookId, long bookCopyId) {

        BookEntity book = bookService.retriveBookEntityById(bookId);
        BookCopyEntity bookCopy = bookCopyService.retriveCopyEntityById(bookCopyId);
        UserEntity reader = readerService.retriveReaderEntityId(readerId);
        

        if (bookCopy.getStatus() != BookStatus.AVAILABLE)
            throw new BookCopyNotAvailable("Book Copy is already issued");
        bookCopyService.updateBookCopyStatus(bookCopyId, BookStatus.ISSUED);

        BookLendEntity bookLendEntity = new BookLendEntity(
                reader,
                book,
                bookCopy);

        bookLendEntity = lendRepository.save(bookLendEntity);

        BookLendResponse response = new BookLendResponse(
                bookService.retriveBookDTOById(bookId),
                bookCopyService.retriveCopyDTOById(bookCopyId),
                readerService.retriveReaderDTOById(readerId).getUsername(),
                mapper.toBookLendDTO(bookLendEntity));

        return new ResponseEntity<BookLendResponse>(response, HttpStatus.CREATED);
    }

    // This method retrieves all book lend information.
    // It retrieves all BookLendEntity objects from the repository,
    // converts them to BookLendDTO objects using the mapper,
    // and returns a list of BookLendDTOs.
    public List<BookLendDTO> retriveAllLendBooks() {
        List<BookLendEntity> bookLendEntities = lendRepository.findAll();
        return mapper.toBookLendDTO(bookLendEntities);
    }

    // This method retrieves all information about a specific book lend by its ID.
    // It retrieves the BookLendEntity, the associated book, book copy, and user details,
    // and returns a BookLendResponse object containing all this information.
    // This is typically used to get detailed information about a specific book lend.
    public BookLendResponse retirveBookLendAllInfo(long id) {
        BookLendEntity bookLendEntity = retriveBookLendEntity(id);

        BookDTO book = bookService.retriveBookDTOById(
                bookLendEntity.getBook().getBookId());

        BookCopyDTO bookCopy = bookCopyService.retriveCopyDTOById(bookLendEntity.getBookCopy().getCopyId());
        UserDTO reader = readerService.retriveReaderDTOById(bookLendEntity.getUsers().getReaderId());

        return new BookLendResponse(book, bookCopy, reader.getUsername(), mapper.toBookLendDTO(bookLendEntity));

    }

    // This method updates the status of a book lend to indicate that the book has been returned.
    // It retrieves the BookLendEntity by its ID, sets the isReturned field to true,
    // updates the status of the associated book copy to AVAILABLE,
    // and saves the updated BookLendEntity in the repository.
    // This method is used to mark a book as returned.
    // It updates the book lend record in the database to reflect that the book has been returned
    public void updateReturnedBook(long id) {
        BookLendEntity bookLendEntity = retriveBookLendEntity(id);
        bookLendEntity.setIsReturned(true);
        bookCopyService.updateBookCopyStatus(bookLendEntity.getBookCopy().getCopyId(), BookStatus.AVAILABLE);
        lendRepository.save(bookLendEntity);
    }

}
