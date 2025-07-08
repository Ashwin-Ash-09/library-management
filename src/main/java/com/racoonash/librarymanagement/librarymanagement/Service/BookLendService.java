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

    public BookLendDTO retriveBookLendDTO(long id) {
        return mapper.toBookLendDTO(retriveBookLendEntity(id));
    }

    public BookLendEntity retriveBookLendEntity(long id) {

        BookLendEntity lend = lendRepository.findById(id).orElse(null);

        if (lend == null)
            throw new BookNotFoundExceptions("Book Lend is not fOund with id = " + id);

        return lend;

    }

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

    public List<BookLendDTO> retriveAllLendBooks() {
        List<BookLendEntity> bookLendEntities = lendRepository.findAll();
        return mapper.toBookLendDTO(bookLendEntities);
    }

    public BookLendResponse retirveBookLendAllInfo(long id) {
        BookLendEntity bookLendEntity = retriveBookLendEntity(id);

        BookDTO book = bookService.retriveBookDTOById(
                bookLendEntity.getBook().getBookId());

        BookCopyDTO bookCopy = bookCopyService.retriveCopyDTOById(bookLendEntity.getBookCopy().getCopyId());
        UserDTO reader = readerService.retriveReaderDTOById(bookLendEntity.getUsers().getReaderId());

        return new BookLendResponse(book, bookCopy, reader.getUsername(), mapper.toBookLendDTO(bookLendEntity));

    }

    public void updateReturnedBook(long id) {
        BookLendEntity bookLendEntity = retriveBookLendEntity(id);
        bookLendEntity.setIsReturned(true);
        bookCopyService.updateBookCopyStatus(bookLendEntity.getBookCopy().getCopyId(), BookStatus.AVAILABLE);
        lendRepository.save(bookLendEntity);
    }

}
