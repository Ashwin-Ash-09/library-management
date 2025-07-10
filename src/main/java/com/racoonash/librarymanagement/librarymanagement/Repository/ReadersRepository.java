package com.racoonash.librarymanagement.librarymanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.racoonash.librarymanagement.librarymanagement.Entity.UserEntity;;

// This interface extends JpaRepository to provide CRUD operations for UserEntity.
@Repository
public interface ReadersRepository extends JpaRepository<UserEntity,Long>{

    UserEntity findByUsername(String username);
    
}
