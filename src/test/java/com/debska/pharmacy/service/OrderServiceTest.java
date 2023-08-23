package com.debska.pharmacy.service;

import com.debska.pharmacy.dto.reqDTO.ReqOrderDTO;
import com.debska.pharmacy.dto.respDTO.RespOrderDTO;
import com.debska.pharmacy.entity.*;
import com.debska.pharmacy.enums.Status;
import com.debska.pharmacy.exceptions.NotFoundException;
import com.debska.pharmacy.repository.AddressRepository;
import com.debska.pharmacy.repository.DrugRepository;
import com.debska.pharmacy.repository.OrderRepository;
import com.debska.pharmacy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    private static final int id = 1;

    private static final BigDecimal price = new BigDecimal(10.5);

    private static final BigDecimal wholePrice = new BigDecimal(21);
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private DrugRepository drugRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;


    @Test
    void createOrderWhichIsPresentTest() {
        //given
        List<Integer> listOfDrugsId = List.of(1);
        ReqOrderDTO reqOrderDTO = new ReqOrderDTO(listOfDrugsId, id);
        List<OrderEntity> orderEntityList = new ArrayList<>();
        DrugEntity drugEntity = new DrugEntity(id, "Adamed", 10, price, new ProducerEntity(), orderEntityList);

        List<DrugEntity> drugEntityList = new ArrayList<>();
        drugEntityList.add(drugEntity);
        UserEntity userEntity = new UserEntity();
        userEntity.setAddress(new AddressEntity());
        when(drugRepository.findDrugsByIdIn(listOfDrugsId)).thenReturn(drugEntityList);
        when(userRepository.findById(reqOrderDTO.getUserId())).thenReturn(Optional.of(userEntity));
        //when
        RespOrderDTO orderResult = orderService.createOrder(reqOrderDTO);
        //then
        assertEquals(Status.CREATED, orderResult.getStatus());
        assertEquals(new BigDecimal(10.5), orderResult.getWholePrice());
    }

    @Test
    void getOrderByIdWhichIsPresentTest() {
        //given
        List<DrugEntity> listDrugs = new ArrayList<>();
        UserEntity userEntity = new UserEntity();
        userEntity.setAddress(new AddressEntity());
        OrderEntity orderEntity = new OrderEntity(id, Status.CREATED, wholePrice, listDrugs, userEntity);
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderEntity));
        //when
        RespOrderDTO orderResult = orderService.getOrderById(id);
        //then
        assertEquals(Status.CREATED, orderResult.getStatus());
        assertEquals(new BigDecimal(21), orderResult.getWholePrice());
    }

    @Test
    void getOrderByIdWhichIsNotPresentTest() {
        //given
        //when
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            orderService.getOrderById(id);
        });
        //then
        assertEquals("The order is not found", notFoundException.getMessage());
    }

    @Test
    void getAllOrdersWitchEmptyListTest() {
        //given
        //when
        List<RespOrderDTO> allOrdersResult = orderService.getAllOrders();
        //then
        assertEquals(Collections.emptyList(), allOrdersResult);
    }

    @Test
    void getAllOrdersWithNotEmptyListTest() {
        //given
        OrderEntity orderEntity = new OrderEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setAddress(new AddressEntity());
        orderEntity.setUser(userEntity);
        List<OrderEntity> orderEntityList = List.of(orderEntity);
        when(orderRepository.findAll()).thenReturn(orderEntityList);
        //when
        List<RespOrderDTO> allOrdersResult = orderService.getAllOrders();
        //then
        assertEquals(1, allOrdersResult.size());
    }

    @Test
    void deleteOrderByIdWhichIsPresentTest() {
        //given
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderEntity));
        //when
        String messageResult = orderService.deleteOrderById(id);
        //then
        assertEquals("You have removed the order 0", messageResult);
    }

    @Test
    void deleteOrderByIdWhichIsNotPresentTest() {
        //given
        //when
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            orderService.deleteOrderById(id);
        });
        //then
        assertEquals("The order does not found!", notFoundException.getMessage());
    }

    @Test
    void changeStatusOrderByIdWhichIsPresentTest() {
        //given
        List<DrugEntity> drugEntityList = new ArrayList<>();
        AddressEntity addressEntity = new AddressEntity();
        UserEntity userEntity = new UserEntity(id, "Test", "Test", "test@test.pl", addressEntity, new ArrayList<>());
        OrderEntity orderEntity = new OrderEntity(id, Status.CREATED, wholePrice, drugEntityList, userEntity);
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderEntity));
        //when
        RespOrderDTO respOrderDTOResult = orderService.changeStatusOrderById(id);
        //then
        assertEquals(Status.WAITING, respOrderDTOResult.getStatus());
    }

    @Test
    void changeStatusOrderByIdWhichIsNotPresentTest() {
        //given
        //when
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            orderService.changeStatusOrderById(id);
        });
        //then
        assertEquals("The order does not found!", notFoundException.getMessage());
    }

}