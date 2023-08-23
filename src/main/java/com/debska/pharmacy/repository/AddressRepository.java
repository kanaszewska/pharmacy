package com.debska.pharmacy.repository;


import com.debska.pharmacy.entity.AddressEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {

}