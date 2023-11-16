package com.api.crud.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "clasificador")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClasificadorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @Lob
    private byte[] clasificador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getClasificador_de_rostros() {
        return clasificador;
    }

    public void setClasificador_de_rostros(byte[] clasificador_de_rostros) {
        this.clasificador = clasificador_de_rostros;
    }
}
