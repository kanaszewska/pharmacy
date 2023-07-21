package com.debska.pharmacy.dto;

import com.debska.pharmacy.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugDTO {

    private int id;
    private String name;
    private int quantityOfTablets;
    private BigDecimal price;
    private ProducerDTO producerDTO;
    private Set<OrderEntity> orders;

}