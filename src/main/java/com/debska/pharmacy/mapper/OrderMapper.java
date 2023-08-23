package com.debska.pharmacy.mapper;

import com.debska.pharmacy.dto.respDTO.RespOrderDTO;
import com.debska.pharmacy.entity.OrderEntity;

import java.util.stream.Collectors;

public class OrderMapper {

    public static RespOrderDTO mapOrderEntityToDTO(OrderEntity orderEntity) {
        RespOrderDTO respOrderDTO = RespOrderDTO.builder()
                .status(orderEntity.getStatus())
                .wholePrice(orderEntity.getWholePrice())
                .orderedDrugsDTO(orderEntity.getOrderedDrugsEntity().stream()
                        .map(drugEntity -> DrugMapper.mapDrugEntityToDTO(drugEntity))
                        .collect(Collectors.toList()))
                .user(UserMapper.mapUserEntityToUserDTO(orderEntity.getUser()))
                .build();

        return respOrderDTO;
    }

}
