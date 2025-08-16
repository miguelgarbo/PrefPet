package com.uni.PrefPet.model;

import com.uni.PrefPet.model.Enum.StatusDenuncia;
import com.uni.PrefPet.model.Enum.TipoDenuncia;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Denuncia{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoDenuncia tipo; // MAUS_TRATOS ou ANIMAL_SILVESTRE

    @Enumerated(EnumType.STRING)
    private StatusDenuncia status;

    public enum TipoDenuncia {
        MAUS_TRATOS, ANIMAL_SILVESTRE
    }

    public enum StatusDenuncia {
        ABERTA, EM_ANDAMENTO, FINALIZADA
    }

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String especie;

    private String descricao;

    @Embedded
    private Localizacao localizacao;



    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "denuncia", cascade = CascadeType.ALL)
    private List<MidiaDenuncia> midias;

    private Boolean anonima;

    @ManyToMany
    @JoinTable(
            name = "denuncia_contato",
            joinColumns = @JoinColumn(name = "denuncia_id"),
            inverseJoinColumns = @JoinColumn(name = "contato_id")
    )
    private List<Contato> contatosNotificados;
}
