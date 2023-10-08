package com.debska.pharmacy.mapper;

import com.debska.pharmacy.dto.AddressDTO;
import com.debska.pharmacy.entity.AddressEntity;

public class AddressMapper {

    public static AddressDTO mapAddressEntityToDTO(AddressEntity addressEntity) {
        return AddressDTO.builder()
                .city(addressEntity.getCity())
                .street(addressEntity.getStreet())
                .zipCode(addressEntity.getZipCode())
                .build();
    }

    public static AddressEntity mapAddressDTOtoEntity(AddressDTO addressDTO) {
        return AddressEntity.builder()
                .city(addressDTO.getCity())
                .street(addressDTO.getStreet())
                .zipCode(addressDTO.getZipCode())
                .build();

    }

}
