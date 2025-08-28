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

    /// crud basico

    public Vacina save(Vacina vacina, int meses) {
        if (vacina.getDataValidade() == null && vacina.getDataAplicacao() != null) {
            vacina.setDataValidade(gerarDataValidade(vacina.getDataAplicacao(), meses));
        }
        return vacinaRepository.save(vacina);
    }

    public Vacina findById(Long id) {
        return vacinaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Vacina Não Encontradaa")
        );
    }

    public List<Vacina> findAll() {
        return vacinaRepository.findAll();
    }

    public String delete(Long id) {

        if (!existById(id)) {
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

        Vacina vacinaSelecionada = vacinaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Vacina Não Encontrada")
        );

        if (vacinaAtualizada.getNome() != null) {
            vacinaSelecionada.setNome(vacinaAtualizada.getNome());
        }

        if (vacinaAtualizada.getDataAplicacao() != null) {
            vacinaSelecionada.setDataAplicacao(vacinaAtualizada.getDataAplicacao());
        }

        return vacinaRepository.save(vacinaSelecionada);
    }

    ///fim crud basico


    public List<Vacina> findByNome(String nome) {
        return vacinaRepository.findByNomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma vacina encontrada contendo o nome informado"));
    }

    public Vacina findByLote(String lote) {
        return vacinaRepository.findByLote(lote)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma vacina encontrada com o lote informado"));
    }

    public boolean existsByLote(String lote) {
        return vacinaRepository.existsByLote(lote);
    }

    public List<Vacina> findByDataAplicacao(LocalDate dataAplicacao) {
        return vacinaRepository.findByDataAplicacao(dataAplicacao)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma vacina encontrada para a data de aplicação informada"));
    }

    public List<Vacina> findByDataAplicacaoAfter(LocalDate data) {
        return vacinaRepository.findByDataAplicacaoAfter(data)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma vacina encontrada aplicada após a data informada"));
    }

    public List<Vacina> findByValidadeBefore(LocalDate data) {
        return vacinaRepository.findByDataValidadeBefore(data)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma vacina encontrada com validade anterior à data informada"));
    }

    public List<Vacina> findByValidadeAfter(LocalDate data) {
        return vacinaRepository.findByDataValidadeAfter(data)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma vacina encontrada com validade posterior à data informada"));
    }

    public List<Vacina> findByAnimalId(Long animalId) {
        return vacinaRepository.findByAnimais_Id(animalId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Nenhuma vacina encontrada para o animal informado"));
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