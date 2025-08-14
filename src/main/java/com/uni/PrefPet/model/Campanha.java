package com.uni.PrefPet.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity

public class Campanha {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;

    @OneToMany(mappedBy = "campanha", cascade = CascadeType.ALL)
    private List<InscricaoCampanha> inscricaoCampanhas;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario escritor;
}
