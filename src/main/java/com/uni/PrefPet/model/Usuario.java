package com.uni.PrefPet.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @NotBlank(message = "Cpf Não pode estar vazio")
    @Column(unique = true)
    private String CPF;

    @Column(unique = true)
    private String telefone;

    private String senha;

    @Column(unique = true)
    @Email
    private String email;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InscricaoCampanha> inscricaoCampanhas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)

    private List<Denuncia> denuncias;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference

    private List<Animal> animais;

}
