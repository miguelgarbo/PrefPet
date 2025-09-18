package com.uni.PrefPet.model;

import com.uni.PrefPet.model.Usuarios.Tutor;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String texto;

    private Integer nivel;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;
}
