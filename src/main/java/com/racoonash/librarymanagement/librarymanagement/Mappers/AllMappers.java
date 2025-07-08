package com.racoonash.librarymanagement.librarymanagement.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.racoonash.librarymanagement.librarymanagement.DTO.BookCopyDTO;
import com.racoonash.librarymanagement.librarymanagement.DTO.BookDTO;
import com.racoonash.librarymanagement.librarymanagement.DTO.BookLendDTO;
import com.racoonash.librarymanagement.librarymanagement.DTO.UserDTO;
import com.racoonash.librarymanagement.librarymanagement.Entity.BookCopyEntity;
import com.racoonash.librarymanagement.librarymanagement.Entity.BookEntity;
import com.racoonash.librarymanagement.librarymanagement.Entity.BookLendEntity;
import com.racoonash.librarymanagement.librarymanagement.Entity.UserEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AllMappers {

        @Mapping(target = "bookLend", ignore = true)
        @Mapping(target = "bookCopy", ignore = true)
        BookDTO toBookDTO(BookEntity bookEntity);

        @Mapping(target = "bookId", ignore = true)
        @Mapping(target = "bookLend", ignore = true)
        @Mapping(target = "bookCopy", ignore = true)
        BookEntity toBookEntity(BookDTO book);

        @Mapping(target = "bookId", ignore = true)
        public void updateEntityFromDTO(BookDTO dto, @MappingTarget BookEntity bookEntity);

        @Mapping(target = "bookLend", ignore = true)
        @Mapping(target = "bookCopy", ignore = true)
        List<BookDTO> toBookDTOList(List<BookEntity> listBook);

        // Book Copy Mapping

        @Mapping(target = "copyId", ignore = true)
        @Mapping(target = "book", ignore = true)
        @Mapping(target = "bookLend", ignore = true)
        BookCopyEntity toBookCopyEntity(BookCopyDTO bookCopy);

        @Mapping(target = "copyId", ignore = true)
        public void updateEntityFromDTO(BookCopyDTO dto, @MappingTarget BookCopyEntity bookCopyEntity);

        @Mapping(target = "book", ignore = true)
        @Mapping(target = "bookLend", ignore = true)
        BookCopyDTO toBookCopyDTO(BookCopyEntity bookEntity);

        List<BookCopyDTO> toBookCopyList(List<BookCopyEntity> listBook);

        // Readers Maping

        @Mapping(target = "bookLend", ignore = true)
        UserDTO toReadersDTO(UserEntity reader);

        @Mapping(target = "bookLend", ignore = true)
        @Mapping(target = "readerId", ignore = true)
        UserEntity toReadersEntity(UserDTO readerDto);

        @Mapping(target = "readerId", ignore = true)
        public void updateEntityFromDTO(UserDTO readersDTO, @MappingTarget UserEntity readersEntity);

        // Book Lend Mapping

        @Mapping(target = "book",ignore = true)
        BookLendDTO toBookLendDTO(BookLendEntity bookLendEntity);

        @Mapping(target = "bookLendId", ignore = true)
        BookLendEntity toBookLendEntity(BookLendDTO bookLendDTO);

        List<BookLendDTO> toBookLendDTO(List<BookLendEntity> bookLendEntities);

}
