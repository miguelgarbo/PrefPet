package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Publicacao;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.repository.PublicacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;


@ExtendWith(MockitoExtension.class)
public class PublicacaoServiceTest {

    @InjectMocks
    PublicacaoService publicacaoService;

    @Mock
    PublicacaoRepository publicacaoRepository;

    Publicacao publicacao = new Publicacao();

    List<Publicacao> publicacoes = new ArrayList<>();

    @BeforeEach
    void setUp(){
        Entidade entidade = new Entidade();
        entidade.setNome("Unila");
        entidade.setId(1L);

        publicacao.setDescricao("Lorem Ipsum");
        publicacao.setEntidade(entidade);
        publicacao.setTipoPublicacao("CAMPANHA DE VACINAÇÃO");

        publicacoes.add(publicacao);
        publicacaoRepository.save(publicacao);
    }

    @Test
    @DisplayName("esse teste deve retornar publicacoes")
    void findAll() {

        Mockito.when(publicacaoRepository.findAll()).thenReturn(publicacoes);

        var resposta = publicacaoService.findAll();

        Assertions.assertEquals(resposta, publicacoes);
        Mockito.verify(publicacaoRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("esse teste deve salvar publicacao")
    void save() {
        Publicacao publicacaoNova = new Publicacao();
        Mockito.when(publicacaoRepository.save(any())).thenReturn(publicacaoNova);

        var resposta = publicacaoService.save(publicacaoNova);

        Assertions.assertEquals(resposta, publicacaoNova);
        Mockito.verify(publicacaoRepository, Mockito.times(1)).save(publicacaoNova);

    }

    @Test
    @DisplayName("esse teste deve retornar uma publicacao pelo id")
    void findById() {

        Mockito.when(publicacaoRepository.findById(1L)).thenReturn(Optional.of(publicacao));

        var resposta = publicacaoService.findById(1L);

        Assertions.assertEquals(resposta, publicacao);
        Mockito.verify(publicacaoRepository, Mockito.times(1)).findById(any());
    }


    @Test
    void update() {
        Publicacao publicacaoNova = new Publicacao();
        publicacaoNova.setDescricao("Texto atualizado");

        Mockito.when(publicacaoRepository.findById(publicacao.getId())).thenReturn(Optional.of(publicacao));

        publicacao.setDescricao(publicacaoNova.getDescricao());

        Mockito.when(publicacaoRepository.save(publicacao)).thenReturn(publicacao);

        var resposta = publicacaoService.update(publicacao.getId(), publicacaoNova);

        Assertions.assertEquals("Texto atualizado", resposta.getDescricao());
        Mockito.verify(publicacaoRepository, Mockito.times(1)).findById(any());
        Mockito.verify(publicacaoRepository, Mockito.times(2)).save(any());
    }

    @Test
    void delete() {

        Mockito.when(publicacaoRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(publicacaoRepository).deleteById(1L);

        String mensagem = publicacaoService.delete(1L);

        Assertions.assertEquals("Publicacao com id 1 foi excluído com sucesso." ,mensagem);
        Mockito.verify(publicacaoRepository, Mockito.times(1)).deleteById(any());

    }

    @Test
    void findByEntidade() {

        Mockito.when(publicacaoRepository.findByEntidade(new Entidade())).thenReturn(Optional.of(publicacoes));

        var resposta = publicacaoService.findByEntidade(new Entidade());
        System.out.println(publicacoes);
        System.out.println(resposta);

        Assertions.assertEquals(publicacoes,resposta);

    }



    @Test
    void findByEntidadeNome() {

        Mockito.when(publicacaoRepository.findByEntidadeNome("Unila")).thenReturn(Optional.of(publicacoes));

        var resposta = publicacaoService.findByEntidadeNome("Unila");

        System.out.println(publicacoes.get(0).getEntidade().getNome());
        System.out.println(resposta);

        Assertions.assertEquals(publicacoes,resposta);
    }


    @Test
    void findByTipoPublicacao() {

        Mockito.when(publicacaoRepository.findByTipoPublicacao("CAMPANHA DE VACINAÇÃO")).thenReturn(Optional.of(publicacoes));

        var resposta = publicacaoService.findByTipoPublicacao("CAMPANHA DE VACINAÇÃO");

        System.out.println(publicacoes.get(0).getTipoPublicacao());
        System.out.println(resposta);

        Assertions.assertEquals(publicacoes,resposta);
    }


    @Test
    void findByDescricao() {

        Mockito.when(publicacaoRepository.findByDescricaoContaining("Lorem")).thenReturn(Optional.of(publicacoes));

        var resposta = publicacaoService.findByDescricao("Lorem");

        System.out.println(publicacoes.get(0).getDescricao());
        System.out.println(resposta);

        Assertions.assertEquals(publicacoes,resposta);

    }
}
