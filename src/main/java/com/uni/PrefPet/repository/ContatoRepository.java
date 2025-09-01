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

}
