package com.uni.PrefPet.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser nulo")
    @Size(max = 100, message = "Nome do órgão não pode ter mais de 100 caracteres")
    private String nomeOrgao;

    @Pattern(regexp = "\\+?\\d{10,15}", message = "Telefone inválido")
    private String telefone;

    @Email
    @NotBlank(message = "Email não pode ser vazio")
    private String email;

    @JsonIgnoreProperties("emergencias")
    @ManyToMany(mappedBy = "contatos")
    @JsonBackReference
    private List<Emergencia> emergencias;

    private Boolean ativo;





}

