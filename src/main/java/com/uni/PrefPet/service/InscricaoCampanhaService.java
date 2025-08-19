package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Campanha;
import com.uni.PrefPet.model.Enum.StatusInscricao;
import com.uni.PrefPet.model.InscricaoCampanha;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.repository.InscricaoCampanhaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InscricaoCampanhaService {

    @Autowired
    private InscricaoCampanhaRepository inscricaoCampanhaRepository;

    ///crud basico
    public InscricaoCampanha save(InscricaoCampanha inscricaoCampanha) {

        validarAnimalInscritoCampanha(inscricaoCampanha.getAnimal(), inscricaoCampanha.getCampanha());
        validarAnimalUsuario(inscricaoCampanha.getUsuario(), inscricaoCampanha.getAnimal());
        inscricaoCampanha.setDataInscricao(LocalDateTime.now());
        inscricaoCampanha.setStatus(StatusInscricao.INSCRITO);
        return inscricaoCampanhaRepository.save(inscricaoCampanha);
    }


    public InscricaoCampanha findById(Long id){
        return inscricaoCampanhaRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("InscricaoCampanha Não Encontradaa")
        );
    }

    public List<InscricaoCampanha> findAll(){
        return inscricaoCampanhaRepository.findAll();
    }

    public String delete(Long id){
        if(!existById(id)){
        }
        inscricaoCampanhaRepository.deleteById(id);
        return "Inscricao Campanha Deletada com Sucesso";
    }

    public boolean existById(Long id) {
        if (!inscricaoCampanhaRepository.existsById(id)) {
            throw new EntityNotFoundException("Inscricao Campanha Não Encontrada");
        }
        return true;
    }

    public InscricaoCampanha update(Long id, InscricaoCampanha inscricaoCampanhaAtualizada) {

        InscricaoCampanha inscricaoCampanhaSelecionada = inscricaoCampanhaRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Inscricao Campanha Não Encontrada")
        );

        if (inscricaoCampanhaAtualizada.getUsuario() != null) {
            inscricaoCampanhaSelecionada.setUsuario(inscricaoCampanhaAtualizada.getUsuario());
        }

        if (inscricaoCampanhaAtualizada.getAnimal() != null) {
            inscricaoCampanhaSelecionada.setAnimal(inscricaoCampanhaAtualizada.getAnimal());
        }

        if (inscricaoCampanhaAtualizada.getCampanha() != null) {
            inscricaoCampanhaSelecionada.setCampanha(inscricaoCampanhaAtualizada.getCampanha());
        }

        if (inscricaoCampanhaAtualizada.getDataInscricao() != null) {
            inscricaoCampanhaSelecionada.setDataInscricao(inscricaoCampanhaAtualizada.getDataInscricao());
        }

        if (inscricaoCampanhaAtualizada.getStatus() != null) {
            inscricaoCampanhaSelecionada.setStatus(inscricaoCampanhaAtualizada.getStatus());
        }

        return inscricaoCampanhaRepository.save(inscricaoCampanhaSelecionada);    }

    //serviços especificos:

    public void validarAnimalInscritoCampanha(Animal animal, Campanha campanha) {
        if (inscricaoCampanhaRepository.existsByAnimalAndCampanha(animal, campanha)) {
            throw new IllegalArgumentException("Este animal já está inscrito nesta campanha");
        }
    }


    public List<InscricaoCampanha> findByCampanhaTitulo(String titulo) {
        return inscricaoCampanhaRepository.findByCampanhaTitulo(titulo);
    }

    public List<InscricaoCampanha> findByAnimalNome(String nome) {
        return inscricaoCampanhaRepository.findByAnimalNome(nome);
    }

    public List<InscricaoCampanha> findByUsuarioNome(String nome) {
        return inscricaoCampanhaRepository.findByUsuarioNome(nome);
    }

    public List<InscricaoCampanha> findByStatus(StatusInscricao status) {
        return inscricaoCampanhaRepository.findByStatus(status);
    }

    public void validarAnimalUsuario(Usuario usuario, Animal animal){
        if(!usuario.getAnimais().contains(animal)){
            throw new IllegalArgumentException("O Animal deve ser do usuario");
        }
    }

}
