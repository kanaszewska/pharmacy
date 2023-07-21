package com.debska.pharmacy.service;

import com.debska.pharmacy.dto.ProducerDTO;
import com.debska.pharmacy.entity.ProducerEntity;
import com.debska.pharmacy.exceptions.NotFoundException;
import com.debska.pharmacy.mapper.ProducerMapper;
import com.debska.pharmacy.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProducerService {

    @Autowired
    private ProducerRepository producerRepository;

    public String postProducer(ProducerDTO producerDTOdata){
        //todo use pipelines here
        Optional<ProducerEntity> producerRepositoryByName = producerRepository.findByName(producerDTOdata.getName());

        if (producerRepositoryByName.isEmpty()){
            ProducerEntity producerEntity = ProducerMapper.mapProducerDTOToEntity(producerDTOdata);
            producerRepository.save(producerEntity);
            return "You have added a new producer " + producerEntity.getName() +" from "+producerEntity.getCountry();
        } else {
            return new NotFoundException("This producer has been added").getMessage();
        }
    }

    public List<ProducerDTO> getAllProducers(){
        return ((List<ProducerEntity>) producerRepository.findAll()).stream()
                .map(ProducerMapper::mapProducerEntitytoDTO)
                .collect(Collectors.toList());
    }

    public ProducerDTO getProducerById(int id){
        return producerRepository.findById(id)
                .map(ProducerMapper::mapProducerEntitytoDTO)
                .orElseThrow(() -> new NotFoundException("The producer is not found"));
    }

    public String deleteProducerById(int id){
        ProducerEntity removedProducer = producerRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("The producer is not found"));

//        if (removedProducer.isPresent()){
            producerRepository.delete(removedProducer);
            return "You have removed the producer " + removedProducer.getName();
//        }
//        return null;
    }

}
