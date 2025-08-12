package com.uni.PrefPet.model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Animal {

    private String nome;
    private String registroGeral;
    private String especie;
    private Boolean castrado;
    private String cor;
    private String sexo;
    private Boolean microchip;
    private LocalDate dataNascimento;
    private String naturalidade;

    @ManyToOne(cascade = CascadeType.ALL)
    private Tutor tutor;
}
