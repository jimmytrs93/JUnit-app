package com.develop.torres.test.app.app_test.repository;

import com.develop.torres.test.app.app_test.entities.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends JpaRepository<MovimientoEntity, Integer> {
}
