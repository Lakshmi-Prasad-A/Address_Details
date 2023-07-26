package com.insignia.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insignia.entity.CustomerBasicDetailsEntity;

public interface CustomerBasicDetailsRepo extends JpaRepository<CustomerBasicDetailsEntity, Serializable> {

	
}
