package com.debska.pharmacy.service;

import com.debska.pharmacy.dto.ProducerDTO;
import com.debska.pharmacy.entity.ProducerEntity;
import com.debska.pharmacy.exceptions.ElementExistsExceptions;
import com.debska.pharmacy.exceptions.NotFoundException;
import com.debska.pharmacy.mapper.ProducerMapper;
import com.debska.pharmacy.repository.ProducerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.debska.pharmacy.enums.Country.BELGIUM;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProducerServiceTest {

    private static final int id = 1;

    @InjectMocks
    private ProducerService producerService;

    @Mock
    private ProducerRepository producerRepository;

    @Test
    void postProducerWitchIsNotPresentTest() {
        //given
        ProducerDTO producerDTOdata = new ProducerDTO("Adamed", BELGIUM);
        //when
        ProducerDTO producerDTOResult = producerService.postProducer(producerDTOdata);
        //then
        assertEquals(producerDTOdata, producerDTOResult);
    }

    @Test
    void postProducerWitchIsPresentTest() {
        //given
        ProducerDTO producerDTOdata = new ProducerDTO();
        ProducerEntity producerEntity = ProducerMapper.mapProducerDTOToEntity(producerDTOdata);
        //when
        when(producerRepository.findByName(producerDTOdata.getName())).thenReturn(Optional.of(producerEntity));
        ElementExistsExceptions elementExistsExceptions = assertThrows(ElementExistsExceptions.class, () -> {
            producerService.postProducer(producerDTOdata);
        });
        //then
        assertEquals("This producer has been added", elementExistsExceptions.getMessage());
    }

    @Test
    void getAllProducersWithNotEmptyListTest() {
        //given
        List<ProducerEntity> allProducers = List.of(new ProducerEntity(), new ProducerEntity());
        when(producerRepository.findAll()).thenReturn(allProducers);
        //when
        List<ProducerDTO> allProducersResult = producerService.getAllProducers();
        //then
        assertEquals(2, allProducersResult.size());
    }

    @Test
    void getAllProducersWithEmptyListTest() {
        //given
        //when
        List<ProducerDTO> allProducersResult = producerService.getAllProducers();
        //then
        assertEquals(Collections.emptyList(), allProducersResult);
    }

    @Test
    void getProducerByIdWhichIsPresentTest() {
        //given
        ProducerDTO producerDTO = new ProducerDTO("Adamed", BELGIUM);
        when(producerRepository.findById(id)).thenReturn(Optional.of(ProducerMapper.mapProducerDTOToEntity(producerDTO)));
        //when
        ProducerDTO producerByIdResult = producerService.getProducerById(id);
        //then
        assertEquals("Adamed", producerByIdResult.getName());
        assertEquals(BELGIUM, producerByIdResult.getCountry());
    }

    @Test
    void getProducerByIdWhichIsNotPresentTest() {
        //when-then
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            producerService.getProducerById(id);
        });
        assertEquals("The producer is not found", notFoundException.getMessage());
    }

    @Test
    void deleteProducerByIdWhichIsPresentTest() {
        //given
        ProducerDTO producerDTO = new ProducerDTO("Adamed", BELGIUM);
        ProducerEntity producerEntity = ProducerMapper.mapProducerDTOToEntity(producerDTO);
        when(producerRepository.findById(id)).thenReturn(Optional.of(producerEntity));
        //when
        String messageResult = producerService.deleteProducerById(id);
        //then
        assertEquals("You have removed the producer Adamed", messageResult);
    }

    @Test
    void deleteProducerByIdWhichIsNotPresentTest() {
        //when-then
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            producerService.deleteProducerById(id);
        });
        assertEquals("The producer is not found", notFoundException.getMessage());
    }
}