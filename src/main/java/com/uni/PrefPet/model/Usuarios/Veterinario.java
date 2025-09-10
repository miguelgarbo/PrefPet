package com.uni.PrefPet.model.Usuarios;

import com.uni.PrefPet.model.Usuario;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Data
public class Veterinario extends Usuario {

    @NotBlank(message = "O CRMV não pode ser nulo")
    @Column(unique = true)
    //PEGAR API DO CRMV E VALIDAR SE É ATIVO
    private String CRMV;
}
