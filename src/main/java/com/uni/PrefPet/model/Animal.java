package com.uni.PrefPet.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
