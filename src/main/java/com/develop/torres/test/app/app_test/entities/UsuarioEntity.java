package com.develop.torres.test.app.app_test.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USUARIO")
public class UsuarioEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usu_seq")
    @SequenceGenerator(name = "usu_seq", sequenceName = "USU_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "NOMBRE")
    private String nombre;

    public UsuarioEntity() {
    }

    public UsuarioEntity(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
