package com.uni.PrefPet.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeOrgao;
    private String apiUrl;
    private String telefone;
    private String email;
    private Boolean ativo;

    @ManyToMany(mappedBy = "contatosNotificados")
    private List<Denuncia> denunciasRecebidas;
}

