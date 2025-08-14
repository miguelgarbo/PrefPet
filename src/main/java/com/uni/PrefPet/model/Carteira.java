package com.uni.PrefPet.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Carteira {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

}