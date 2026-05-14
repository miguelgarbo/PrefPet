package com.uni.PrefPet.model.dtos;

import com.uni.PrefPet.model.Enum.TipoEntidade;
import lombok.Data;

@Data
public class EntidadeDTO {


    private String nome;
    private String email;
    private String senha;

    private String telefone;
    private String cpf;

    private String cidade;
    private String estado;
    private String cep;

    private String cnpj;

    private TipoEntidade tipoEntidade;
}
