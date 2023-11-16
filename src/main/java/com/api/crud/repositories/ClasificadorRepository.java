package com.api.crud.repositories;

import com.api.crud.models.ClasificadorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasificadorRepository extends JpaRepository<ClasificadorModel, Integer> {
}
