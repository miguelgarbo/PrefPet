package com.uni.PrefPet.model.Usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uni.PrefPet.model.Enum.TipoEntidade;
import com.uni.PrefPet.model.Publicacao;
import com.uni.PrefPet.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;
@Entity
@Data
public class Entidade extends Usuario {
    @Enumerated(EnumType.STRING)
    private TipoEntidade tipoEntidade;

    @OneToMany(mappedBy = "entidade")
    @JsonIgnore
    List<Publicacao> publicacoes;
}
