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

    @Column(columnDefinition = "TEXT")
    private String texto;

    private Integer nivel;

    private Boolean aceito;

    @ManyToOne
    @JoinColumn(name = "tutor_destinatario_id")
    private Tutor tutorDestinatario;

    @ManyToOne
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "tutor_remetente_id")
    private Tutor tutorRemetente;
}
