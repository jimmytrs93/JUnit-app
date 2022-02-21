package com.develop.torres.test.app.app_test.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "MOVIMIENTO")
public class MovimientoEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mov_seq")
    @SequenceGenerator(name = "mov_seq", sequenceName = "MOV_SEQ", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTA_ID")
    private CuentaEntity cuenta;

    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "SALDO_ANTERIOR")
    private BigDecimal saldoAnterior;

    @Column(name = "SALDO_NUEVO")
    private BigDecimal saldoNuevo;

    @Column(name = "MONTO")
    private BigDecimal monto;

    @Column(name = "TIPO_MOVIMIENTO")
    private String tipoMovimiento;

    public MovimientoEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CuentaEntity getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaEntity cuenta) {
        this.cuenta = cuenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getSaldoNuevo() {
        return saldoNuevo;
    }

    public void setSaldoNuevo(BigDecimal saldoNuevo) {
        this.saldoNuevo = saldoNuevo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
}
