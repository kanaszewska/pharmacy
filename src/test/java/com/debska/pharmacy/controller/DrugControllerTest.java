package com.debska.pharmacy.controller;

import com.debska.pharmacy.dto.DrugDTO;
import com.debska.pharmacy.dto.ProducerDTO;
import com.debska.pharmacy.service.DrugService;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DrugController.class)
class DrugControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final int id = 1;
    private static final BigDecimal price = new BigDecimal(10.5);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DrugService drugService;

    @Test
    void postDrugTest() throws Exception {
        DrugDTO requestDto = new DrugDTO();
        String responseMessage = "You have added a new drug Apap 10 tablets.";
        when(drugService.createDrug(any(DrugDTO.class))).thenReturn(responseMessage);

        mvc.perform(post("/drug")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(responseMessage));
    }

    @Test
    void getAllDrugsStatusIsOKTest() throws Exception {
        DrugDTO drug1 = new DrugDTO("Apap", 10, price, new ProducerDTO());
        List<DrugDTO> list = List.of(drug1);
        when(drugService.getAllDrugs()).thenReturn(list);

        mvc.perform(get("/drug/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Apap")));
    }

    @Test
    void getAllDrugsStatusIsNotOKTest() throws Exception {
        List<DrugDTO> drugDTOList = new ArrayList<>();
        when(drugService.getAllDrugs()).thenReturn(drugDTOList);

        mvc.perform(get("/drug/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void getDrugByIdStatusIsOKTest() throws Exception {
        DrugDTO foundDrug = new DrugDTO("Apap", 10, price, new ProducerDTO());
        when(drugService.getDrugById(id)).thenReturn(foundDrug);

        mvc.perform(get("/drug/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apap"));
    }

    @Test
    void getDrugByIdStatusIsNotOKTest() throws Exception {
        when(drugService.getDrugById(id)).thenReturn(null);

        mvc.perform(get("/drug/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteDrugByIdTest() throws Exception {
        DrugDTO drug = new DrugDTO("Apap", 10, price, new ProducerDTO());
        String removedDrugMessage = "You have removed the drug " + drug.getName();
        when(drugService.deleteDrugById(id)).thenReturn(removedDrugMessage);

        mvc.perform(delete("/drug/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("You have removed the drug Apap"));

    }

    @Test
    void updateDrugByIdTest() throws Exception {
        DrugDTO updateDataDrug = new DrugDTO("Apap", 10, price, new ProducerDTO());
        DrugDTO expectedDrug = new DrugDTO();
        when(drugService.updateDrugById(id, updateDataDrug)).thenReturn(expectedDrug);

        mvc.perform(put("/drug/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDataDrug)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedDrug)));
    }
}