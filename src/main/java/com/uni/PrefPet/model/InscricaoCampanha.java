package com.uni.PrefPet.model;

import com.uni.PrefPet.model.Enum.StatusInscricao;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class InscricaoCampanha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Campanha campanha;

    @ManyToOne
    private Animal animal;

    @ManyToOne
    private Usuario usuario;

    private LocalDateTime dataInscricao;

    @Enumerated(EnumType.STRING)
    private StatusInscricao status;
}

