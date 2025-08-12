package com.uni.PrefPet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String CPF;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Animal> animais;

}
