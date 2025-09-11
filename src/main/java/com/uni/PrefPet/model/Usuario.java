package com.uni.PrefPet.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;


@Data
@MappedSuperclass
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Column(unique = true)
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Telefone inválido")

    private String telefone;

    @NotBlank(message = "O Cep é obrigatório")
    private String cep;

    @NotBlank(message = "Cpf Não pode estar vazio")
    @Column(unique = true)
    @CPF(message = "CPF INVALIDO")
    private String cpf;

    private String cidade;

    private String estado;

    @CNPJ(message = "CNPJ inválido")
    @Column(unique = true)
    private String cnpj;

    @NotBlank(message = "A Senha é obrigatória")
    private String senha;

    @NotBlank(message = "Email é obrigatório")
    @Column(unique = true)
    @Email
    private String email;

     private String imagemUrlPerfil;

}
