package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Publicacao;
import com.uni.PrefPet.model.Usuarios.Entidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

    Optional<List<Publicacao>> findByTipoPublicacao(String tipoPublicacao);
    Optional<List<Publicacao>> findByDescricaoContaining(String descricao);
    Optional<List<Publicacao>> findByEntidadeNome(String nome);
    Optional<List<Publicacao>> findByDataCriacao(LocalDate dataCriacao);
    Optional<List<Publicacao>> findByEntidade(Entidade entidade);

}
