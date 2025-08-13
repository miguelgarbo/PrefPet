package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Optional<Tutor> findByNome(String nome);
    Optional<Tutor> findByCPF(String cpf);
    Optional<Tutor> findByTelefone(String telefone);

}
