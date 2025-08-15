package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    boolean existsByRegistroGeral(String registroGeral);
    Optional<Animal> findByNome(String nome);

}
