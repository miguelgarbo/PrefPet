package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Usuarios.Entidade;
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

    Optional<List<Veterinario>> findByCpf(String cpf);

    Optional<List<Veterinario>> findByTelefone(String telefone);

    Optional<List<Veterinario>> findByEmail(String email);





}
