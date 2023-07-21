package com.debska.pharmacy.entity;

import com.debska.pharmacy.example.Country;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producer")
public class ProducerEntity {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Country country;

}

