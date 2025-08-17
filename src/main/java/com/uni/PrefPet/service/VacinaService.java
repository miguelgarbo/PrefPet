package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Vacina;
import com.uni.PrefPet.repository.VacinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            throw new EntityNotFoundException("");
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

        if (vacinaAtualizada.getDataAplicacao() != null) {
            vacinaSelecionada.setDataAplicacao(vacinaAtualizada.getDataAplicacao());
        }

        return vacinaRepository.save(vacinaSelecionada);    }

    ///fim crud basico



    //serviços especificos

    public List<Vacina> findByNome(String nome) {
        List<Vacina> vacinas = vacinaRepository.findByNomeContainingIgnoreCase(nome);
        if (vacinas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma vacina encontrada contendo o nome informado");
        }
        return vacinas;
    }

    public Vacina findByLote(String lote) {
        return vacinaRepository.findByLote(lote)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma vacina encontrada com o lote informado"));
    }

    public boolean existsByLote(String lote) {
        return vacinaRepository.existsByLote(lote);
    }

    public List<Vacina> findByValidadeBefore(LocalDate data) {
        List<Vacina> vacinas = vacinaRepository.findByValidadeBefore(data);
        if (vacinas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma vacina encontrada com validade anterior à data informada");
        }
        return vacinas;
    }

    public List<Vacina> findByValidadeAfter(LocalDate data) {
        List<Vacina> vacinas = vacinaRepository.findByValidadeAfter(data);
        if (vacinas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma vacina encontrada com validade posterior à data informada");
        }
        return vacinas;
    }


    //fim
}
