package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.model.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AplicacaoVacinaRepository extends JpaRepository<AplicacaoVacina, Long> {

    Optional<AplicacaoVacina> findByLote(String lote);

    boolean existsByLote(String lote);

    Optional<List<AplicacaoVacina>> findByDataAplicacao(LocalDate dataAplicacao);

    Optional<List<AplicacaoVacina>> findByDataAplicacaoAfter(LocalDate data);

    Optional<List<AplicacaoVacina>> findByDataValidadeBefore(LocalDate data);

    Optional<List<AplicacaoVacina>> findByDataValidadeAfter(LocalDate data);

    // Método para contar as aplicações de uma vacina específica para um animal específico
    long countByAnimalAndVacina(Animal animal, Vacina vacina);

    Optional<List<AplicacaoVacina>> findByAnimalId(Long animal_id);
}
