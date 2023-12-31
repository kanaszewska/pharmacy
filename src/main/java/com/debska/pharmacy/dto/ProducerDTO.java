package com.debska.pharmacy.dto;

import com.debska.pharmacy.enums.Country;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerDTO {

    @NotEmpty(message = "Name is mandatory")
    private String name;
    private Country country;

}