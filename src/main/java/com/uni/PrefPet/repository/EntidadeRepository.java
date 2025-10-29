package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Enum.TipoEntidade;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.model.Usuarios.Entidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntidadeRepository extends JpaRepository<Entidade, Long> {

    //especialistas
    Optional<List<Entidade>> findByTipoEntidade(TipoEntidade tipoEntidade);


    //padrao
    boolean existsByCnpj(String cnpj);
    Optional<Entidade> findByCnpj(String cnpj);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    Optional<List<Entidade>> findByNomeContainingIgnoreCase(String nome);

    Optional<Entidade> findByCpf(String cpf);

    Optional<List<Entidade>> findByTelefone(String telefone);

    Optional<Entidade> findByEmail(String email);



}
