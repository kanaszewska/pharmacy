package com.debska.pharmacy.repository;

import com.debska.pharmacy.entity.ProducerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProducerRepository extends CrudRepository<ProducerEntity, Integer> {

    Optional<ProducerEntity> findByName(String name);
}
