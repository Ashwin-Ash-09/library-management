package com.racoonash.librarymanagement.librarymanagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.racoonash.librarymanagement.librarymanagement.Entity.BookEntity;

// This interface extends JpaRepository to provide CRUD operations for BookEntity.
@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long> {

    List<BookEntity> findByBookNameContaining(String name);

    List<BookEntity> findByIsbn(String isbn);
    
}