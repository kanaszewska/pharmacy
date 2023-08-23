package com.debska.pharmacy.controller;

import com.debska.pharmacy.dto.DrugDTO;
import com.debska.pharmacy.dto.UserDTO;
import com.debska.pharmacy.dto.reqDTO.ReqOrderDTO;
import com.debska.pharmacy.dto.respDTO.RespOrderDTO;
import com.debska.pharmacy.enums.Status;
import com.debska.pharmacy.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static List<DrugDTO> drugDTOList = new ArrayList<>();
    private static final int id = 1;
    private static final BigDecimal wholePrice = new BigDecimal(21);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrderTest() throws Exception {
        RespOrderDTO respOrderDTO = new RespOrderDTO(Status.WAITING, wholePrice, drugDTOList, new UserDTO());
        List<Integer> listOfDrugsId = List.of(1);
        ReqOrderDTO reqOrderDTO = new ReqOrderDTO(listOfDrugsId, id);
        when(orderService.createOrder(reqOrderDTO)).thenReturn(respOrderDTO);

        mvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqOrderDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wholePrice").value(21))
                .andExpect(jsonPath("$.status").value("WAITING"));
    }

    @Test
    void getOrderByIdTest() throws Exception {
        RespOrderDTO respOrderDTO = new RespOrderDTO(Status.CREATED, wholePrice, drugDTOList, new UserDTO());
        when(orderService.getOrderById(id)).thenReturn(respOrderDTO);
        mvc.perform(get("/order/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wholePrice").value(21));
    }

    @Test
    void getAllOrdersStatusIsOKTest() throws Exception {
        RespOrderDTO respOrderDTO = new RespOrderDTO(Status.CREATED, wholePrice, drugDTOList, new UserDTO());
        List<RespOrderDTO> respOrderDTOList = List.of(respOrderDTO);
        when(orderService.getAllOrders()).thenReturn(respOrderDTOList);
        mvc.perform(get("/order/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getAllOrdersStatusIsNotOKTest() throws Exception {
        List<RespOrderDTO> respOrderDTOList = new ArrayList<>();
        when(orderService.getAllOrders()).thenReturn(respOrderDTOList);
        mvc.perform(get("/order/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteOrderByIdTest() throws Exception {
        String removedOrderMessage = "You have removed the order";
        when(orderService.deleteOrderById(id)).thenReturn(removedOrderMessage);

        mvc.perform(delete("/order/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("You have removed the order"));
    }

    @Test
    void changeStatusOrderByIdTest() throws Exception {
        RespOrderDTO responseDto = new RespOrderDTO();
        when(orderService.changeStatusOrderById(id)).thenReturn(responseDto);

        mvc.perform(get("/order/changeStatus/1", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
    }
}