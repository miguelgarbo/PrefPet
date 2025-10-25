package com.uni.PrefPet.service;
import com.uni.PrefPet.model.Contato;
import com.uni.PrefPet.repository.ContatoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    ///crud basico

    public Contato save(Contato contato){
        return contatoRepository.save(contato);
    }

    public Contato findById(Long id){
        return contatoRepository.findById(id).orElseThrow(this::mensagemContatoNotFounded);
    }

    public List<Contato> findAll(){
        return contatoRepository.findAll();
    }

    public String delete(Long id){

        if(!existById(id)){

        }
        contatoRepository.deleteById(id);

        return "Contato Deletado com Sucesso";
    }

    public boolean existById(Long id) {
        if (!contatoRepository.existsById(id)) {
            throw new EntityNotFoundException("contato Não Encontrado");
        }
        return true;
    }

    public Contato update(Long id, Contato contatoAtualizado) {

        Contato contatoSelecionada = contatoRepository.findById(id).orElseThrow(
                this::mensagemContatoNotFounded
        );

        if (contatoAtualizado.getTelefone() != null) {
            contatoSelecionada.setTelefone(contatoAtualizado.getTelefone());
        }

        if (contatoAtualizado.getAtivo() != null) {
            contatoSelecionada.setAtivo(contatoAtualizado.getAtivo());
        }

        if (contatoAtualizado.getEmail() != null) {
            contatoSelecionada.setEmail(contatoAtualizado.getEmail());
        }

        if (contatoAtualizado.getTelefone() != null) {
            contatoSelecionada.setTelefone(contatoAtualizado.getTelefone());
        }


        if (contatoAtualizado.getNomeOrgao() != null) {
            contatoSelecionada.setNomeOrgao(contatoAtualizado.getNomeOrgao());
        }
        
        return contatoRepository.save(contatoSelecionada);    }


    //serviços especificos:
    public boolean existsByEmail(String email) {
        return contatoRepository.existsByEmail(email);
    }

    public List<Contato> findByEmail(String email) {
        return contatoRepository.findByEmail(email)
                .orElseThrow(this::mensagemContatoNotFounded);
    }


    public List<Contato> findByNomeOrgaoContainingIgnoreCase(String nomeOrgao) {
        return contatoRepository.findByNomeOrgaoContainingIgnoreCase(nomeOrgao).orElseThrow(
                this::mensagemContatoNotFounded
        );
    }

    public List<Contato> findByTelefone(String telefone) {
        return contatoRepository.findByTelefoneContaining(telefone).
                orElseThrow(this::mensagemContatoNotFounded);
    }

    public EntityNotFoundException mensagemContatoNotFounded(){
        return new EntityNotFoundException("Contato Não Encontrado");
    }

}
