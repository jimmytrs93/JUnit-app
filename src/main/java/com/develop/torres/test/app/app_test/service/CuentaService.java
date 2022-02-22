package com.develop.torres.test.app.app_test.service;

import com.develop.torres.test.app.app_test.dto.CuentaDto;
import com.develop.torres.test.app.app_test.exceptions.BancoException;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {

    BigDecimal consultarSaldo(int cuenta);
    void retirar(Integer cuenta, BigDecimal monto) throws BancoException;
    void transferir(Integer cuentaOrigen, int cuentaDestino, BigDecimal monto) throws BancoException;
    List<CuentaDto> cuentasPorUsuario(Integer usuarioId);
}
