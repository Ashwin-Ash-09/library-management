package com.racoonash.librarymanagement.librarymanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.racoonash.librarymanagement.librarymanagement.Entity.BookLendEntity;

// This interface extends JpaRepository to provide CRUD operations for BookLendEntity.
@Repository
public interface BookLendRepository extends JpaRepository<BookLendEntity,Long>{

}
