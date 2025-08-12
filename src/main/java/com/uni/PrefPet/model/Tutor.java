package com.uni.PrefPet.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Tutor {

    private String nome;
    private String CPF;


    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Animal> animais;

}
