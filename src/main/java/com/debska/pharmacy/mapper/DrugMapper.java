package com.debska.pharmacy.mapper;

import com.debska.pharmacy.dto.DrugDTO;
import com.debska.pharmacy.entity.DrugEntity;
import com.debska.pharmacy.entity.ProducerEntity;

public class DrugMapper {

    public static DrugDTO mapDrugEntityToDTO(DrugEntity drugEntity){

        DrugDTO drugDTO = DrugDTO.builder()
                .id(drugEntity.getId())
                .name(drugEntity.getName())
                .quantityOfTablets(drugEntity.getQuantityOfTablets())
                .price(drugEntity.getPrice())
                .producerDTO(ProducerMapper.mapProducerEntitytoDTO(drugEntity.getProducerEntity()))
                .orders(drugEntity.getOrders())
                .build();

        return drugDTO;
    }

    public static DrugEntity mapDrugDTOToEntity(DrugDTO drugDTO, ProducerEntity producerEntity){

        DrugEntity drugEntity = DrugEntity.builder()
                .id(drugDTO.getId())
                .name(drugDTO.getName())
                .quantityOfTablets(drugDTO.getQuantityOfTablets())
                .price(drugDTO.getPrice())
                .producerEntity(producerEntity)
                .orders(drugDTO.getOrders())
                .build();

        return drugEntity;
    }
}
