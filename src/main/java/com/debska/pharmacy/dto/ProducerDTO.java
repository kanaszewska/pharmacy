package com.debska.pharmacy.dto;

import com.debska.pharmacy.example.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerDTO {
    private int id;
    private String name;
    private Country country;

}