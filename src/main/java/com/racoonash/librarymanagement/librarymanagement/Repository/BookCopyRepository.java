package com.racoonash.librarymanagement.librarymanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.racoonash.librarymanagement.librarymanagement.Entity.BookCopyEntity;
import java.util.List;


@Repository
public interface BookCopyRepository extends JpaRepository<BookCopyEntity,Long> {
    
    public List<BookCopyEntity> findAllByBookBookId(long id);
}