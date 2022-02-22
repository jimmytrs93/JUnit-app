package com.develop.torres.test.app.app_test.dto;

import java.util.List;

public class GetCuentasResponseDto {

    List<CuentaDto> cuentas;

    public GetCuentasResponseDto() {
    }

    public List<CuentaDto> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaDto> cuentas) {
        this.cuentas = cuentas;
    }
}