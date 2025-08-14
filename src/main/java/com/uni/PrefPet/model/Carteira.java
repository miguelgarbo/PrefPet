package com.uni.PrefPet.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Entity


@Getter
@Setter
public class Carteira {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

}