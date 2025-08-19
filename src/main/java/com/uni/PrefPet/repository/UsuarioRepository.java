package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Usuario;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);
    Optional<List<Usuario>> findByNomeContainingIgnoreCase(String nome);
    Optional<List<Usuario>> findByCpf(String cpf);
    Optional<List<Usuario>> findByTelefone(String telefone);
    Optional<List<Usuario>> findByEmail(String email);


    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByNomeContendo(@Param("nome") String nome);

}
