package com.debska.pharmacy.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugDTO {

    @NotEmpty(message = "Name is mandatory")
    private String name;

    private int quantityOfTablets;

    private BigDecimal price;

    private ProducerDTO producerDTO;

}