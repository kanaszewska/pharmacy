package com.debska.pharmacy.service;

import com.debska.pharmacy.dto.ProducerDTO;
import com.debska.pharmacy.entity.ProducerEntity;
import com.debska.pharmacy.exceptions.ElementExistsExceptions;
import com.debska.pharmacy.exceptions.NotFoundException;
import com.debska.pharmacy.mapper.ProducerMapper;
import com.debska.pharmacy.repository.DrugRepository;
import com.debska.pharmacy.repository.ProducerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class ProducerService {

    private ProducerRepository producerRepository;
    private DrugRepository drugRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public ProducerDTO postProducer(ProducerDTO producerDTOdata) {
        Optional<ProducerEntity> foundProducerByName = producerRepository.findByName(producerDTOdata.getName());

        if (foundProducerByName.isPresent()) {
            throw new ElementExistsExceptions("This producer has been added");
        }
        ProducerDTO producerDTO = saveNewProducer(producerDTOdata);
        return producerDTO;
    }


    private ProducerDTO saveNewProducer(ProducerDTO producerDTOdata) {
        ProducerEntity producerEntity = ProducerMapper.mapProducerDTOToEntity(producerDTOdata);
        producerRepository.save(producerEntity);
        ProducerDTO producerDTO = ProducerMapper.mapProducerEntitytoDTO(producerEntity);
        return producerDTO;
    }

    public List<ProducerDTO> getAllProducers() {
        return ((List<ProducerEntity>) producerRepository.findAll()).stream()
                .map(ProducerMapper::mapProducerEntitytoDTO)
                .collect(Collectors.toList());
    }

    public ProducerDTO getProducerById(int id) {
        return producerRepository.findById(id)
                .map(ProducerMapper::mapProducerEntitytoDTO)
                .orElseThrow(() -> new NotFoundException("The producer is not found"));
    }

    public String deleteProducerById(int id) {
        ProducerEntity removedProducer = producerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("The producer is not found"));
        producerRepository.delete(removedProducer);
        return "You have removed the producer " + removedProducer.getName();
    }

}
