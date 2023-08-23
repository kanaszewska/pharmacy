package com.debska.pharmacy.validators;

import com.debska.pharmacy.entity.DrugEntity;
import com.debska.pharmacy.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {

    @Autowired
    private OrderValidator orderValidator;

    @Test
    void validateSizeOfListsTest() {
        //given
        List<DrugEntity> drugsListByIdIn = List.of(new DrugEntity(), new DrugEntity());
        List<Integer> listOfDrugsId = List.of(1);
        //when
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            OrderValidator.validateSizeOfLists(drugsListByIdIn, listOfDrugsId);
        });
        //then
        assertEquals("Wrong order!", notFoundException.getMessage());
    }
}