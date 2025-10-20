package com.uni.PrefPet.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

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
    @JsonIgnore
    @ToString.Exclude
    private Publicacao publicacao;



}
