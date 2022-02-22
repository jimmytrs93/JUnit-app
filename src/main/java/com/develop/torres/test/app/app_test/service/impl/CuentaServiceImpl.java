package com.develop.torres.test.app.app_test.service.impl;

import com.develop.torres.test.app.app_test.dto.CuentaDto;
import com.develop.torres.test.app.app_test.entities.CuentaEntity;
import com.develop.torres.test.app.app_test.exceptions.BancoException;
import com.develop.torres.test.app.app_test.mapper.CuentaMapper;
import com.develop.torres.test.app.app_test.repository.CuentaRepository;
import com.develop.torres.test.app.app_test.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService {

    CuentaRepository cuentaRepository;
    CuentaMapper cuentaMapper;

    @Autowired
    public CuentaServiceImpl(CuentaRepository cuentaRepository, CuentaMapper cuentaMapper) {
        this.cuentaRepository = cuentaRepository;
        this.cuentaMapper = cuentaMapper;
    }

    @Override
    public BigDecimal consultarSaldo(int cuenta) {
        Optional<CuentaEntity> cuentaOpt = cuentaRepository.findById(cuenta);
        if (cuentaOpt.isPresent()) {
            return cuentaOpt.get().getSaldo();
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void retirar(Integer cuenta, BigDecimal monto) throws BancoException {
        Optional<CuentaEntity> cuentaOpt = cuentaRepository.findById(cuenta);
        CuentaEntity cuentaEntity = cuentaOpt.orElseThrow(() -> new BancoException("100", "No Existe cuenta con id " + cuenta));
        BigDecimal nuevoSaldo = cuentaEntity.getSaldo().subtract(monto);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new BancoException("101", "Dinero insuficiente");
        }
        cuentaEntity.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuentaEntity);
    }

    @Transactional
    @Override
    public void transferir(Integer cuentaOrigen, int cuentaDestino, BigDecimal monto) throws BancoException {
        Optional<CuentaEntity> cuentaOpt = cuentaRepository.findById(cuentaDestino);
        CuentaEntity cuentaEntity = cuentaOpt.orElseThrow(() -> new BancoException("100", "No Existe cuenta con id " + cuentaDestino));
        retirar(cuentaOrigen, monto);
        BigDecimal nuevoSaldo = cuentaEntity.getSaldo().add(monto);
        cuentaEntity.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuentaEntity);
    }

    @Override
    public List<CuentaDto> cuentasPorUsuario(Integer usuarioId) {

        Optional<List<CuentaEntity>> cuentasOpt = cuentaRepository.findAllByUsuarioId(usuarioId);
        if (cuentasOpt.isPresent()) {
            List<CuentaEntity> list = cuentasOpt.get();
            List<CuentaDto> cuentas = cuentaMapper.cuentasEntityToCuentasDto(list);
            return cuentas;
        }

        return null;
    }
}
