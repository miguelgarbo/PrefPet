package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Usuarios.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {


    //especialistas
    boolean existsByCRMV(String cnpj);
    Optional<Veterinario> findByCRMV(String crmv);

    //padrao
    boolean existsByCnpj(String cnpj);
    Optional<Veterinario> findByCnpj(String cnpj);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    Optional<List<Veterinario>> findByNomeContainingIgnoreCase(String nome);

    Optional<Veterinario> findByCpf(String cpf);

    Optional<Veterinario> findByEmail(String email);





}
