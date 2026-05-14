package com.uni.PrefPet.model.Usuarios;
import com.uni.PrefPet.model.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@Data
//public abstract class Usuario implements UserDetails{


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String keycloakId;

    private String nome;


    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(unique = true)
    private String telefone;
    private String cep;

    @NotBlank(message = "CPF é obrigatório")
    @Column(unique = true)
    @CPF
    private String cpf;
    private String cidade;
    private String estado;

    @Column(unique = true)
    private String cnpj;

    @NotBlank(message = "Email é obrigatório")
    @Column(unique = true)
    @Email
    private String email;

    private String imagemUrlPerfil;
}
