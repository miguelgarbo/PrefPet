package com.uni.PrefPet.model.Usuarios;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uni.PrefPet.model.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Enumerated(EnumType.STRING)
    private Role role;

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

    //metodos sobrescritos da userDetails
    //essencias pro processo de roles funcionar

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    //funções padroes que o userDetails permite
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }





}
