package com.develop.torres.test.app.app_test.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "CUENTA")
public class CuentaEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cue_seq")
    @SequenceGenerator(name = "cue_seq", sequenceName = "CUE_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "TIPO")
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANCO_ID")
    private BancoEntity banco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UsuarioEntity usuario;

    @Column(name = "SALDO")
    private BigDecimal saldo;

    public CuentaEntity() {
    }

    public CuentaEntity(Integer id, String tipo, BancoEntity banco, UsuarioEntity usuario, BigDecimal saldo) {
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

    public BancoEntity getBanco() {
        return banco;
    }

    public void setBanco(BancoEntity banco) {
        this.banco = banco;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

}