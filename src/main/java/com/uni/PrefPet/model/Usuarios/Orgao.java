package com.uni.PrefPet.model.Usuarios;

import com.uni.PrefPet.model.Enum.TipoOrgao;
import com.uni.PrefPet.model.Publicacao;
import com.uni.PrefPet.model.Usuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Orgao extends Usuario {
    @Enumerated(EnumType.STRING)
    private TipoOrgao tipoOrgao;

    @OneToMany(mappedBy = "usuario")
    List<Publicacao> publicacoes;
}
