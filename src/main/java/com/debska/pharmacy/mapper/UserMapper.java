package com.debska.pharmacy.mapper;

import com.debska.pharmacy.dto.UserDTO;
import com.debska.pharmacy.entity.UserEntity;

public class UserMapper {

    public static UserDTO mapUserEntityToUserDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .address(AddressMapper.mapAddressEntityToDTO(userEntity.getAddress()))
                .build();

    }

}