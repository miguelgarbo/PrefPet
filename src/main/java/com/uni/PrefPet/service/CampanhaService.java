package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Campanha;
import com.uni.PrefPet.repository.CampanhaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampanhaService {
    
    @Autowired
    private CampanhaRepository campanhaRepository;

    ///crud basico

    public Campanha save(Campanha campanha){
        return campanhaRepository.save(campanha);
    }

    public Campanha findById(Long id){
        return campanhaRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Campanha Não Encontrado")
        );
    }

    public List<Campanha> findAll(){
        return campanhaRepository.findAll();
    }

    public String delete(Long id){

        if(!existById(id)){
        }
        campanhaRepository.deleteById(id);

        return "Campanha Deletado com Sucesso";
    }

    public boolean existById(Long id) {
        if (!campanhaRepository.existsById(id)) {
            throw new EntityNotFoundException("campanha Não Encontradq");
        }
        return true;
    }


    public Campanha update(Long id, Campanha campanhaAtualizada) {

        Campanha campanhaSelecionada = campanhaRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("campanha  Não Encontrada")
        );

        if (campanhaAtualizada.getDescricao() != null) {
            campanhaSelecionada.setDescricao(campanhaAtualizada.getDescricao());
        }

        if (campanhaAtualizada.getDataCriacao() != null) {
            campanhaSelecionada.setDataCriacao(campanhaAtualizada.getDataCriacao());
        }

        if (campanhaAtualizada.getEscritor() != null) {
            campanhaSelecionada.setEscritor(campanhaAtualizada.getEscritor());
        }

        if (campanhaAtualizada.getTitulo() != null) {
            campanhaSelecionada.setTitulo(campanhaAtualizada.getTitulo());
        }

        if (campanhaAtualizada.getInscricaoCampanhas() != null) {
            campanhaSelecionada.setInscricaoCampanhas(campanhaAtualizada.getInscricaoCampanhas());
        }

        return campanhaRepository.save(campanhaSelecionada);    }

    ///fim crud basico

    //serviços especificos:


    //
}
