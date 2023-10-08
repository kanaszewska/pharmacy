package com.debska.pharmacy.service;

import com.debska.pharmacy.dto.DrugDTO;
import com.debska.pharmacy.dto.ProducerDTO;
import com.debska.pharmacy.entity.DrugEntity;
import com.debska.pharmacy.entity.ProducerEntity;
import com.debska.pharmacy.exceptions.ElementExistsExceptions;
import com.debska.pharmacy.exceptions.NotFoundException;
import com.debska.pharmacy.mapper.DrugMapper;
import com.debska.pharmacy.mapper.ProducerMapper;
import com.debska.pharmacy.repository.DrugRepository;
import com.debska.pharmacy.repository.ProducerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class DrugService {

    private DrugRepository drugRepository;

    private ProducerRepository producerRepository;

    public DrugService(DrugRepository drugRepository, ProducerRepository producerRepository) {
        this.drugRepository = drugRepository;
        this.producerRepository = producerRepository;
    }

    /**
     *
     * @param drugDTOdata
     * @return
     */
    public String createDrug(DrugDTO drugDTOdata) {

        ProducerEntity producer =
                producerRepository.findByName(drugDTOdata.getProducerDTO().getName())
                        .orElseGet(() -> saveNewProducer(drugDTOdata.getProducerDTO()));

        Optional<DrugEntity> drug = drugRepository.findDrugByName(drugDTOdata.getName());

        if (drug.isPresent()) {
            throw new ElementExistsExceptions("This drug exists");
        } else {
            DrugEntity drugEntity = saveNewDrug(drugDTOdata, producer);
            DrugDTO drugDTO = DrugMapper.mapDrugEntityToDTO(drugEntity);
            return "You have added a new drug " + drugDTO.getName() + " " + drugDTO.getQuantityOfTablets() + " tablets.";
        }
    }

    private ProducerEntity saveNewProducer(ProducerDTO producerDTOdata) {

        ProducerDTO producerDTO = ProducerDTO.builder()
                .name(producerDTOdata.getName())
                .country(producerDTOdata.getCountry())
                .build();

        ProducerEntity producerEntity = producerRepository.save(ProducerMapper.mapProducerDTOToEntity(producerDTO));
        return producerEntity;
    }

    private DrugEntity saveNewDrug(DrugDTO drugDTOdata, ProducerEntity producer) {

        DrugDTO drugDTO = DrugDTO.builder()
                .name(drugDTOdata.getName())
                .quantityOfTablets(drugDTOdata.getQuantityOfTablets())
                .price(drugDTOdata.getPrice())
                .build();

        DrugEntity drugEntity = DrugMapper.mapDrugDTOToEntity(drugDTO, producer);
        drugEntity = drugRepository.save(drugEntity);

        return drugEntity;
    }

    public List<DrugDTO> getAllDrugs() {
        return drugRepository.findAll().stream()
                .map(DrugMapper::mapDrugEntityToDTO)
                .collect(Collectors.toList());
    }

    public DrugDTO getDrugById(int id) {
        return drugRepository.findById(id)
                .map(drug -> DrugMapper.mapDrugEntityToDTO(drug))
                .orElseThrow(() -> new NotFoundException("The drug does not found!"));
    }

    public String deleteDrugById(int id) {
        DrugEntity removedDrug = drugRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("The drug does not found!"));

        drugRepository.delete(removedDrug);
        return "You have removed the drug " + removedDrug.getName();
    }

    public DrugDTO updateDrugById(int id, DrugDTO drugDTOdata) {
        return drugRepository.findById(id)
                .map(drugEntity -> updateDrug(drugDTOdata, drugEntity))
                .orElseThrow(() -> new NotFoundException("The drug does not found!"));
    }

    private DrugDTO updateDrug(DrugDTO drugDTOdata, DrugEntity updatedDrugEntity) {

        updatedDrugEntity.setName(drugDTOdata.getName());
        updatedDrugEntity.setQuantityOfTablets(drugDTOdata.getQuantityOfTablets());
        updatedDrugEntity.setPrice(drugDTOdata.getPrice());

        drugRepository.save(updatedDrugEntity);
        DrugDTO drugDTO = DrugMapper.mapDrugEntityToDTO(updatedDrugEntity);
        return drugDTO;
    }
}
