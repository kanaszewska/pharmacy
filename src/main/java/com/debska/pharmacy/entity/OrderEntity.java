package com.debska.pharmacy.entity;

import com.debska.pharmacy.enums.Status;
import com.debska.pharmacy.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Entity
@Table(name = "orderEntity")
public class OrderEntity {

    @GeneratedValue
    @Id
    private int id;
    private Status status;
    private BigDecimal wholePrice;

    @ManyToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DrugEntity> orderedDrugsEntity = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private Status changeStatus(Status status) {

        switch (status) {
            case CREATED:
                return Status.WAITING;
            case WAITING:
                return Status.PAYED;
            case PAYED, SEND:
                return Status.SEND;
            default:
                throw new NotFoundException("Status error!");
        }
    }

    public void changeToTheNextStatus() {
        this.setStatus(changeStatus(this.getStatus()));
    }

}