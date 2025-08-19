package com.uni.PrefPet.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

import com.uni.PrefPet.model.Campanha;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Long> { //Campanha é a entidade que a interface manipula e Long é o tipo do id da entidade

    boolean existsByTitulo(String titulo); //verifica se já existe campanha com esse título
    List<Campanha> findByDataCriacao(LocalDate data); //busca por data exata
    List<Campanha> findByTituloContainingIgnoreCase(String titulo); //busca parcial no título
    List<Campanha> findCampanhaByDataCriacaoAfter(LocalDate data); //busca por campanha depois do dataCriacao
    List<Campanha> findCampanhaByDataCriacaoBefore(LocalDate data);
    List<Campanha> findCampanhaByDataCriacaoBetween(LocalDate comeco, LocalDate fim);
}