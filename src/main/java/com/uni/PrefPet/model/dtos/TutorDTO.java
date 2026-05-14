package com.uni.PrefPet.model.dtos;

import lombok.Data;

@Data
public class TutorDTO {

    private String nome;
    private String email;
    private String senha;

    private String telefone;
    private String cpf;

    private String cidade;
    private String estado;
    private String cep;
}