package com.uni.PrefPet.model;

import com.uni.PrefPet.model.Enum.StatusInscricao;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"animal_id", "campanha_id"})
})

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

