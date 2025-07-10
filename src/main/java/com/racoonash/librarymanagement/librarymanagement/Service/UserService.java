package com.racoonash.librarymanagement.librarymanagement.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.racoonash.librarymanagement.librarymanagement.DTO.UserDTO;
import com.racoonash.librarymanagement.librarymanagement.Entity.UserEntity;
import com.racoonash.librarymanagement.librarymanagement.Repository.ReadersRepository;

import jakarta.transaction.Transactional;

import com.racoonash.librarymanagement.librarymanagement.Exceptions.UserNotFoundException;
import com.racoonash.librarymanagement.librarymanagement.Mappers.AllMappers;

@Service
@Transactional
public class UserService {

    private ReadersRepository readersRepository;
    private AllMappers mapper;

    public UserService(ReadersRepository readersRepository, AllMappers mapper) {
        this.readersRepository = readersRepository;
        this.mapper = mapper;
    }

    // Retrieves a UserDTO by ID
    // Throws UserNotFoundException if the user does not exist
    public UserDTO retriveReaderDTOById(long id) throws UserNotFoundException {

        return mapper.toReadersDTO(retriveReaderEntityId(id));
    }

    // Retrieves a UserEntity by ID
    // Throws UserNotFoundException if the user does not exist
    public UserEntity retriveReaderEntityId(long id) throws UserNotFoundException {
        UserEntity user = readersRepository.findById(id).orElse(null);

        if (user == null)
            throw new UserNotFoundException("User Not Found");

        return user;
    }

    // Retrieves a UserEntity by username
    // Throws UserNotFoundException if the user does not exist
    public List<UserEntity> findAllUsers() {
        return readersRepository.findAll();
    }

    // Creates a new user and returns the UserDTO
    // Throws UserNotFoundException if the user already exists
    public UserDTO createUser(UserDTO user) {
        UserEntity reader = mapper.toReadersEntity(user);
        user = mapper.toReadersDTO(readersRepository.save(reader));
        return user;
    }

    // Deletes a user by ID
    // Throws UserNotFoundException if the user does not exist
    public void deleteReaderByID(long id) {
        retriveReaderDTOById(id);
        readersRepository.deleteById(id);
    }

    // Updates a user by ID and returns the updated UserDTO
    // Throws UserNotFoundException if the user does not exist
    public UserDTO updateReaderByID(long id, UserDTO readersDTO) {

        UserEntity reader = retriveReaderEntityId(id);

        mapper.updateEntityFromDTO(readersDTO, reader);
        reader = readersRepository.save(reader);

        return mapper.toReadersDTO(reader);
    }

    // Retrieves a UserEntity by username
    // Throws UserNotFoundException if the user does not exist
    public UserEntity retriveReaderEntityByUsername(String username) {
        return Optional.ofNullable(readersRepository.findByUsername(username)).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

}
