package com.debska.pharmacy.mapper;

import com.debska.pharmacy.dto.OrderDTO;
import com.debska.pharmacy.entity.OrderEntity;

public class OrderMapper {

    public static OrderDTO mapOrderEntityToDTO(OrderEntity orderEntity){
        OrderDTO orderDTO = OrderDTO.builder()
                .id(orderEntity.getId())
                .status(orderEntity.getStatus())
                .listOfDrugs(orderEntity.getListOfDrugs())
                .wholePrice(orderEntity.getWholePrice())
                .orderedDrugs(orderEntity.getOrderedDrugs())
                .build();

        return orderDTO;
    }

    public static OrderEntity mapOrderDTOToEntity(OrderDTO orderDTO){

        OrderEntity orderEntity = OrderEntity.builder()
                .id(orderDTO.getId())
                .status(orderDTO.getStatus())
                .listOfDrugs(orderDTO.getListOfDrugs())
                .wholePrice(orderDTO.getWholePrice())
                .orderedDrugs(orderDTO.getOrderedDrugs())
                .build();

        return orderEntity;
    }
}
