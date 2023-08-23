package com.debska.pharmacy.repository;


import com.debska.pharmacy.entity.UserEntity;
import com.debska.pharmacy.enums.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    @Query("SELECT u FROM UserEntity u WHERE u.address.city = ?1 and u.address.street = ?2 and u.address.zipCode = ?3")
    List<UserEntity> findUsersByAddress(City city, String street, String zipCode);

    Optional<UserEntity> findById(int userId);

    List<UserEntity> findAll();

}