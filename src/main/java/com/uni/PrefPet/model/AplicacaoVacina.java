package com.uni.PrefPet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class AplicacaoVacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull(message = "Data de aplicação não deve ser nula")
    private LocalDate dataAplicacao;

    private LocalDate dataValidade;

    @NotBlank(message = "Lote é um campo obrigatório")
    private String lote;

    private Integer numeroDose;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "vacina_id")
//    @JsonIgnoreProperties("aplicacoes")
    private Vacina vacina;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "animal_id")
//    @JsonIgnoreProperties("aplicacoes")
    private Animal animal;


}
