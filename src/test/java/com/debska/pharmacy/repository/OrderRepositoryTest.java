package com.debska.pharmacy.repository;

import com.debska.pharmacy.entity.OrderEntity;
import com.debska.pharmacy.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void findAllTest() {
        //given
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        orderEntity.setStatus(Status.WAITING);
        orderRepository.save(orderEntity);
        //when
        List<OrderEntity> orderRepositoryAll = orderRepository.findAll();
        //then
        assertFalse(orderRepositoryAll.isEmpty());
        assertEquals(Status.WAITING, orderRepositoryAll.get(0).getStatus());
        assertEquals(1, orderRepositoryAll.get(0).getId());
    }
}