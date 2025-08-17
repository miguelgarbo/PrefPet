package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Campanha;
import com.uni.PrefPet.model.InscricaoCampanha;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Enum.StatusInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoCampanhaRepository extends JpaRepository<InscricaoCampanha, Long> {

    boolean existsByAnimalAndCampanha(Animal animal, Campanha campanha);

    List<InscricaoCampanha> findByCampanha(Campanha campanha);
    List<InscricaoCampanha> findByAnimal(Animal animal);
    List<InscricaoCampanha> findByUsuario(Usuario usuario);
    List<InscricaoCampanha> findByStatus(StatusInscricao status);
    List<InscricaoCampanha> findByAnimalAndCampanha(Animal animal, Campanha campanha);
    List<InscricaoCampanha> findBycampanhaAndStatus(Campanha campanha, StatusInscricao status);

}
