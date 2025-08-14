package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Vacina;
import com.uni.PrefPet.repository.VacinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VacinaService {

    @Autowired
    private VacinaRepository vacinaRepository;

    ///crud basico

    public Vacina save(Vacina vacina){
        return vacinaRepository.save(vacina);
    }

    public Vacina findById(Long id){
            return vacinaRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Vacina Não Encontradaa")
            );
    }

    public List<Vacina> findAll(){
        return vacinaRepository.findAll();
    }

    public String delete(Long id){

        if(!existById(id)){
        }
        vacinaRepository.deleteById(id);

        return "Vacina Deletada com Sucesso";
    }

    public boolean existById(Long id) {
        if (!vacinaRepository.existsById(id)) {
            throw new EntityNotFoundException("Vacina Não Encontrada");
        }
        return true;
    }

    public Vacina update(Long id, Vacina vacinaAtualizada) {

        Vacina vacinaSelecionada = vacinaRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Vacina Não Encontrada")
                );

        if (vacinaAtualizada.getNome() != null) {
            vacinaSelecionada.setNome(vacinaAtualizada.getNome());
        }

        if (vacinaAtualizada.getData() != null) {
            vacinaSelecionada.setData(vacinaAtualizada.getData());
        }

        return vacinaRepository.save(vacinaSelecionada);    }

    ///fim crud basico

    //serviços especificos:


    //






}
