package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Usuarios.Tutor;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    boolean existsByRegistroGeral(String registroGeral);

    Optional<Animal> findByNomeContainingIgnoreCase(String nome);

    Optional<List<Animal> >findByEspecieIgnoreCase(String especie);

    Optional<List<Animal>> findByCorIgnoreCase(String cor);

    Optional<List<Animal>> findBySexoIgnoreCase(String sexo);

    Optional<List<Animal>> findByCastrado(Boolean castrado);

    Optional<Animal> findByNumeroMicrochip(String microchip);

    Optional<List<Animal>> findByTutor(Tutor tutor);

    Optional<List<Animal>> findByDataNascimento(LocalDate dataNascimento);

    Optional<List<Animal>> findByRegistroGeral(String registroGeral);

    @Query("SELECT a FROM Animal a WHERE a.dataNascimento <= :limite")
    List<Animal> findAnimaisIdadeAcima(@Param("limite") LocalDate limite);

    @Query("SELECT a FROM Animal a WHERE a.dataNascimento BETWEEN :limiteMax AND :limitMin")
    List<Animal> findAnimaisIdadeEntre(@Param("limiteMax") LocalDate limiteMax, @Param("limiteMin")LocalDate limiteMin);

}
