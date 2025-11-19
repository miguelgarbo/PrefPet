package com.uni.PrefPet.repository.auth;

import com.uni.PrefPet.model.Usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String Email);
    boolean existsByCpf(String CPF);
    boolean existsByTelefone(String telefone);
    boolean existsByCnpj(String CNPJ);

    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByCpfAndIdNot(String cpf,Long id);
    boolean existsByTelefoneAndIdNot(String telefone, Long id);
    boolean existsByCnpjAndIdNot(String cnpj, Long id);

}
