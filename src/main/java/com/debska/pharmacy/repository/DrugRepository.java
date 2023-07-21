package com.debska.pharmacy.repository;

import com.debska.pharmacy.entity.DrugEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrugRepository extends CrudRepository<DrugEntity, Integer> {

    Optional<DrugEntity> findDrugByName (String name);

    List<DrugEntity> findAll();


}
