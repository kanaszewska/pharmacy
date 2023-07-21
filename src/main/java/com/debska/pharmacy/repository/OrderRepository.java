package com.debska.pharmacy.repository;

import com.debska.pharmacy.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {
//    Optional<OrderEntity> findByName(String name);
}