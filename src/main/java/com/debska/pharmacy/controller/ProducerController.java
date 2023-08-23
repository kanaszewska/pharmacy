package com.debska.pharmacy.controller;

import com.debska.pharmacy.dto.ProducerDTO;
import com.debska.pharmacy.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producer")
public class ProducerController {
    private ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<ProducerDTO> postProducer(@RequestBody ProducerDTO producerDTO) {
        ProducerDTO postResult = producerService.postProducer(producerDTO);
        return new ResponseEntity<>(postResult, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProducerDTO>> getAllProducers() {
        List<ProducerDTO> producersList = producerService.getAllProducers();

        if (producersList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(producersList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducerDTO> getProducerById(@PathVariable int id) {
        ProducerDTO foundProducer = producerService.getProducerById(id);

        return new ResponseEntity<>(foundProducer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducerById(@PathVariable int id) {
        String removedProducersName = producerService.deleteProducerById(id);

        return new ResponseEntity<>(removedProducersName, HttpStatus.OK);
    }

}
