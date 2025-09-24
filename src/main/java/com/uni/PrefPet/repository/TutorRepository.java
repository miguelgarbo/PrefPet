package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.model.Usuarios.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCnpj(String cnpj);
    Optional<Tutor> findByCnpj(String cnpj);

    boolean existsByTelefone(String telefone);

    Optional<Tutor> findByNomeContainingIgnoreCase(String nome);

    Optional<Tutor> findByCpf(String cpf);

    Optional<Tutor> findByTelefone(String telefone);

    Optional<Tutor> findByEmail(String email);


}


