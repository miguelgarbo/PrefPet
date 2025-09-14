package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {

    Optional<List<Vacina>> findByNomeContainingIgnoreCase(String nome);
}
