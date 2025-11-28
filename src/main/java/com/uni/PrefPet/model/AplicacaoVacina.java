package com.uni.PrefPet.model;

import com.uni.PrefPet.model.Usuarios.Veterinario;
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
    private Long id;


    private LocalDate dataAplicacao;

    private LocalDate dataValidade;


    private LocalDate dataPrevista;

    @NotBlank(message = "Lote é um campo obrigatório")
    private String lote;

    private Integer numeroDose;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "vacina_id")
    private Vacina vacina;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    private Veterinario veterinario;
}
