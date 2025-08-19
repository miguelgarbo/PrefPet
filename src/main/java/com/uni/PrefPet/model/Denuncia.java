package com.uni.PrefPet.model;

import com.uni.PrefPet.model.Enum.StatusDenuncia;
import com.uni.PrefPet.model.Enum.TipoDenuncia;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoDenuncia tipo; // MAUS_TRATOS ou ANIMAL_SILVESTRE

    @Enumerated(EnumType.STRING)
    private StatusDenuncia status;

    @ManyToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.ALL)
    private Especie especie;

    @NotBlank(message = "Descrição Não deve ser nula")
    private String descricao;

    @Embedded
    private Localizacao localizacao;

    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    private boolean anonima;

    @ManyToMany
    @JoinTable(
            name = "denuncia_contato",
            joinColumns = @JoinColumn(name = "denuncia_id"),
            inverseJoinColumns = @JoinColumn(name = "contato_id")
    )
    private List<Contato> contatosNotificados;
}
