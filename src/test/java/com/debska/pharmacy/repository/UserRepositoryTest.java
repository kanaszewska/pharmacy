package com.debska.pharmacy.repository;

import com.debska.pharmacy.entity.AddressEntity;
import com.debska.pharmacy.entity.UserEntity;
import com.debska.pharmacy.enums.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;


    @Test
    void findUsersByAddressTest() {
        //given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(City.WARSAW);
        addressEntity.setStreet("Test");
        addressEntity.setZipCode("11111");
        UserEntity userEntity = new UserEntity();
        userEntity.setAddress(addressEntity);
        userEntity.setName("Test1");
        userRepository.save(userEntity);
        //when
        List<UserEntity> users = userRepository.findUsersByAddress(City.WARSAW, "Test", "11111");
        //then
        assertEquals(1, users.size());
    }


    @Test
    void findByIdTest() {
        //given
        UserEntity userEntity = new UserEntity(1, "Test", "Test1", "test@test.pl", new AddressEntity(), new ArrayList<>());
        userRepository.save(userEntity);
        //when
        Optional<UserEntity> userRepositoryById = userRepository.findById(1);
        //then
        assertTrue(userRepositoryById.isPresent());
        assertEquals("Test1", userRepositoryById.get().getSurname());
    }

    @Test
    void findAllTest() {
        //given
        UserEntity userEntity1 = new UserEntity();
        UserEntity userEntity2 = new UserEntity();
        userRepository.save(userEntity1);
        userRepository.save(userEntity2);
        //when
        List<UserEntity> userEntityList = userRepository.findAll();
        //then
        assertEquals(2, userEntityList.size());
    }
}