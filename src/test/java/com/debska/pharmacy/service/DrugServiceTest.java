package com.debska.pharmacy.service;

import com.debska.pharmacy.dto.DrugDTO;
import com.debska.pharmacy.dto.ProducerDTO;
import com.debska.pharmacy.entity.DrugEntity;
import com.debska.pharmacy.entity.OrderEntity;
import com.debska.pharmacy.entity.ProducerEntity;
import com.debska.pharmacy.exceptions.ElementExistsExceptions;
import com.debska.pharmacy.exceptions.NotFoundException;
import com.debska.pharmacy.mapper.DrugMapper;
import com.debska.pharmacy.mapper.ProducerMapper;
import com.debska.pharmacy.repository.DrugRepository;
import com.debska.pharmacy.repository.ProducerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

import static com.debska.pharmacy.enums.Country.BELGIUM;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class DrugServiceTest {

    private static final int id = 1;
    private static final BigDecimal price = new BigDecimal(10.50);
    @InjectMocks
    private DrugService drugService;
    @Mock
    private DrugRepository drugRepository;
    @Mock
    private ProducerRepository producerRepository;

    @Test
    void createDrugWhichIsPresentTest() {
        //given
        DrugDTO drugDTOdata = new DrugDTO("Apap", 10, price, new ProducerDTO());

        ProducerEntity producer = new ProducerEntity(id, "Adamed", BELGIUM);
        ;
        when(producerRepository.findByName(drugDTOdata.getProducerDTO().getName())).thenReturn(Optional.of(producer));

        DrugEntity drugEntity = new DrugEntity();
        when(drugRepository.findDrugByName(drugDTOdata.getName())).thenReturn(Optional.of(drugEntity));
        //when
        ElementExistsExceptions elementExistsExceptions = assertThrows(ElementExistsExceptions.class, () -> {
            drugService.createDrug(drugDTOdata);
        });
        //then
        assertEquals("This drug exists", elementExistsExceptions.getMessage());
    }

    @Test
    void saveNewProducerTest() {
        //given
        DrugDTO drugDTOdata = new DrugDTO("Apap", 10, price, new ProducerDTO());

        Optional<ProducerEntity> producerEntity = Optional.empty();
        when(producerRepository.findByName(drugDTOdata.getProducerDTO().getName())).thenReturn(producerEntity);

        DrugEntity drugEntity = new DrugEntity();
        when(drugRepository.findDrugByName(drugDTOdata.getName())).thenReturn(Optional.of(drugEntity));
        //when
        ElementExistsExceptions elementExistsExceptions = assertThrows(ElementExistsExceptions.class, () -> {
            drugService.createDrug(drugDTOdata);
        });
        //then
        assertEquals("This drug exists", elementExistsExceptions.getMessage());
    }

    @Test
    void createDrugWhichIsNotPresentTest() {
        //given
        DrugDTO drugDTOdata = new DrugDTO("Apap", 10, price, new ProducerDTO());

        ProducerEntity producer = new ProducerEntity();
        when(producerRepository.findByName(drugDTOdata.getProducerDTO().getName())).thenReturn(Optional.of(producer));

        DrugEntity drugEntity = DrugMapper.mapDrugDTOToEntity(drugDTOdata, producer);
        when(drugRepository.save(drugEntity)).thenReturn(drugEntity);
        //when
        String messageResult = drugService.createDrug(drugDTOdata);
        //then
        assertEquals("You have added a new drug Apap 10 tablets.", messageResult);
    }

    @Test
    void getAllDrugsWitchEmptyListTest() {
        //given
        //when
        List<DrugDTO> allDrugsResult = drugService.getAllDrugs();
        //then
        assertEquals(Collections.emptyList(), allDrugsResult);
    }

    @Test
    void getAllDrugsWitchNotEmptyListTest() {
        //given
        List<OrderEntity> orderEntityList = new ArrayList<>();
        DrugEntity drugEntity = new DrugEntity(id, "Apap", 10, price, new ProducerEntity(), orderEntityList);

        List<DrugEntity> drugEntityList = List.of(drugEntity);
        when(drugRepository.findAll()).thenReturn(drugEntityList);
        //when
        List<DrugDTO> allDrugsResult = drugService.getAllDrugs();
        //then
        assertEquals(1, allDrugsResult.size());
    }

    @Test
    void getDrugByIdWhichIsPresentTest() {
        //given
        ProducerDTO producerDTO = new ProducerDTO();
        DrugDTO drugDTO = new DrugDTO("Apap", 10, price, producerDTO);
        DrugEntity drugEntity = DrugMapper.mapDrugDTOToEntity(drugDTO, ProducerMapper.mapProducerDTOToEntity(producerDTO));

        when(drugRepository.findById(id)).thenReturn(Optional.of(drugEntity));
        //when
        DrugDTO resultDrugById = drugService.getDrugById(id);
        //then
        assertEquals("Apap", resultDrugById.getName());
        assertEquals(10, resultDrugById.getQuantityOfTablets());
        assertEquals(new BigDecimal(10.5), resultDrugById.getPrice());
    }

    @Test
    void getDrugByIdWhichIsNotPresentTest() {
        //given
        //when
        //then
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            drugService.getDrugById(id);
        });
        assertEquals("The drug does not found!", notFoundException.getMessage());
    }

    @Test
    void deleteDrugByIdWhichIsNotPresentTest() {
        //given
        //when
        //then
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            drugService.deleteDrugById(id);
        });
        assertEquals("The drug does not found!", notFoundException.getMessage());
    }

    @Test
    void deleteDrugByIdWhichIsPresentTest() {
        //given
        DrugEntity drug = new DrugEntity();
        drug.setName("Apap");
        when(drugRepository.findById(id)).thenReturn(Optional.of(drug));
        //when
        String removalResult = drugService.deleteDrugById(id);
        //then
        assertEquals("You have removed the drug Apap", removalResult);
    }

    @Test
    void updateDrugByIdTest() {
        //given
        ProducerDTO producerDTO = new ProducerDTO();
        DrugDTO drugDTOdata = new DrugDTO("Apap", 10, price, producerDTO);

        DrugDTO drugDTO = new DrugDTO("Apap", 10, price, producerDTO);
        DrugEntity drugEntity = DrugMapper.mapDrugDTOToEntity(drugDTO, ProducerMapper.mapProducerDTOToEntity(producerDTO));
        when(drugRepository.findById(id)).thenReturn(Optional.of(drugEntity));
        //when
        DrugDTO drugUpdatedResolve = drugService.updateDrugById(id, drugDTOdata);
        //then
        assertEquals("Apap", drugUpdatedResolve.getName());
        assertEquals(10, drugUpdatedResolve.getQuantityOfTablets());
        assertEquals(new BigDecimal(10.5), drugUpdatedResolve.getPrice());
    }

    @Test
    void updateDrugByIdWhichIsNotPresentTest() {
        //given
        DrugDTO drugDTOdata = new DrugDTO();
        //when
        //then
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            drugService.updateDrugById(id, drugDTOdata);
        });
        assertEquals("The drug does not found!", notFoundException.getMessage());
    }
}