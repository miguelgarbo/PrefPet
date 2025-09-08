package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Emergencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmergenciaRepository extends JpaRepository<Emergencia, Long> {

    Optional<List<Emergencia>> findByNome(String nome);
}

