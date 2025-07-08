package com.racoonash.librarymanagement.librarymanagement.Service;

import java.util.List;

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

    public UserDTO retriveReaderDTOById(long id) throws UserNotFoundException {

        return mapper.toReadersDTO(retriveReaderEntityId(id));
    }

    public UserEntity retriveReaderEntityId(long id) throws UserNotFoundException {
        UserEntity user = readersRepository.findById(id).orElse(null);

        if (user == null)
            throw new UserNotFoundException("User Not Found");

        return user;
    }

    public List<UserEntity> findAllUsers() {
        return readersRepository.findAll();
    }

    public UserDTO createUser(UserDTO user) {
        UserEntity reader = mapper.toReadersEntity(user);
        user = mapper.toReadersDTO(readersRepository.save(reader));
        return user;
    }

    public void deleteReaderByID(long id) {
        retriveReaderDTOById(id);
        readersRepository.deleteById(id);
    }

    public UserDTO updateReaderByID(long id, UserDTO readersDTO) {

        UserEntity reader = retriveReaderEntityId(id);

        mapper.updateEntityFromDTO(readersDTO, reader);
        reader = readersRepository.save(reader);

        return mapper.toReadersDTO(reader);
    }

    public UserEntity retriveReaderEntityByUsername(String username) {
        return readersRepository.findByUsername(username);
    }

}
