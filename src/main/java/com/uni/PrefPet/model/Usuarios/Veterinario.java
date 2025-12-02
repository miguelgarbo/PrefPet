package com.uni.PrefPet.model.Usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "veterinario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AplicacaoVacina> aplicacoes;



    public Veterinario(){
        setRole(Role.VETERINARIO);
    }


    public List<AplicacaoVacina> getAplicacoes() {
        return aplicacoes;
    }

    public void setAplicacoes(List<AplicacaoVacina> aplicacoes) {
        this.aplicacoes = aplicacoes;
    }

    public String getCRMV() {
        return CRMV;
    }

    public void setCRMV(String CRMV) {
        this.CRMV = CRMV;
    }
}
