package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Usuarios.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    boolean existsByCRMV(String crmv);

}
