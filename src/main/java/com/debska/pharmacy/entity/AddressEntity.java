package com.debska.pharmacy.entity;

import com.debska.pharmacy.enums.City;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    private City city;
    private String street;
    private String zipCode;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    @JsonIgnore
    private UserEntity user;

}
