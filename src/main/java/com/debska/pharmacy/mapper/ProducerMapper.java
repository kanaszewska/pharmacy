package com.debska.pharmacy.mapper;

import com.debska.pharmacy.dto.ProducerDTO;
import com.debska.pharmacy.entity.ProducerEntity;

public class ProducerMapper {
    public static ProducerDTO mapProducerEntitytoDTO(ProducerEntity producerEntity) {

        ProducerDTO producerDTO = ProducerDTO.builder()
                .name(producerEntity.getName())
                .country(producerEntity.getCountry())
                .build();

        return producerDTO;
    }

    public static ProducerEntity mapProducerDTOToEntity(ProducerDTO producerDTO) {

        ProducerEntity producerEntity = ProducerEntity.builder()
                .name(producerDTO.getName())
                .country(producerDTO.getCountry())
                .build();
        return producerEntity;
    }
}
