package com.uni.PrefPet.model.Usuarios;

import com.uni.PrefPet.model.Enum.TipoOrgao;
import com.uni.PrefPet.model.Publicacao;
import com.uni.PrefPet.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;
@Entity
@Data
public class Orgao extends Usuario {
    @Enumerated(EnumType.STRING)
    private TipoOrgao tipoOrgao;

    @CNPJ(message = "CNPJ inválido")
    @Column(unique = true)
    @NotBlank(message = "Cnpj Não deve ser nulo")
    private String cnpj;

    @OneToMany(mappedBy = "usuario")
    List<Publicacao> publicacoes;
}
