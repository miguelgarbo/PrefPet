package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Publicacao;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.repository.PublicacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicacaoService {

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    public List<Publicacao> findAll() {
        return publicacaoRepository.findAll();
    }

    public Publicacao save(Publicacao publicacao) {
        return publicacaoRepository.save(publicacao);
    }

    public Publicacao findById(Long id) {
        return publicacaoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

//    public Imagem saveImagem(Imagem imagem) {
//        Long publicacaoId = imagem.getPublicacao().getId();
//        Publicacao publicacao = publicacaoRepository.findById(publicacaoId)
//                .orElseThrow(() -> new EntityNotFoundException("Publicação com id " + publicacaoId + " não encontrada"));
//
//        imagem.setPublicacao(publicacao);
//        return imagemRepository.save(imagem);
//    }


    public Publicacao update(Long id, Publicacao publicacaoAtualizada) {
        Publicacao publicacaoSelecionada = publicacaoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Publicacao com id " + id + " não encontrado."));
        


        if (publicacaoAtualizada.getDescricao() != null) {
            publicacaoSelecionada.setDescricao(publicacaoAtualizada.getDescricao());
        }

        if (publicacaoAtualizada.getDataCriacao() != null) {
            publicacaoSelecionada.setDataCriacao(publicacaoAtualizada.getDataCriacao());
        }

        if (publicacaoAtualizada.getImagens() != null) {
            publicacaoSelecionada.setImagens(publicacaoAtualizada.getImagens());
        }

        return publicacaoRepository.save(publicacaoSelecionada);
    }

    public String delete(Long id) {
        if (!publicacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Publicaco com id " + id + " não encontrado.");
        }
        publicacaoRepository.deleteById(id);
        return "Publicacao com id " + id + " foi excluído com sucesso.";
    }


    public List<Publicacao> findByEntidade(Entidade entidade){

        return publicacaoRepository.findByEntidade(entidade).orElseThrow(()->
                entityNotFound());
    }

    public List<Publicacao> findByEntidadeNome(String nomeEntidade){

        return publicacaoRepository.findByEntidadeNome(nomeEntidade).orElseThrow(()->
                entityNotFound());
    }


    public List<Publicacao> findByTipoPublicacao(String tipoPublicacao){
        return publicacaoRepository.findByTipoPublicacao(tipoPublicacao).orElseThrow(()->
                entityNotFound());
    }

    public List<Publicacao> findByDescricao(String descricao){

        return publicacaoRepository.findByDescricaoContaining(descricao).orElseThrow(()->
                entityNotFound());
    }

    public EntityNotFoundException entityNotFound(){
        return new EntityNotFoundException("Publicacoes não encontradas");
    }

}
