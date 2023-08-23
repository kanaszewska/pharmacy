package com.debska.pharmacy.dto;

import com.debska.pharmacy.enums.City;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private City city;

    private String street;

    private String zipCode;

}
