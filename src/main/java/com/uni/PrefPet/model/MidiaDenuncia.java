package com.uni.PrefPet.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MidiaDenuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @ManyToOne
    @JoinColumn(name = "denuncia_id")
    private Denuncia denuncia;
}

