package com.uni.PrefPet.model.Usuarios;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;
@Entity
@Data
public class UsuarioComum  extends Usuario {


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Animal> animais;

    @NotBlank(message = "Cpf Não pode estar vazio")
    @Column(unique = true)
    @CPF(message = "CPF INVALIDO")
    private String cpf;



}
