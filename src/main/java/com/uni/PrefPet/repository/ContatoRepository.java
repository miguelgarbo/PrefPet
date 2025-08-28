package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    Optional<List<Contato>> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<List<Contato>>findByNomeOrgaoContainingIgnoreCase(String nomeOrgao); //busca por nome do orgao, parcial
    Optional<List<Contato>> findByTelefoneContaining(String telefone);


    //    @Id
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    private Long id;
    //
    //    @NotBlank(message = "Nome não pode ser nulo")
    //    @Size(max = 100, message = "Nome do órgão não pode ter mais de 100 caracteres")
    //    private String nomeOrgao;
    //
    //    @Pattern(regexp = "\\+?\\d{10,15}", message = "Telefone inválido")
    //    private String telefone;
    //
    //    @Email
    //    @NotBlank(message = "Email não pode ser vazio")
    //    private String email;
    //
    //    @JsonIgnoreProperties("denuncias")
    //    @ManyToMany(mappedBy = "contatos")
    //    private List<Denuncia> denuncias;
    //
    //    private Boolean ativo;
    //
    //
    //
    //
    //
    //}
}
