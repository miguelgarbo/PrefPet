package com.uni.PrefPet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Especie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "especie")
    @JsonIgnore
    private List<Animal> animais;

    @OneToMany(mappedBy = "especie")
    @JsonIgnore
    private List<Denuncia> denuncias;



}
