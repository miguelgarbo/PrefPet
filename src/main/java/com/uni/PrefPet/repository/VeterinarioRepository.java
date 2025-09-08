package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Usuarios.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    boolean existsByCRMV(String crmv);
    Optional<Veterinario> findByCrmv(String crmnv);

}
