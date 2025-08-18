package com.uni.PrefPet.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "O título da campanha é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataCriacao;

    @OneToMany(mappedBy = "campanha", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InscricaoCampanha> inscricaoCampanhas;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario escritor;
}
