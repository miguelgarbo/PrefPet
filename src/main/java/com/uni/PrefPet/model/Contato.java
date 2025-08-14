package com.uni.PrefPet.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser nulo")
    private String nomeOrgao;

    private String apiUrl;

    private String telefone;
    private String email;

    private Boolean ativo;

    @ManyToMany(mappedBy = "contatosNotificados")
    private List<Denuncia> denunciasRecebidas;
}

