package com.debska.pharmacy.controller;

import com.debska.pharmacy.dto.reqDTO.ReqOrderDTO;
import com.debska.pharmacy.dto.respDTO.RespOrderDTO;
import com.debska.pharmacy.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<RespOrderDTO> createOrder(@RequestBody ReqOrderDTO reqOrderDTO) {
        RespOrderDTO createdOrder = orderService.createOrder(reqOrderDTO);

        return new ResponseEntity<>(createdOrder, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespOrderDTO> getOrderById(@PathVariable int id) {
        RespOrderDTO foundOrder = orderService.getOrderById(id);

        return new ResponseEntity<>(foundOrder, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RespOrderDTO>> getAllOrders() {
        List<RespOrderDTO> ordersList = orderService.getAllOrders();

        if (ordersList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable int id) {
        String removedOrder = orderService.deleteOrderById(id);

        return new ResponseEntity<>(removedOrder, HttpStatus.OK);
    }

    @GetMapping("/changeStatus/{id}")
    public ResponseEntity<RespOrderDTO> changeStatusOrderById(@PathVariable int id) {
        RespOrderDTO updatedOrder = orderService.changeStatusOrderById(id);

        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}
