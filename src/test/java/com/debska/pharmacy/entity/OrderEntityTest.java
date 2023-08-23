package com.debska.pharmacy.entity;

import com.debska.pharmacy.enums.Status;
import com.debska.pharmacy.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderEntityTest {

    private static final int id = 1;
    private static final BigDecimal wholePrice = new BigDecimal(10);

    private static final List<DrugEntity> drugEntityList = new ArrayList<>();

    @ParameterizedTest
    @CsvSource({"CREATED,WAITING", "WAITING,PAYED", "PAYED,SEND", "SEND,SEND"})
    void changeToTheNextStatusWhichIsOKTest(Status inputStatus, Status expectedStatus) {
        //given
        OrderEntity orderEntity = new OrderEntity(id, inputStatus, wholePrice, drugEntityList, new UserEntity());
        //when
        orderEntity.changeToTheNextStatus();
        //then
        assertEquals(expectedStatus, orderEntity.getStatus());
    }


    @Test
    void changeToTheNextStatusWhichIsNotOKTest() {
        //given
        Status wrongStatusMessage = Status.DEFAULT;
        OrderEntity orderEntity = new OrderEntity(id, wrongStatusMessage, wholePrice, drugEntityList, new UserEntity());
        //when
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            orderEntity.changeToTheNextStatus();
        });
        //then
        assertEquals("Status error!", notFoundException.getMessage());
    }
}