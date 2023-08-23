package com.debska.pharmacy.mapper;

import com.debska.pharmacy.dto.DrugDTO;
import com.debska.pharmacy.entity.DrugEntity;
import com.debska.pharmacy.entity.ProducerEntity;


public class DrugMapper {

    public static DrugDTO mapDrugEntityToDTO(DrugEntity drugEntity) {

        return DrugDTO.builder()
                .name(drugEntity.getName())
                .quantityOfTablets(drugEntity.getQuantityOfTablets())
                .price(drugEntity.getPrice())
                .producerDTO(ProducerMapper.mapProducerEntitytoDTO(drugEntity.getProducerEntity()))
                .build();

    }

    public static DrugEntity mapDrugDTOToEntity(DrugDTO drugDTO, ProducerEntity producerEntity) {

        return DrugEntity.builder()
                .name(drugDTO.getName())
                .quantityOfTablets(drugDTO.getQuantityOfTablets())
                .price(drugDTO.getPrice())
                .producerEntity(producerEntity)
                .build();

    }

}
