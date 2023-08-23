package com.debska.pharmacy.mapper;

import com.debska.pharmacy.dto.ProducerDTO;
import com.debska.pharmacy.entity.ProducerEntity;

public class ProducerMapper {
    public static ProducerDTO mapProducerEntitytoDTO(ProducerEntity producerEntity) {

        return ProducerDTO.builder()
                .name(producerEntity.getName())
                .country(producerEntity.getCountry())
                .build();

    }

    public static ProducerEntity mapProducerDTOToEntity(ProducerDTO producerDTO) {

        return ProducerEntity.builder()
                .name(producerDTO.getName())
                .country(producerDTO.getCountry())
                .build();
    }
}
