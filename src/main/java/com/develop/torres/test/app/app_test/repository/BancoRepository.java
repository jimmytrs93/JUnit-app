package com.develop.torres.test.app.app_test.repository;

import com.develop.torres.test.app.app_test.entities.BancoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepository extends JpaRepository<BancoEntity, Integer> {
}
