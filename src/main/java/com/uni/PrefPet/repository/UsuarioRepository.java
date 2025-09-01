package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Usuarios.UsuarioComum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    Optional<List<UsuarioComum>> findByNomeContainingIgnoreCase(String nome);

    Optional<List<UsuarioComum>> findByCpf(String cpf);

    Optional<List<UsuarioComum>> findByTelefone(String telefone);

    Optional<List<UsuarioComum>> findByEmail(String email);

    @Query("SELECT u FROM UsuarioComum u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<UsuarioComum> findByNomeContendo(@Param("nome") String nome);

}
