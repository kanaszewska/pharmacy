package com.debska.pharmacy.service;

import com.debska.pharmacy.dto.DrugDTO;
import com.debska.pharmacy.dto.ProducerDTO;
import com.debska.pharmacy.entity.DrugEntity;
import com.debska.pharmacy.entity.ProducerEntity;
import com.debska.pharmacy.exceptions.NotFoundException;
import com.debska.pharmacy.mapper.DrugMapper;
import com.debska.pharmacy.mapper.ProducerMapper;
import com.debska.pharmacy.repository.DrugRepository;
import com.debska.pharmacy.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class DrugService {

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private ProducerRepository producerRepository;

    public String postDrug(DrugDTO drugDTOdata){

        ProducerEntity producer =
                producerRepository.findByName(drugDTOdata.getProducerDTO().getName())
                                    .orElseGet(() -> saveNewProducer(drugDTOdata.getProducerDTO()));

        Optional<DrugEntity> drug = drugRepository.findDrugByName(drugDTOdata.getName());

        if (drug.isPresent()){
            return "This drug exists";
        } else {
            DrugEntity drugEntity = saveNewDrug(drugDTOdata, producer);
            DrugDTO drugDTO = DrugMapper.mapDrugEntityToDTO(drugEntity);

            return "You have added a new drug " + drugDTO.getName() +" "+drugDTO.getQuantityOfTablets() + " tablets.";
        }
    }
    //todo send as parameter producerDTO-DONE!!!!!!
    private ProducerEntity saveNewProducer(ProducerDTO producerDTOdata){

        ProducerDTO producerDTO = ProducerDTO.builder()
                .name(producerDTOdata.getName())
                .country(producerDTOdata.getCountry())
                .build();

        ProducerEntity producerEntity = producerRepository.save(ProducerMapper.mapProducerDTOToEntity(producerDTO));
        return producerEntity;
    }

    private DrugEntity saveNewDrug(DrugDTO drugDTOdata, ProducerEntity producer){

        DrugDTO drugDTO = DrugDTO.builder()
                .name(drugDTOdata.getName())
                .quantityOfTablets(drugDTOdata.getQuantityOfTablets())
                .price(drugDTOdata.getPrice())
                .orders(drugDTOdata.getOrders())
                .build();

        DrugEntity drugEntity = DrugMapper.mapDrugDTOToEntity(drugDTO, producer);
        //first save the producer
        //set producerEntity to DrugEntity
        //save drug
        drugEntity = drugRepository.save(drugEntity);

        return drugEntity;
    }

    public List<DrugDTO> getAllDrugs(){
        return drugRepository.findAll().stream()
                .map(DrugMapper::mapDrugEntityToDTO)
                .collect(Collectors.toList());
    }

    public DrugDTO getDrugById(int id){
        return drugRepository.findById(id)
                .map(drug-> DrugMapper.mapDrugEntityToDTO(drug))
                //todo use orElseThrow - DONE!!!
                .orElseThrow(()-> new NotFoundException("The drug does not found!"));
    }

    public String deleteDrugById(int id){

        DrugEntity removedDrug = drugRepository.findById(id)
                //todo use the new exception (NotFoundException) and use here and in Controller advice -- DONE!!
                // do the same in different places of the application -- DONE!!
                .orElseThrow(() -> new NotFoundException("The drug does not found!"));

            drugRepository.delete(removedDrug);
            return "You have removed the drug " + removedDrug.getName();
    }

    public DrugDTO updateDrugById(int id, DrugDTO drugDTOdata){

       return drugRepository.findById(id)
                .map(drugEntity-> updateDrug(drugDTOdata, drugEntity))
               //todo use orElseThrow instead of orElse - DONE!!
                .orElseThrow(()->new NotFoundException("The drug does not found!"));
    }

    private DrugDTO updateDrug(DrugDTO drugDTOdata, DrugEntity updatedDrugEntity){

        updatedDrugEntity.setName(drugDTOdata.getName());
        updatedDrugEntity.setQuantityOfTablets(drugDTOdata.getQuantityOfTablets());
        updatedDrugEntity.setPrice(drugDTOdata.getPrice());
        updatedDrugEntity.setOrders(drugDTOdata.getOrders());

        drugRepository.save(updatedDrugEntity);
        DrugDTO drugDTO = DrugMapper.mapDrugEntityToDTO(updatedDrugEntity);
        return drugDTO;
    }
}
