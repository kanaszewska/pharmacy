package com.debska.pharmacy.repository;

import com.debska.pharmacy.entity.ProducerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepository extends CrudRepository<ProducerEntity, Integer> {
    Optional<ProducerEntity> findByName(String name);
}
