package com.debska.pharmacy.validators;

import com.debska.pharmacy.entity.DrugEntity;
import com.debska.pharmacy.exceptions.NotFoundException;

import java.util.List;

public class OrderValidator {

    public static void validateSizeOfLists(List<DrugEntity> drugsListByIdIn, List<Integer> listOfDrugsId) {
        if (drugsListByIdIn.size() != listOfDrugsId.size()) {
            throw new NotFoundException("Wrong order!");
        }
    }
}
