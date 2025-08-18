package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);
    Optional<Usuario> findByNome(String nome);
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByTelefone(String telefone);
    Optional<Usuario> findByEmail(String email);
}
