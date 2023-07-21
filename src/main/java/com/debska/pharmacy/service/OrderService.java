package com.debska.pharmacy.service;

import com.debska.pharmacy.dto.OrderDTO;
import com.debska.pharmacy.entity.OrderEntity;
import com.debska.pharmacy.exceptions.NotFoundException;
import com.debska.pharmacy.mapper.OrderMapper;
import com.debska.pharmacy.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderDTO createOrder(OrderDTO orderDTOdata){
//        Optional<OrderEntity> orderById = orderRepository.findById(orderDTOdata.getId());
//
//        OrderDTO orderDTO = OrderDTO.builder()
//                .status(orderDTOdata.getStatus())
//                .listOfDrugs(orderDTOdata.getListOfDrugs())
//                .wholePrice(orderDTOdata.getWholePrice())
//                .build();
//        OrderEntity orderEntity = OrderMapper.mapOrderDTOToEntity(orderDTO);
//        orderRepository.save(orderEntity);

//        return orderDTO;
        return null;

    }
}
