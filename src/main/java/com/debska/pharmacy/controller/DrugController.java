package com.debska.pharmacy.controller;

import com.debska.pharmacy.dto.DrugDTO;
import com.debska.pharmacy.service.DrugService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * CRUD operations for drugs
 */
@RestController
@RequestMapping("/drug")
public class DrugController {
    private DrugService drugService;

    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @PostMapping
    public ResponseEntity<String> postDrug(@RequestBody DrugDTO drugDTOdata) {
        String postResult = drugService.createDrug(drugDTOdata);

        return new ResponseEntity<>(postResult, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DrugDTO>> getAllDrugs() {
        List<DrugDTO> drugsDTOList = drugService.getAllDrugs();

        if (drugsDTOList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(drugsDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrugDTO> getDrugById(@PathVariable int id) {
        Optional<DrugDTO> foundDrug = Optional.ofNullable(drugService.getDrugById(id));

        if (foundDrug.isPresent()) {
            return new ResponseEntity<>(foundDrug.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDrugById(@PathVariable int id) {
        String removedDrugsName = drugService.deleteDrugById(id);

        return new ResponseEntity<>(removedDrugsName, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrugDTO> updateDrugById(@PathVariable int id, @RequestBody DrugDTO updateDataDrug) {
        DrugDTO updatedDrugDTO = drugService.updateDrugById(id, updateDataDrug);

        return new ResponseEntity<>(updatedDrugDTO, HttpStatus.OK);
    }
}
