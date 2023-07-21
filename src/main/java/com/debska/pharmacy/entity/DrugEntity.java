package com.debska.pharmacy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
//students
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
    private Set<OrderEntity> orders;


    //todo
    // 1. Add entity Order ( id, Status, List of Drugs, WholePrice) -- DONE!!
    // 2. Create Enum Status (Created, Waiting, Payed, Send) -- DONE!!
    // 3 Create endpoint which accept list of drugs and Create an Order
    // 4. Create CRUD endpoint of Order
    // 5. Create an endpoint which gives you possibility to move status into waiting (You need to include validation of the status)
}
