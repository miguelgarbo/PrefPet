package com.uni.PrefPet.service;

import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.repository.AplicacaoVacinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AplicacaoVacinaService {

    @Autowired
    private AplicacaoVacinaRepository aplicacaoVacinaRepository;

    /// crud basico

    public AplicacaoVacina save(AplicacaoVacina aplicacaoVacina, int meses) {
        if (aplicacaoVacina.getDataValidade() == null && aplicacaoVacina.getDataAplicacao() != null) {
            aplicacaoVacina.setDataValidade(gerarDataValidade(aplicacaoVacina.getDataAplicacao(), meses));
        }
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

        if (!existById(id)) {
            throw new EntityNotFoundException("");
        }
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

    public boolean existsByLote(String lote) {
        return aplicacaoVacinaRepository.existsByLote(lote);
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
    
    
}
