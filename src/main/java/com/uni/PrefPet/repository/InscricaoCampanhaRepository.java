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

    boolean existsByAnimalAndCampanhaAndUsuario(Animal animal, Campanha campanha, Usuario usuario);
    List<InscricaoCampanha> findByCampanhaTitulo(String titulo);
    List<InscricaoCampanha> findByAnimalNome(String nome);
    List<InscricaoCampanha> findByUsuarioNome(String nome);
    List<InscricaoCampanha> findByStatus(StatusInscricao status);}
