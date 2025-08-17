package com.uni.PrefPet.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank(message = "nome é um campo obrigatório")
    private String nome;

    @NotBlank(message = "data de aplicação é um campo obrigatório")
    private String dataAplicacao;

    @NotBlank(message = "lote é um campo obrigatório")
    private String lote;

    @NotBlank(message = "validade é um campo obrigatório")
    private LocalDateTime validade;

    @ToString.Exclude
    @ManyToMany(mappedBy = "vacinas")
    @JsonIgnoreProperties("vacinas")
    private List<Animal> animais;


}
