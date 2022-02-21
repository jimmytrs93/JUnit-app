package com.develop.torres.test.app.app_test.repository;

import com.develop.torres.test.app.app_test.entities.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<CuentaEntity, Integer> {

    Optional<List<CuentaEntity>> findAllByBancoId(Integer bancoId);

    Optional<List<CuentaEntity>> findAllByUsuarioId(Integer usuarioId);
}
