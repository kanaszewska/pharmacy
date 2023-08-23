package com.debska.pharmacy.repository;

import com.debska.pharmacy.entity.DrugEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.*;


@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class DrugRepositoryTest {

    @Autowired
    private DrugRepository drugRepository;

    @Test
    void findDrugByNameTest() {
        //given
        DrugEntity drugEntity = new DrugEntity();
        drugEntity.setName("Apap");
        drugRepository.save(drugEntity);
        //when
        Optional<DrugEntity> drugByName = drugRepository.findDrugByName(drugEntity.getName());
        //then
        assertTrue(drugByName.isPresent());
        assertEquals("Apap", drugByName.get().getName());
    }

    @Test
    void findAllTest() {
        //given
        DrugEntity drugEntity = new DrugEntity();
        drugEntity.setName("Apap");
        drugRepository.save(drugEntity);
        //when
        List<DrugEntity> drugRepositoryAll = drugRepository.findAll();
        //then
        assertFalse(drugRepositoryAll.isEmpty());
        assertEquals("Apap", drugRepositoryAll.get(0).getName());
    }

    @Test
    void findDrugsByIdInTest() {
        //given
        DrugEntity drugEntity = new DrugEntity();
        drugEntity.setId(1);
        drugEntity.setName("Apap");
        List<Integer> integerList = List.of(1);
        drugRepository.save(drugEntity);
        //when
        List<DrugEntity> drugsByIdIn = drugRepository.findDrugsByIdIn(integerList);
        //then
//        assertEquals("Apap", drugsByIdIn.get(0).getName());
        assertFalse(drugsByIdIn.isEmpty());
    }
}
