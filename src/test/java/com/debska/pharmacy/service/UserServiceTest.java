package com.debska.pharmacy.service;

import com.debska.pharmacy.dto.AddressDTO;
import com.debska.pharmacy.dto.UserDTO;
import com.debska.pharmacy.entity.AddressEntity;
import com.debska.pharmacy.entity.UserEntity;
import com.debska.pharmacy.enums.City;
import com.debska.pharmacy.repository.AddressRepository;
import com.debska.pharmacy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void createUserTest() {
        //given
        UserDTO userDTO = new UserDTO("Test", "Test", "test@test.pl", new AddressDTO());
        //when
        UserDTO resultUser = userService.createUser(userDTO);
        //then
        assertEquals("Test", resultUser.getName());
        assertEquals("test@test.pl", resultUser.getEmail());
    }

    @Test
    void getAllUsersWithNotEmptyTest() {
        //given
        List<UserEntity> userEntityList = List.of(new UserEntity(), new UserEntity());
        when(userService.getAllUsers()).thenReturn(userEntityList);
        //when
        List<UserEntity> resultAllUsers = userService.getAllUsers();
        //then
        assertEquals(2, resultAllUsers.size());
    }



    @Test
    void getUserByAddressTest() {
        //given
        AddressDTO addressDTO = new AddressDTO(City.WARSAW, "Test", "11111");
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(City.WARSAW);
        addressEntity.setStreet("Test");
        addressEntity.setZipCode("11111");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setAddress(addressEntity);
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setAddress(addressEntity);
        List<UserEntity> userEntityList = List.of(userEntity1, userEntity2);

        when(userRepository.findUsersByAddress(
                addressEntity.getCity(),
                addressEntity.getStreet(),
                addressEntity.getZipCode()))
                .thenReturn(userEntityList);
        //when
        List<UserDTO> resultUserByAddress = userService.getUserByAddress(addressDTO);
        //then
        assertEquals(2, resultUserByAddress.size());
    }
}