package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Usuarios.Orgao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgaoRepository extends JpaRepository<Orgao, Long> {

    boolean existsByCnpj(String cnpj);

}
