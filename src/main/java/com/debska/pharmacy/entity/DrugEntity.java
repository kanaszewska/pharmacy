package com.debska.pharmacy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "drugEntity")
public class DrugEntity {

    @GeneratedValue
    @Id
    private int id;
    private String name;
    private int quantityOfTablets;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "producer_id")
    private ProducerEntity producerEntity;

    @ManyToMany
    @JoinTable(
            name = "ordered_drugs",
            joinColumns = @JoinColumn(name = "drugEntity_id"),
            inverseJoinColumns = @JoinColumn(name = "orderEntity_id")
    )
    @JsonBackReference
    private List<OrderEntity> orders = new ArrayList<>();

}
