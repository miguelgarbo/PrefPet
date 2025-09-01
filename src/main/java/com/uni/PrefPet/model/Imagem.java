package com.uni.PrefPet.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Deve Haver uma url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "publicacao_id")
    private Publicacao publicacao;

}
