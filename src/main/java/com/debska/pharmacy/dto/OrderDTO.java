package com.debska.pharmacy.dto;

import com.debska.pharmacy.entity.DrugEntity;
import com.debska.pharmacy.example.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private int id;
    private Status status;
    private List<DrugEntity> listOfDrugs;
    private BigDecimal wholePrice;
    private Set<DrugEntity> orderedDrugs;
}
