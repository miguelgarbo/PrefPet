package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Denuncia;
import com.uni.PrefPet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
    List<Denuncia> findByTipo(Denuncia.TipoDenuncia tipo);
    List<Denuncia> findByStatus(Denuncia.StatusDenuncia status); //busca por andamento (aberta, em andamento ou finalizada)
    List<Denuncia> findByUsuario(Usuario usuario);
    List<Denuncia> findByAnonimaTrue();
    List<Denuncia> findByAnonimaFalse();
    List<Denuncia> findByEspecieContainingIgnoreCase(String especie);
}
