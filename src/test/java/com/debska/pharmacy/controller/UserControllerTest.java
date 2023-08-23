package com.debska.pharmacy.controller;

import com.debska.pharmacy.dto.AddressDTO;
import com.debska.pharmacy.dto.UserDTO;
import com.debska.pharmacy.entity.UserEntity;
import com.debska.pharmacy.enums.City;
import com.debska.pharmacy.repository.UserRepository;
import com.debska.pharmacy.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void postUserTest() {
        //given
        UserDTO respUserDTO = new UserDTO();
        respUserDTO.setEmail("test@test.pl");
        respUserDTO.setName("Test");
        when(userService.createUser(any(UserDTO.class))).thenReturn(respUserDTO);
        //when
        ResponseEntity<UserDTO> responseEntity = userController.postUser(respUserDTO);
        //then
        verify(userService, times(1)).createUser(respUserDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("test@test.pl", responseEntity.getBody().getEmail());
        assertEquals("Test", responseEntity.getBody().getName());

    }

    @Test
    void getAllUsersStatusIsOKTest() {
        //given
        List<UserEntity> usersEntityList = List.of(new UserEntity(), new UserEntity());
        when(userService.getAllUsers()).thenReturn(usersEntityList);
        //when
        ResponseEntity<List<UserEntity>> responseEntity = userController.getAllUsers();
        //then
        verify(userService, times(1)).getAllUsers();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(usersEntityList.size(), responseEntity.getBody().size());
    }

    @Test
    void getAllUsersStatusIsNotOKTest() {
        //given
        List<UserEntity> userEntityList = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn(userEntityList);
        //when
        ResponseEntity<List<UserEntity>> responseEntity = userController.getAllUsers();
        //then
        verify(userService, times(1)).getAllUsers();
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void getAllUsersByAddressTest() {
        //given
        AddressDTO addressDTO = new AddressDTO(City.WARSAW, "Test", "11111");
        List<UserDTO> userDTOList = List.of(new UserDTO(), new UserDTO());
        when(userService.getUserByAddress(eq(addressDTO))).thenReturn(userDTOList);
        //when
        ResponseEntity<List<UserDTO>> responseEntity = userController.getAllUsersByAddress(addressDTO);
        //then
        verify(userService, times(1)).getUserByAddress(addressDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDTOList.size(), responseEntity.getBody().size());
    }
}