package com.debska.pharmacy.controller;

import com.debska.pharmacy.dto.ProducerDTO;
import com.debska.pharmacy.enums.Country;
import com.debska.pharmacy.service.ProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProducerController.class)
class ProducerControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final int id = 1;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProducerService producerService;

    @Test
    void postProducerTest() throws Exception {
        ProducerDTO reqProducerDto = new ProducerDTO("Hasco", Country.GERMANY);
        ProducerDTO respProducerDto = new ProducerDTO("Hasco", Country.GERMANY);

        when(producerService.postProducer(any(ProducerDTO.class))).thenReturn(respProducerDto);

        mvc.perform(post("/producer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqProducerDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hasco"))
                .andExpect(jsonPath("$.country").value("GERMANY"));
    }

    @Test
    void getAllProducersStatusIsOkTest() throws Exception {
        List<ProducerDTO> producersList = new ArrayList<>();
        ProducerDTO producerDTO = new ProducerDTO("Hasco", Country.POLAND);
        producersList.add(producerDTO);
        when(producerService.getAllProducers()).thenReturn(producersList);
        mvc.perform(get("/producer/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Hasco")));
    }

    @Test
    void getAllProducersStatusIsNotOkTest() throws Exception {
        List<ProducerDTO> producersList = new ArrayList<>();
        when(producerService.getAllProducers()).thenReturn(producersList);
        mvc.perform(get("/producer/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void getProducerByIdTest() throws Exception {
        ProducerDTO foundProducer = new ProducerDTO("Hasco", Country.POLAND);
        when(producerService.getProducerById(id)).thenReturn(foundProducer);
        mvc.perform(get("/producer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hasco"));

    }

    @Test
    void deleteProducerByIdTest() throws Exception {
        ProducerDTO foundProducer = new ProducerDTO("Hasco", Country.POLAND);
        String removedProducerMessage = "You have removed the producer " + foundProducer.getName();
        when(producerService.deleteProducerById(id)).thenReturn(removedProducerMessage);
        mvc.perform(delete("/producer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("You have removed the producer Hasco"));
    }
}