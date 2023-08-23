package com.debska.pharmacy.service;

import com.debska.pharmacy.dto.AddressDTO;
import com.debska.pharmacy.dto.UserDTO;
import com.debska.pharmacy.entity.UserEntity;
import com.debska.pharmacy.mapper.AddressMapper;
import com.debska.pharmacy.mapper.UserMapper;
import com.debska.pharmacy.repository.AddressRepository;
import com.debska.pharmacy.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService {
    //todo change to constructor injection. Do the same with the rest

    private UserRepository userRepository;
    private AddressRepository addressRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public UserDTO createUser(UserDTO userDTOdata) {

        UserEntity userEntity = saveNewUser(userDTOdata);
        UserDTO userDTO = UserMapper.mapUserEntityToUserDTO(userEntity);

        return userDTO;
    }

    private UserEntity saveNewUser(UserDTO userDTOdata) {
        UserEntity userEntity = UserEntity.builder()
                .name(userDTOdata.getName())
                .surname(userDTOdata.getSurname())
                .email(userDTOdata.getEmail())
                .address(AddressMapper.mapAddressDTOtoEntity(userDTOdata.getAddress()))
                .build();
        addressRepository.save(userEntity.getAddress());
        userRepository.save(userEntity);


        return userEntity;
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> userRepositoryAll = userRepository.findAll();
        return userRepositoryAll;
    }

    public List<UserDTO> getUserByAddress(AddressDTO addressDTO) {
        List<UserEntity> userEntityList = userRepository.findUsersByAddress(
                addressDTO.getCity(),
                addressDTO.getStreet(),
                addressDTO.getZipCode()
        );

        List<UserDTO> userDTOList = userEntityList.stream()
                .map(userEntity -> UserMapper.mapUserEntityToUserDTO(userEntity))
                .collect(Collectors.toList());

        return userDTOList;
    }

}
