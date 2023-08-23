package com.debska.pharmacy.repository;

import com.debska.pharmacy.entity.ProducerEntity;
import com.debska.pharmacy.enums.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProducerRepositoryTest {

    @Autowired
    private ProducerRepository producerRepository;

    @Test
    void findByNameTest() {
        //given
        ProducerEntity producer = new ProducerEntity(1, "Adamed", Country.POLAND);
        producerRepository.save(producer);
        //when
        Optional<ProducerEntity> producerRepositoryByName = producerRepository.findByName(producer.getName());
        //then
        assertEquals("Adamed", producerRepositoryByName.get().getName());
        assertEquals(1, producerRepositoryByName.get().getId());
        assertEquals(Country.POLAND, producerRepositoryByName.get().getCountry());
    }
}