package com.uni.PrefPet.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @NotBlank(message = "Cpf  ")
    @Column(unique = true)
    private String CPF;
    private String telefone;

    @Column(unique = true)
    @Email
    private String email;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<InscricaoCampanha> inscricaoCampanhas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Denuncia> denuncias;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Animal> animais;

}
