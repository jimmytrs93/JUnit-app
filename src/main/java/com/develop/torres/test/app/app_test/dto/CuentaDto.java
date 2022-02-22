package com.develop.torres.test.app.app_test.dto;

import java.math.BigDecimal;

public class CuentaDto {

    private Integer id;
    private String tipo;
    private BancoDto banco;
    private UsuarioDto usuario;
    private BigDecimal saldo;

    public CuentaDto() {
    }

    public CuentaDto(Integer id, String tipo, BancoDto banco, UsuarioDto usuario, BigDecimal saldo) {
        this.id = id;
        this.tipo = tipo;
        this.banco = banco;
        this.usuario = usuario;
        this.saldo = saldo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BancoDto getBanco() {
        return banco;
    }

    public void setBanco(BancoDto banco) {
        this.banco = banco;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}