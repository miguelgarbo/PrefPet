package com.uni.PrefPet.model.Usuarios;

import com.uni.PrefPet.model.Enum.TipoOrgao;
import com.uni.PrefPet.model.Usuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.validator.constraints.br.CNPJ;

public class Orgao extends Usuario {
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;
    @Enumerated(EnumType.STRING)
    private TipoOrgao tipoOrgao;
}
