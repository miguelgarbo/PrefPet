package com.uni.PrefPet.model.Usuarios;

import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.model.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Veterinario extends Usuario {

    @NotBlank(message = "O CRMV não pode ser nulo")
    @Column(unique = true)
    //PEGAR API DO CRMV E VALIDAR SE É ATIVO
    private String CRMV;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AplicacaoVacina> aplicacoes;

    public Veterinario(){
        setRole(Role.VETERINARIO);
    }

}
