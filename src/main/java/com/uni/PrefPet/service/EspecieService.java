package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Especie;
import com.uni.PrefPet.repository.EspecieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecieService {

    @Autowired
    private EspecieRepository especieRepository;

    ///crud basico

    public Especie save(Especie especie){
        if(especieRepository.existsByNomeIgnoreCase(especie.getNome())){

            throw new IllegalArgumentException("Já existe uma Especie com este nome.");
        }

        return especieRepository.save(especie);
    }

    public Especie findById(Long id){
        return especieRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Especie Não Encontradaa")
        );
    }

    public List<Especie> findAll(){
        return especieRepository.findAll();
    }

    public String delete(Long id){

        if(!existById(id)){
            throw new EntityNotFoundException("");
        }
        especieRepository.deleteById(id);

        return "Especie Deletada com Sucesso";
    }

    public boolean existById(Long id) {
        if (!especieRepository.existsById(id)) {
            throw new EntityNotFoundException("Especie Não Encontrada");
        }
        return true;
    }

    public Especie update(Long id, Especie especieAtualizada) {

        Especie especieSelecionada = especieRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Especie Não Encontrada")
        );

        if (especieAtualizada.getNome() != null) {
            especieSelecionada.setNome(especieAtualizada.getNome());
        }

        return especieRepository.save(especieSelecionada);    }

    ///fim crud basico

    //serviços especificos:
    public boolean existsByNome(String nome) {
        return especieRepository.existsByNomeIgnoreCase(nome);
    }

    public List<Especie> findByNomeContaining(String nome) {
        List<Especie> especies = especieRepository.findByNomeContainingIgnoreCase(nome);
        if (especies.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma espécie encontrada contendo o nome informado");
        }
        return especies;
    }

    //fim


    
}
