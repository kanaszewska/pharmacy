package com.debska.pharmacy.mapper;

import com.debska.pharmacy.dto.AddressDTO;
import com.debska.pharmacy.entity.AddressEntity;

public class AddressMapper {

    public static AddressDTO mapAddressEntitytoDTO(AddressEntity addressEntity) {
        AddressDTO addressDTO = AddressDTO.builder()
                .city(addressEntity.getCity())
                .street(addressEntity.getStreet())
                .zipCode(addressEntity.getZipCode())
                .build();

        return addressDTO;
    }

    public static AddressEntity mapAddressDTOtoEntity(AddressDTO addressDTO) {
        AddressEntity addressEntity = AddressEntity.builder()
                .city(addressDTO.getCity())
                .street(addressDTO.getStreet())
                .zipCode(addressDTO.getZipCode())
                .build();

        return addressEntity;
    }

}
