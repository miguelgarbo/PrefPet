package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.model.Notificacao;
import com.uni.PrefPet.model.Vacina;

import com.uni.PrefPet.repository.AnimalRepository;
import com.uni.PrefPet.repository.AplicacaoVacinaRepository;
import com.uni.PrefPet.repository.VacinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AplicacaoVacinaService {

    @Autowired
    private AplicacaoVacinaRepository aplicacaoVacinaRepository;
    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private AnimalRepository animalRepository;

    /// crud basico

    public AplicacaoVacina save(AplicacaoVacina aplicacaoVacina, int meses) {
        Vacina vacina = vacinaRepository.findById(aplicacaoVacina.getVacina().getId())
                .orElseThrow(() -> new EntityNotFoundException("Vacina não encontrada"));

        Animal animal = animalRepository.findById(aplicacaoVacina.getAnimal().getId())
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado"));

        // Calcule o número da dose com base no histórico do animal e vacina
        long doseAnterior = aplicacaoVacinaRepository.countByAnimalAndVacina(animal, vacina);
        aplicacaoVacina.setNumeroDose((int) doseAnterior + 1);

        aplicacaoVacina.setAnimal(animal);
        aplicacaoVacina.setVacina(vacina);

        LocalDate validade = aplicacaoVacina.getDataAplicacao().plusMonths(meses);
        aplicacaoVacina.setDataValidade(validade);

        return aplicacaoVacinaRepository.save(aplicacaoVacina);
    }

    public AplicacaoVacina findById(Long id) {
        return aplicacaoVacinaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("AplicacaoVacina Não Encontradaa")
        );
    }

    public List<AplicacaoVacina> findAll() {
        return aplicacaoVacinaRepository.findAll();
    }

    public String delete(Long id) {
        existById(id);
        aplicacaoVacinaRepository.deleteById(id);
        return "AplicacaoVacina Deletada com Sucesso";
    }

    public boolean existById(Long id) {
        if (!aplicacaoVacinaRepository.existsById(id)) {
            throw new EntityNotFoundException("AplicacaoVacina Não Encontrada");
        }
        return true;
    }

    public AplicacaoVacina update(Long id, AplicacaoVacina aplicacaoVacinaAtualizada) {

        AplicacaoVacina aplicacaoVacinaSelecionada = aplicacaoVacinaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("AplicacaoVacina Não Encontrada")
        );

        if (aplicacaoVacinaAtualizada.getDataAplicacao() != null) {
            aplicacaoVacinaSelecionada.setDataAplicacao(aplicacaoVacinaAtualizada.getDataAplicacao());
        }

        return aplicacaoVacinaRepository.save(aplicacaoVacinaSelecionada);
    }

    ///fim crud basico




    public AplicacaoVacina findByLote(String lote) {
        return aplicacaoVacinaRepository.findByLote(lote)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma aplicacaoVacina encontrada com o lote informado"));
    }


    public List<AplicacaoVacina> findByDataAplicacao(LocalDate dataAplicacao) {
        return aplicacaoVacinaRepository.findByDataAplicacao(dataAplicacao)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma aplicacaoVacina encontrada para a data de aplicação informada"));
    }

    public List<AplicacaoVacina> findByDataAplicacaoAfter(LocalDate data) {
        return aplicacaoVacinaRepository.findByDataAplicacaoAfter(data)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma aplicacaoVacina encontrada aplicada após a data informada"));
    }

    public List<AplicacaoVacina> findByValidadeBefore(LocalDate data) {
        return aplicacaoVacinaRepository.findByDataValidadeBefore(data)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma aplicacaoVacina encontrada com validade anterior à data informada"));
    }

    public List<AplicacaoVacina> findByValidadeAfter(LocalDate data) {
        return aplicacaoVacinaRepository.findByDataValidadeAfter(data)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma aplicacaoVacina encontrada com validade posterior à data informada"));
    }


    /// Validação de datas

    public void validarDataAplicacaoEValidade(LocalDate dataAplicacao, LocalDate dataValidade) {
        if (!dataAplicacao.isBefore(dataValidade)) {
            throw new IllegalArgumentException("A data de aplicação deve ser antes da validade");
        }
    }

    public LocalDate gerarDataValidade(LocalDate dataAplicacao, int meses) {
        return dataAplicacao.plusMonths(meses);
    }

    public List<AplicacaoVacina> findByAnimal(Long animal_id) {
        return aplicacaoVacinaRepository.findByAnimalId(animal_id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma aplicacaoVacina com esse animal "));
    }


}
