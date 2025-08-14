package com.uni.PrefPet.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nome;
    private String data;

    @ToString.Exclude
    @ManyToMany(mappedBy = "vacinas")
    @JsonIgnoreProperties("vacinas")
    private List<Animal> animais;

}
