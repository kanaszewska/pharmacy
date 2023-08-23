package com.debska.pharmacy.mapper;

import com.debska.pharmacy.dto.UserDTO;
import com.debska.pharmacy.entity.UserEntity;

public class UserMapper {

    public static UserDTO mapUserEntityToUserDTO(UserEntity userEntity) {
        UserDTO userDTO = UserDTO.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .address(AddressMapper.mapAddressEntitytoDTO(userEntity.getAddress()))
                .build();

        return userDTO;
    }

}