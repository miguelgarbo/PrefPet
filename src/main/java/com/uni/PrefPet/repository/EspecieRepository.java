package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {

    boolean existsByNomeIgnoreCase(String nome);


    List<Especie> findByNomeContainingIgnoreCase(String nome);
}
