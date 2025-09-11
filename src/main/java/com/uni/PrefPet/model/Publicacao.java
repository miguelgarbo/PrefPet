package com.uni.PrefPet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uni.PrefPet.model.Usuarios.Entidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
public class Publicacao {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O tipo da campanha é obrigatório")
    private String tipoPublicacao;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Imagem> imagens;

    @ManyToOne
    @JoinColumn(name = "entidade_id")
    private Entidade entidade;
}
