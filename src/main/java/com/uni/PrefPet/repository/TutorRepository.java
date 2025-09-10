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
    Optional<Entidade> findByCnpj(String cnpj);

    boolean existsByTelefone(String telefone);

    Optional<List<Tutor>> findByNomeContainingIgnoreCase(String nome);

    Optional<List<Tutor>> findByCpf(String cpf);

    Optional<List<Tutor>> findByTelefone(String telefone);

    Optional<List<Tutor>> findByEmail(String email);


}


