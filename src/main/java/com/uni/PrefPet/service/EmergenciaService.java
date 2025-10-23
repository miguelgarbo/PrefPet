package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Emergencia;
import com.uni.PrefPet.repository.ContatoRepository;
import com.uni.PrefPet.repository.EmergenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmergenciaService {

    @Autowired
    private EmergenciaRepository emergenciaRepository;

//    @Autowired
//    private ContatoRepository contatoRepository;

//    public Emergencia save(Emergencia denuncia) {
//        if (denuncia.getContatos() != null && !denuncia.getContatos().isEmpty()) {
//            List<Contato> contatosTratados = new ArrayList<>();
//            for (Contato c : denuncia.getContatos()) {
//                if (c.getId() != null) {
//
//                    Contato existente = contatoRepository.findById(c.getId())
//                            .orElseThrow(() -> new RuntimeException("Contato não encontrado: " + c.getId()));
//                    contatosTratados.add(existente);
//                } else {
//                    contatosTratados.add(contatoRepository.save(c));
//                }
//            }
//            denuncia.setContatos(contatosTratados);
//        }
//        return emergenciaRepository.save(denuncia);
//    }
    
    
    public Emergencia save(Emergencia emergencia){
        
        if (emergenciaRepository.existsByNome(emergencia.getNome())){
            throw new IllegalArgumentException("Esse Tipo de Emergencia já existe");
        }
        return emergenciaRepository.save(emergencia);
    }

    public Emergencia findById(Long id) {
        return emergenciaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Emergencia não encontrada")
        );
    }

    public List<Emergencia> findAll() {
        return emergenciaRepository.findAll();
    }

    public String delete(Long id) {
        if (!emergenciaRepository.existsById(id)) {
            throw new EntityNotFoundException("Emergencia não encontrada");
        }

        emergenciaRepository.deleteById(id);
        return "Emergencia deletada com sucesso";
    }


    public Emergencia update(Long id, Emergencia denunciaAtualizada) {
        Emergencia denunciaSelecionada = emergenciaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Emergencia não encontrada")
        );

        if (denunciaAtualizada.getNome() != null) {
            denunciaSelecionada.setNome(denunciaAtualizada.getNome());
        }
        return emergenciaRepository.save(denunciaSelecionada);
    }

    /// Filtros extras
    public List<Emergencia> findByNome(String nome) {
        return emergenciaRepository.findByNome(nome).orElseThrow(()->
                new EntityNotFoundException("Tipo de Emergencia Não Encontrada"));
    }

}
