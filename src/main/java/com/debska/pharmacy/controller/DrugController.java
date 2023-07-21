package com.debska.pharmacy.controller;

import com.debska.pharmacy.dto.DrugDTO;
import com.debska.pharmacy.entity.DrugEntity;
import com.debska.pharmacy.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/drug")
public class DrugController {

    @Autowired
    private DrugService drugService;

    @PostMapping
    public ResponseEntity<String> postDrug(@RequestBody DrugDTO drugDTOdata){

        String postResult = drugService.postDrug(drugDTOdata);
        if (postResult == null){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(postResult, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DrugDTO>> getAllDrugs(){
        List<DrugDTO> drugsDTOList = drugService.getAllDrugs();

        if (drugsDTOList.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(drugsDTOList, HttpStatus.OK);
    }

    @GetMapping("/get-drug-by/{id}")
    public ResponseEntity<DrugDTO> getDrugById(@PathVariable int id){
        Optional<DrugDTO> foundDrug = Optional.ofNullable(drugService.getDrugById(id));

            return new ResponseEntity<>(foundDrug.get(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDrugById(@PathVariable int id){
        String removedDrugsName = drugService.deleteDrugById(id);
        //todo you don't need to check if removedDrugsName is empty which comes from the service - DONE!!!

        return new ResponseEntity<>(removedDrugsName, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DrugDTO> updateDrugById(@PathVariable int id, @RequestBody DrugDTO updateDataDrug){
        DrugDTO updatedDrugDTO = drugService.updateDrugById(id, updateDataDrug);

        return new ResponseEntity<>(updatedDrugDTO, HttpStatus.OK);
    }
}
