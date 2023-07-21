package com.debska.pharmacy.entity;

import com.debska.pharmacy.example.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
//group
@Entity(name = "orderEntity")
public class OrderEntity {

    @GeneratedValue
    @Id
    private int id;
    private Status status;
    private List<DrugEntity> listOfDrugs;
    private BigDecimal wholePrice;


    @ManyToMany
//    @JoinTable(
//            name = "ordered_drugs",
//            joinColumns = @JoinColumn(name = "orderEntity_id"),
//            inverseJoinColumns = @JoinColumn(name = "drugEntity_id"))
    private Set<DrugEntity> orderedDrugs;
}