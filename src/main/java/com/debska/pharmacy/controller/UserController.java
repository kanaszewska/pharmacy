package com.debska.pharmacy.controller;


import com.debska.pharmacy.dto.AddressDTO;
import com.debska.pharmacy.dto.UserDTO;
import com.debska.pharmacy.entity.UserEntity;
import com.debska.pharmacy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> postUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO postResult = userService.createUser(userDTO);
        return new ResponseEntity<>(postResult, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> userList = userService.getAllUsers();

        if (userList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/all-by-address")
    public ResponseEntity<List<UserDTO>> getAllUsersByAddress(@RequestBody AddressDTO addressDTO) {
        List<UserDTO> usersByAddress = userService.getUserByAddress(addressDTO);

        return new ResponseEntity<>(usersByAddress, HttpStatus.OK);
    }


}