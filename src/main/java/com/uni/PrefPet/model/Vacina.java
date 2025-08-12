package com.uni.PrefPet.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nome;


}
