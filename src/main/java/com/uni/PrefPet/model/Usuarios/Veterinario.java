package com.uni.PrefPet.model.Usuarios;

import com.uni.PrefPet.model.Usuario;
import jakarta.validation.constraints.NotBlank;

public class Veterinario extends Usuario {

    @NotBlank(message = "O CRMV não pode ser nulo")
    //PEGAR API DO CRMV E VALIDAR SE É ATIVO
    private String CRMV;
}
