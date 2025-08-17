package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    Optional<Contato> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Contato> findByAtivoTrue();
    List<Contato> findByNomeOrgao(String nomeOrgao); //buscar contatos por nome do órgão, busca exata
    List<Contato> findByNomeOrgaoContainingIgnoreCase(String nomeOrgao); //busca por nome do orgao, parcial
    List<Contato> findByTelefone(String telefone);
}
