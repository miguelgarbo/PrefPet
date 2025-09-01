package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {

    Optional<List<Denuncia>> findByNome(String nome);
}

