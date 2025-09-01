package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Denuncia;
import com.uni.PrefPet.repository.DenunciaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaService {

    @Autowired
    private DenunciaRepository denunciaRepository;

    /// CRUD básico
    public Denuncia save(Denuncia denuncia) {
        return denunciaRepository.save(denuncia);
    }

    public Denuncia findById(Long id) {
        return denunciaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Denúncia não encontrada")
        );
    }

    public List<Denuncia> findAll() {
        return denunciaRepository.findAll();
    }

    public String delete(Long id) {
        if (!existById(id)) {
            throw new EntityNotFoundException("Denúncia não encontrada");
        }
        denunciaRepository.deleteById(id);
        return "Denúncia deletada com sucesso";
    }

    public boolean existById(Long id) {
        if (!denunciaRepository.existsById(id)) {
            throw new EntityNotFoundException("Denúncia não encontrada");
        }
        return true;
    }

    public Denuncia update(Long id, Denuncia denunciaAtualizada) {
        Denuncia denunciaSelecionada = denunciaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Denúncia não encontrada")
        );

        if (denunciaAtualizada.getNome() != null) {
            denunciaSelecionada.setNome(denunciaAtualizada.getNome());
        }

        return denunciaRepository.save(denunciaSelecionada);
    }

    /// Filtros extras
    public List<Denuncia> findByNome(String nome) {
        return denunciaRepository.findByNome(nome).orElseThrow(()->
                new EntityNotFoundException("Tipo de Denuncia Não Encontrada"));
    }


}
