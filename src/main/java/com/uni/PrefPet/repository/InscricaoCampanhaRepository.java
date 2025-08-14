package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Campanha;
import com.uni.PrefPet.model.InscricaoCampanha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface InscricaoCampanhaRepository extends JpaRepository<InscricaoCampanha, Long> {

    boolean existsByAnimalAndCampanha(Animal animal, Campanha campanha);

}
